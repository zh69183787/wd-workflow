package com.wonders.project.workflow.organTree.model.bo;

/**用户组织树对象
 * @author XFZ
 * @version 1.0 2012-6-19
 */
public class OrganUserBo {
	//{"id":2850,"name":"陈川","isParent":false,"orders":1,"pid":2549,"loginName":"G020001000502549"}
	public String id = "";
	public String loginName = "";
	public String name = "";
	public boolean isParent = false;
	public int orders = 0;
	public String pid = "";
	public String icon = "/workflow/receive/organTree/img/user.png";
}
