package com.wonders.dept.workflow.organTree.model.bo;

/**部门组织树对象
 * @author XFZ
 * @version 1.0 2012-6-19
 */
public class OrganDeptBo {
	//[{"id":2500,"levels":0,"name":"申通地铁集团有限公司","isParent":true,"orders":0,"pid":0}
	public String id = "";
	public Integer levels = 0;
	public String name = "";
	public boolean isParent = false;
	public int orders = 0;
	public String pid = "";
	public String description = "";
	
	public String recvLoginName = "";
	public String recvName = "";
	public String ldLoginName = "";
	public String ldName = "";
	public String typeId = "";
	public String icon = "/workflow/contract/organTree/img/users.png";
}
