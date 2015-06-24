package com.wonders.project.workflow.organTree.model.bo;

/**用户组织树对象
 * @author XFZ
 * @version 1.0 2012-6-19
 */
public class OrganLeaderBo {
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
	public Long leaderId = 0L;
	public Long leaderDeptId= 0L;	
	public String leaderLoginName = "";;
	public String leaderName = "";;
	public Long secretaryId= 0L;
	public Long secretaryDeptId= 0L;	
	public String secretaryLoginName = "";;
	public String secretaryName = "";;
	public boolean isParent = false;
	public int orders = 0;
	public String pid = "2501";
	public String icon = "/workflow/receive/organTree/img/user.png";
	public void init(){
		this.id = this.leaderLoginName+"";
		this.name = this.leaderName;
	}
}
