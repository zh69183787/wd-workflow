/**
 * 
 */
package com.wonders.dataExchange.model.common.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.wonders.attach.model.bo.AttachFile;

/** 
 * @ClassName: AttachVo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年3月12日 下午3:01:15 
 *  
 */

@XmlRootElement(name="AttachVo")
public class AttachDataVo {
	@XmlElement(name = "attach")
	public List<AttachFile> attachList = new ArrayList<AttachFile>();

	@XmlTransient
	public List<AttachFile> getAttachList() {
		return attachList;
	}

	public void setAttachList(List<AttachFile> attachList) {
		this.attachList = attachList;
	}

	
	
}
