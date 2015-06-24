<%@ page language="java" import="java.util.*,java.net.*" pageEncoding="utf-8"%>

<%@page import="com.wonders.contract.workflow.common.model.vo.UserInfo"%>
<%@page import="com.wonders.common.model.vo.TaskUserVo"%>
<%@page import="com.wonders.contract.workflow.constants.CommonConstants"%>
<%@page import="com.wonders.contract.workflow.constants.LoginConstants"%>
<%@page import="com.wonders.dataExchange.util.*"%>
<%@page import="com.wonders.util.*"%>
<%@page import="com.wonders.contract.workflow.common.util.LoginUtil"%>
<%@page import="java.sql.Clob"%>
<%@page import="org.springframework.web.util.HtmlUtils;"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
  <head>
 	<script src="<%=path %>/dataExchange/js/html5.js"></script>
    <script src="<%=path %>/dataExchange/js/jquery-1.7.1.js"></script>
	<script src="<%=path %>/dataExchange/js/jquery.formalize.js"></script>
	<script src="<%=path %>/dataExchange/js/jquery.reveal.js"></script>
  	<link rel="stylesheet" href="<%=path %>/dataExchange/js/reveal.css" />
	
  	<link rel="stylesheet" href="<%=path %>/dataExchange/css/formalize.css" />
	<link rel="stylesheet" href="<%=path %>/dataExchange/css/page.css" />
	<link rel="stylesheet" href="<%=path %>/dataExchange/css/default/imgs.css" />
	<link rel="stylesheet" href="<%=path %>/dataExchange/css/reset.css" />
  	<script>
  	$(document).ready(function(){
		$("#sb").click(function(){
			window.location.reload();
		})
		$(".view").click(function(){
			//alert($(this).next().html());
			$('#msgp').html("<pre>"+$(this).next().html()+"</pre>");
			$('#myModal').reveal({
			     animation: 'fade',                   //fade, fadeAndPop, none
			     animationspeed: 300,                       //how fast animtions are
			     closeonbackgroundclick: true,              //if you click background will modal close?
			     dismissmodalclass: 'close-reveal-modal'    //the class of a button or element that will close an open modal
			});
			
		});
		$("#myModal").width($(document).width()/3);
		$("#myModal").height($(document).height()/1.5);
  	})
  	
	function openForm(pname,pincident,cname,cincident,steplabel,taskid,taskuser){
  		if(pname=="合同后审流程"){
			var url = "<%=path%>/contract-reviewMain/forward.action?"
				+"pname="+encodeURI(pname)+"&"
				+"pincident="+pincident+"&"
				+"cname="+encodeURI(cname)+"&"
				+"cincident="+cincident+"&"
				+"steplabel="+encodeURI(steplabel)+"&"
				+"taskid="+taskid+"&"
				+"taskuser="+taskuser+"&"
				+"rand="+Math.random();
			window.open(url);
  		}
			return false;
	  	}
  	
  
  	</script>
<%!
	public String oracleClob2Str(Clob clob) throws Exception {
    return (clob != null ? clob.getSubString(1, (int) clob.length()) : null);
}
%>  	
<%
	UserInfo bo = LoginUtil.getUserInfo(session);
	Map<String,TaskUserVo> userMap = bo.getMap();
	for(Map.Entry<String , TaskUserVo> entry : userMap.entrySet()){
		System.out.println(entry.getKey()+"<br>");
		System.out.println(entry.getValue().getLoginName()+"<br>");
		System.out.println(entry.getValue().getUserName()+"<br>");
		System.out.println(entry.getValue().getDeptId()+"<br>");
		System.out.println(entry.getValue().getDeptName()+"<br>");
	}
	
	String loginNames = "";
	for(Map.Entry<String, TaskUserVo> entry : userMap.entrySet()){
		loginNames += "'"+StringUtil.getNotNullValueString(entry.getKey()) +"'"+",";
	}
	if(loginNames.length() > 0){
		loginNames = loginNames.substring(0, loginNames.length()-1);
	}
	
	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	if(!"".equals(loginNames)){
		list = DataExchangeUtil.list(loginNames);
	}
%>
  	
  </head>
 
  <body>
   <div id="myModal" class="reveal-modal" style="overflow:scroll;"> 
   <p id="msgp"></p>
