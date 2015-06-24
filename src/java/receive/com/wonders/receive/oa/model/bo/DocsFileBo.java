/**
 * 
 */
package com.wonders.receive.oa.model.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.wonders.util.DateUtil;

/** 
 * @ClassName: DocsFileBo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-8-7 上午11:40:56 
 *  
 */
@Entity
@Table(name = "T_DOCS_FILE")
public class DocsFileBo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8934165531678389353L;
	
	private String sid;
	private String file_name;
	private String file_path;
	private String state;
	private String ext_name;
	private String file_size;
	private String create_user;
	private String create_date;
	private String update_user;
	private String update_date;
	private String parent_sid;
	private Integer save_mode;
	private String file_pty_id;
	private String file_pty_key;
	private String file_pty_cwrq;
	private String file_pty_lwdw;
	private String file_status;
	private String file_assessor;
	private String file_check_flag;
	private String file_assessor_date;
	private String create_user_name;
	private String flag;	
	private String link_flag;
	
	public DocsFileBo(){
		this.state = "1";
		this.flag = "wjgl";
		this.create_date = DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss");
		this.file_assessor_date = DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss");
		this.update_date = DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss");
	}
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name = "SID")
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	
	@Column(name = "FILE_NAME", nullable = true, length = 200)
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	@Column(name = "FILE_PATH", nullable = true, length = 500)
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	@Column(name = "STATE", nullable = true, length = 1)
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Column(name = "EXT_NAME", nullable = true, length = 10)
	public String getExt_name() {
		return ext_name;
	}
	public void setExt_name(String ext_name) {
		this.ext_name = ext_name;
	}
	@Column(name = "FILE_SIZE", nullable = true, length = 20)
	public String getFile_size() {
		return file_size;
	}
	public void setFile_size(String file_size) {
		this.file_size = file_size;
	}
	@Column(name = "CREATE_USER", nullable = true, length = 50)
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	@Column(name = "CREATE_DATE", nullable = true, length = 50)
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	@Column(name = "UPDATE_USER", nullable = true, length = 50)
	public String getUpdate_user() {
		return update_user;
	}
	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
	@Column(name = "UPDATE_DATE", nullable = true, length = 50)
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
	@Column(name = "PARENT_SID", nullable = true, length = 100)
	public String getParent_sid() {
		return parent_sid;
	}
	public void setParent_sid(String parent_sid) {
		this.parent_sid = parent_sid;
	}
	@Column(name = "SAVE_MODE")
	public Integer getSave_mode() {
		return save_mode;
	}
	public void setSave_mode(Integer save_mode) {
		this.save_mode = save_mode;
	}
	@Column(name = "FILE_PTY_ID", nullable = true, length = 200)
	public String getFile_pty_id() {
		return file_pty_id;
	}
	public void setFile_pty_id(String file_pty_id) {
		this.file_pty_id = file_pty_id;
	}
	@Column(name = "FILE_PTY_KEY", nullable = true, length = 200)
	public String getFile_pty_key() {
		return file_pty_key;
	}
	public void setFile_pty_key(String file_pty_key) {
		this.file_pty_key = file_pty_key;
	}
	@Column(name = "FILE_PTY_CWRQ", nullable = true, length = 19)
	public String getFile_pty_cwrq() {
		return file_pty_cwrq;
	}
	public void setFile_pty_cwrq(String file_pty_cwrq) {
		this.file_pty_cwrq = file_pty_cwrq;
	}
	@Column(name = "FILE_PTY_LWDW", nullable = true, length = 200)
	public String getFile_pty_lwdw() {
		return file_pty_lwdw;
	}
	public void setFile_pty_lwdw(String file_pty_lwdw) {
		this.file_pty_lwdw = file_pty_lwdw;
	}
	@Column(name = "FILE_STATUS", nullable = true, length = 50)
	public String getFile_status() {
		return file_status;
	}
	public void setFile_status(String file_status) {
		this.file_status = file_status;
	}
	@Column(name = "FILE_ASSESSOR", nullable = true, length = 50)
	public String getFile_assessor() {
		return file_assessor;
	}
	public void setFile_assessor(String file_assessor) {
		this.file_assessor = file_assessor;
	}
	@Column(name = "FILE_CHECK_FLAG", nullable = true, length = 50)
	public String getFile_check_flag() {
		return file_check_flag;
	}
	public void setFile_check_flag(String file_check_flag) {
		this.file_check_flag = file_check_flag;
	}
	@Column(name = "FILE_ASSESSOR_DATE", nullable = true, length = 50)
	public String getFile_assessor_date() {
		return file_assessor_date;
	}
	public void setFile_assessor_date(String file_assessor_date) {
		this.file_assessor_date = file_assessor_date;
	}
	@Column(name = "CREATE_USER_NAME", nullable = true, length = 200)
	public String getCreate_user_name() {
		return create_user_name;
	}
	public void setCreate_user_name(String create_user_name) {
		this.create_user_name = create_user_name;
	}
	@Column(name = "FLAG", nullable = true, length = 50)
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Column(name = "LINK_FLAG", nullable = true, length = 10)
	public String getLink_flag() {
		return link_flag;
	}
	public void setLink_flag(String link_flag) {
		this.link_flag = link_flag;
	}
	
	

}
