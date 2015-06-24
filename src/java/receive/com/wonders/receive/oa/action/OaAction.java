/**
 * 
 */
package com.wonders.receive.oa.action;

import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.wonders.receive.oa.util.OaUtil;
import com.wonders.receive.workflow.common.action.AbstractParamAction;
import com.wonders.receive.workflow.model.vo.ParamVo;

/** 
 * @ClassName: OaAction 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-8-6 下午7:48:54 
 *  
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace(value="/oa")
@Controller("oaAction")
@Scope("prototype")
public class OaAction extends AbstractParamAction{
	private Map<String,String> resultMap;
	private String pname;
	private String pincident;
	private String type;
	
	


	@Action(value="dealResult",results={
			@Result(name="success",location="/receive/approve/dealResult.jsp")
	})
	public String dealResult(){
		resultMap = OaUtil.getProcessRelation(pname, pincident, type);
		return "success";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getPincident() {
		return pincident;
	}
	public void setPincident(String pincident) {
		this.pincident = pincident;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public Map<String, String> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, String> resultMap) {
		this.resultMap = resultMap;
	}

	/** 
	* @Title: getParams 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @throws 
	*/
	@Override
	public ParamVo getParams() {
		// TODO Auto-generated method stub
		return null;
	}

}
