package com.wonders.project.workflow.organTree.model.bo;

/**部门组织树对象
 * @author XFZ
 * @version 1.0 2012-6-19
 */
public class GroupBo {
	//[{"id":2500,"levels":0,"name":"申通地铁集团有限公司","isParent":true,"orders":0,"pid":0}
	public String id = "";
	public String code = "";
	public String name = "";
	public boolean isParent = true;
	public int orders = 0;
	public String pid = "";
	public String description = "";
	public String icon = "/workflow/project/organTree/img/users.png";
	public void init(){
		this.id = this.code;
	}
}