</div> 	
  	<div class="main">
    	<!--Ctrl-->
		<div class="ctrl clearfix">
			<div class="fl"><img id="show" onclick="showHide();" src="<%=path %>/dataExchange/css/default/images/sideBar_arrow_right.jpg" width="46" height="30" alt="收起"></div>
            <div class="posi fl">
            	<ul>
                	<li><a href="javascript:void(0);">流程接口</a></li>
                	<li class="fin">流程接口</li>
                </ul>
            </div>
            <div style="display:none;" class="fr lit_nav nwarp">
            	<ul>
                    <li class="selected"><a class="print" href="#">打印</a></li>
                    <li><a class="express" href="#">导出数据</a></li>
                    <li class="selected"><a class="table" href="#">表格模式</a></li>
                    <li><a class="treeOpen" href="#">打开树</a></li>
                    <li><a class="filterClose" href="#">关闭过滤</a></li>
                </ul>
            </div>
   		</div>
        <!--Ctrl End-->
      <div class="pt45">
        <!--Filter-->
      <div class="filter">
        	<div class="query">
        	<div class="p8 filter_search">
        	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
        	    <tr>
        	       <td class="t_r">流程类型</td>
        	      <td>
        	      <input type="text" id="typename" name="typename" class="input_large"  value=""/>
        	      </td>
        	       <td class="t_r">标题</td>
        	      <td>
        	      <input type="text" id="title" name="title" class="input_large" value=""/>
        	      </td>
        	      <td class="t_r">接收时间</td>
        	      <td>
        	      <input type="text" id="occurtimes" name="occurtimes" class="input_large date" value="" readonly="readonly"/>
                    -
                  <input type="text" id="occurtimee" name="occurtimee" class="input_large date" value="" readonly="readonly" />
        	      </td>
        	      
      	        </tr>
      	        
      	        
        	    <tr>
        	      <td colspan="6" class="t_c">
                  	<input type="button" id="sb" value="检 索" />&nbsp;&nbsp;
                  	<input id="clearInput" type="button" value="重 置"/></td>
				</tr>
      	    </table>
      	    </div>
        	</div>     
		      <div class="fn clearfix">
		                  <h5 class="fl"><a href="#" class="fl">X</a></h5>
		             <!-- <input type="submit" name="button2" id="button2" value="新 增" class="fr">
		             <h5 class="fr"><font class="L_08">提示：待办事项35分钟更新一次。</font></h5>--> 
		            </div>
		      </div>
        <!--Filter End-->
        <!--Table-->
        <div class="mb10">
        	<table width="100%"  class="table_1">
               <tbody>
                <tr class="tit">
                  <!-- <td><input type="checkbox" id="test_checkbox_1" name="test_checkbox_1" /></td>-->
               		<td class="t_c">序号</td>
                  <td class="t_c">流程类型</td>
                  <td class="t_c">来源系统</td>
                  <td style="width:50%;" class="t_c">标题</td>
                  <td class="t_c">当前阶段</td>
                  <td class="t_c">接收人</td>
                  <td class="t_c">接收人部门</td>
                  <td class="t_c">接收时间</td>
                  <td class="t_c">状态</td>
                  <td class="t_c">内容</td>
                  <td class="t_c">表单</td>
                  </tr>
                  
                  <%
	                  if(list!=null && list.size()>0){
	              		for(int i=0;i<list.size();i++){
	              			Map<String,Object> map = list.get(i);
	              			String id = map.get("id").toString();
	              			String type = map.get("type").toString();
	              			String source = map.get("source").toString();
	              			String title = map.get("TITLE").toString();
	              			String loginName = map.get("login_name").toString();
	              			String userName = map.get("user_name").toString();
	              			String deptId = map.get("dept_id").toString();
	              			String deptName = map.get("dept_name").toString();
	              			String createTime = map.get("create_time").toString();
	              			String status = map.get("status").toString();
	              			String content = map.get("content").toString();
	              			String stepName = map.get("step").toString();
                  %>
                 	<tr>
                	<td class="t_c"><%=(i+1) %></td>
                	<td class="t_c"><%=type %></td>
	               	<td class="t_c"><%=source %></td>
	                <td class="t_l"><%=title %></td>
	                <td class="t_l"><%=stepName %></td>
                 	<td class="t_c"><%=userName %></td>
                  	<td class="t_c"><%=deptName %></td>
                  	<td class="t_c"><%=createTime %></td>
                  	<td class="t_c"><%=status %></td>
                  	<td class="t_c"><a class="view" href="javascript:void(0);">点击查看</a>
                  	<pre style="display:none;"><%=HtmlUtils.htmlEscape(content) %></pre> </td>
                  	<td class="nwarp">
                  		<center>
	                  <a href="/workflow/dataExchange/view.action?id=<%=id %>" target="_blank" class="todoUrl"><img src="<%=path %>/dataExchange/css/default/images/p_open.gif"></a>
	                	</center>
	                </td>
                  </tr>
                  
                  <%}
	              		}%>
                </tbody>
              </table>

      </div>
        <!--Table End-->
</div>
</div>
  </body>
</html>
