/**
 * 
 */
package com.wonders.dataExchange.model.flow.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.wonders.dataExchange.model.common.xml.AttachDataVo;
import com.wonders.dataExchange.model.common.xml.RegisterDataVo;
import com.wonders.dataExchange.model.flow.vo.RecvMainVo;

/** 
 * @ClassName: ReviewMainXml 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年3月12日 下午2:25:13 
 *  
 */

@XmlRootElement(name = "root")
public class RecvMainXml {
	
	@XmlElement(name = "basicData")
	public RecvMainVo mainVo = new RecvMainVo();
	
	@XmlElement(name = "attachData")
	public AttachDataVo attachVo = new AttachDataVo();
	
	@XmlElement(name = "registerData")
	public RegisterDataVo registerVo = new RegisterDataVo();
	
	@XmlTransient
	public RecvMainVo getMainVo() {
		return mainVo;
	}
	public void setMainVo(RecvMainVo mainVo) {
		this.mainVo = mainVo;
	}
	
	@XmlTransient
	public AttachDataVo getAttachVo() {
		return attachVo;
	}
	public void setAttachVo(AttachDataVo attachVo) {
		this.attachVo = attachVo;
	}
	
	@XmlTransient
	public RegisterDataVo getRegisterVo() {
		return registerVo;
	}
	public void setRegisterVo(RegisterDataVo registerVo) {
		this.registerVo = registerVo;
	}
	
	
	
}
