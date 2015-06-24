/**
 * 
 */
package com.wonders.receive.oa.model.bo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/** 
 * @ClassName: MsgBo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-8-6 上午9:50:19 
 *  
 */
@Entity
@Table(name = "T_MSG_USERMESSAGE")
public class MsgBo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1724521972158207295L;
	private Long sid;		//内部流水号

	private Long empidrec;		//接受短消息的用户编号，可以为空（短消息发送方式为广播的时候）
	private Long empidsend;		//发送短消息的用户编号

	private String title;		//短消息标题

	private String content;		//短消息内容

	private String senddate;	//to_char(sysdate,'yyyy-mm-dd hh24:mi:ss')		发送时间

	private String seedate;		//查看时间
	private Long sendmode;		//发送短消息的方式   INFOTYPE=9
	private Long parentid;		//所回复的短消息编号
	private Integer state;		//短消息的状态  0--未读短消息  1--已读短消息

	private String sendstate;	//'1'发送者当前行的状态，0-已删除 1-正常
	private String recrstate;	//'1'接收者当前行的状态，0-已删除 1-正常
	private String senddelete;	//'1'发送者是否物理删除，0-已删除 1-正常
	private String recrdelete;	//'1'接收者是否物理删除，0-已删除 1-正常
	private Integer autoalert;		//0	是否自动提醒过  <3-未提醒 
	private Integer msgcount;		//0	统计短信数量
	
	private Long eventId;

	
	public MsgBo(){
		this.senddate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		this.sendmode = 187L;
		this.state = 0;
		this.sendstate = "1";
		this.recrstate = "1";
		this.senddelete = "1";
		this.recrdelete = "1";
		this.autoalert = 0;
		this.msgcount = 0;
	}


	@Id  
	@SequenceGenerator(name="t_seq_msg", sequenceName="SEQ_T_MSG") 
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="t_seq_msg")  
	@Column(name = "SID")
	public Long getSid() {
		return sid;
	}


	public void setSid(Long sid) {
		this.sid = sid;
	}

	@Column(name = "EMPIDREC")
	public Long getEmpidrec() {
		return empidrec;
	}


	public void setEmpidrec(Long empidrec) {
		this.empidrec = empidrec;
	}

	@Column(name = "EMPIDSEND")
	public Long getEmpidsend() {
		return empidsend;
	}


	public void setEmpidsend(Long empidsend) {
		this.empidsend = empidsend;
	}

	@Column(name = "TITLE", nullable = true, length = 1000)
	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "CONTENT", nullable = true, length = 4000)
	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "SENDDATE", nullable = true, length = 19)
	public String getSenddate() {
		return senddate;
	}


	public void setSenddate(String senddate) {
		this.senddate = senddate;
	}

	@Column(name = "SEEDATE", nullable = true, length = 19)
	public String getSeedate() {
		return seedate;
	}


	public void setSeedate(String seedate) {
		this.seedate = seedate;
	}

	@Column(name = "SENDMODE")
	public Long getSendmode() {
		return sendmode;
	}


	public void setSendmode(Long sendmode) {
		this.sendmode = sendmode;
	}

	@Column(name = "PARENTID")
	public Long getParentid() {
		return parentid;
	}


	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}

	@Column(name = "STATE")
	public Integer getState() {
		return state;
	}


	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "SENDSTATE", nullable = true, length = 1)
	public String getSendstate() {
		return sendstate;
	}


	public void setSendstate(String sendstate) {
		this.sendstate = sendstate;
	}

	@Column(name = "RECRSTATE", nullable = true, length = 1)
	public String getRecrstate() {
		return recrstate;
	}


	public void setRecrstate(String recrstate) {
		this.recrstate = recrstate;
	}

	@Column(name = "SENDDELETE", nullable = true, length = 1)
	public String getSenddelete() {
		return senddelete;
	}


	public void setSenddelete(String senddelete) {
		this.senddelete = senddelete;
	}

	@Column(name = "RECRDELETE", nullable = true, length = 1)
	public String getRecrdelete() {
		return recrdelete;
	}


	public void setRecrdelete(String recrdelete) {
		this.recrdelete = recrdelete;
	}

	@Column(name = "AUTOALERT")
	public Integer getAutoalert() {
		return autoalert;
	}


	public void setAutoalert(Integer autoalert) {
		this.autoalert = autoalert;
	}

	@Column(name = "MSGCOUNT")
	public Integer getMsgcount() {
		return msgcount;
	}


	public void setMsgcount(Integer msgcount) {
		this.msgcount = msgcount;
	}

	@Column(name = "EVENT_ID")
	public Long getEventId() {
		return eventId;
	}


	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	
	
	
}
