package com.wonders.project.workflow.organTree.model.bo;

/**用户组织树对象
 * @author XFZ
 * @version 1.0 2012-6-19
 */
public class GroupUserBo {
	//{"id":2850,"name":"陈川","isParent":false,"orders":1,"pid":2549,"loginName":"G020001000502549"}
//	public String id = "";
//	public String ldLoginName = "";
//	public String ldName = "";
//	public String secLoginName = "";
//	public String secName = "";
//	public boolean isParent = false;
//	public int orders = 0;
//	public String pid = "";
	public String id = "";
	public String name = "";
	public Long userId = 0L;
	public Long deptId= 0L;	
	public String loginName = "";
	public String userName = "";
	public String code = "";;
	public boolean isParent = false;
	public String pid = "";
	public String icon = "/workflow/project/organTree/img/user.png";
	public void init(){
		this.id = this.loginName+"";
		this.pid = this.code;
		this.name = this.userName;
	}
}
