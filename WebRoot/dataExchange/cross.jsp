<%@ page language="java" import="java.util.*,java.net.*" pageEncoding="utf-8"%>

<%@page import="com.wonders.contract.workflow.common.model.vo.UserInfo"%>
<%@page import="com.wonders.common.model.vo.TaskUserVo"%>
<%@page import="com.wonders.contract.workflow.constants.CommonConstants"%>
<%@page import="com.wonders.contract.workflow.constants.LoginConstants"%>
<%@page import="com.wonders.dataExchange.util.*"%>
<%@page import="com.wonders.util.*"%>
<%@page import="com.wonders.contract.workflow.common.util.LoginUtil"%>
<%@page import="java.sql.Clob"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="org.springframework.web.util.HtmlUtils;"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<!--

E80345F921AE41D1AF0B0287CAF5A39F	weibao	weibao2013!	docSend2925	维保公司
E87607F921AE41D1AF0B0287CAF5A39F	yunguan	yunguan2013!	docSend2920	运管中心
111807F921AE41D1AF0B0287CAF5A39F	yunsi	yunsi2013!	docSend2924	运四公司
E80807F9111E41D1AF0B0287CAF5A39F	yunsan	yunsan2013!	docSend2923	运三公司
E80807F9288841D1AF0B0287CAF5A39F	yunyi	yunyi2013!	docSend2921	运一公司
111807EWQEWE41D1AF0B0287CAF5A39F	daqiao	daqiao2013!	docSend2959	大桥
111807F921AE4QWEEWQB0287CAF5A39F	gfgs	gfgs2013!	docSend2945	股份公司
111807F92QWE41D1AF0B0287CAF5A39F	sdy	sdy2013!	docSend2943	隧道院
11QWERRR21AE41D1AF0B0287CAF5A39F	jszx	jszx2013!	docSend2941	技术中心
111807F921AQWER1AF0B0287CAF5A39F	yuner	yuner2013!	docSend2922	运二公司

  -->

<%!
	public static String getUrl(String id,String loginName,String deptId,
			 					String appName,String urlCa,String returnUrl)  throws UnsupportedEncodingException {
		String url = urlCa+"/login.action?"+
				"loginName="+loginName+"&password=wonder&validate=test"+
				"&deptId="+deptId+"&appName="+appName+"&returnUrl="+URLEncoder.encode(returnUrl,"UTF-8");
		return url;
	
	}
%>  

<%
	String deptIdParam = "";
	String key = request.getParameter("key");
	if("E80807F9288841D1AF0B0287CAF5A39F".equals(key)){
		deptIdParam = "2921";
	}else if("E80807F9111E41D1AF0B0287CAF5A39F".equals(key)){
		deptIdParam = "2923";
	}else if("111807F921AE41D1AF0B0287CAF5A39F".equals(key)){
		deptIdParam = "2924";
	}else if("E87607F921AE41D1AF0B0287CAF5A39F".equals(key)){
		deptIdParam = "2920";
	}else if("E80345F921AE41D1AF0B0287CAF5A39F".equals(key)){
		deptIdParam = "2925";
	}else if("111807EWQEWE41D1AF0B0287CAF5A39F".equals(key)){
		deptIdParam = "2959";
	}else if("111807F921AE4QWEEWQB0287CAF5A39F".equals(key)){
		deptIdParam = "2945";
	}else if("111807F92QWE41D1AF0B0287CAF5A39F".equals(key)){
		deptIdParam = "2943";
	}else if("11QWERRR21AE41D1AF0B0287CAF5A39F".equals(key)){
		deptIdParam = "2941";
	}else if("111807F921AQWER1AF0B0287CAF5A39F".equals(key)){
		deptIdParam = "2922";
	}else if("111807F922AQWER8880B0287CAF5A39F".equals(key)){
		deptIdParam = "2946";
	}else if("000807F921AQWER1AF0B0287CAF5A39F".equals(key)){
		deptIdParam = "2944";
	}else if("232137F921AQWER1AF0B0287CAF5A39F".equals(key)){
		deptIdParam = "2962";
	}
	
	
	String appName = "";
	String urlCa = "";
	Properties properties = new Properties();
	String configPath = Thread.currentThread().getContextClassLoader().getResource("config.properties").getPath();
	try {
		FileInputStream fis = new FileInputStream(configPath);
		properties.load(fis);
		appName = properties.getProperty("appName");
		urlCa = properties.getProperty("urlCa");
		fis.close();
	} catch (Exception e) {
	}
	
	String path = request.getContextPath();
	String returnUrl=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ path+"/dataExchange/view.action?id=";
	if(request.getQueryString()!=null){   
		//returnUrl+="?"+req.getQueryString();
	}
	//appName = "workflow4816";
	
	
%>


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
	<style>
		body{font-family:"Helvetica Neue", "Microsoft YaHei", Arial, "Liberation Sans", FreeSans, sans-serif;}
		</style>
  	<script>
  	 var intHand=null;
		var rtn=null;	
		function checkWin(){
			if(rtn!=null && rtn.closed){
				clearInterval(intHand);
				intHand=null;
				rtn=null;
				window.location.replace(window.location.href);
			}
		}
		
		
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
		
		$(".todoUrl").click(function(){
			var url = $(this).attr("data-url");  		
    		rtn = window.open(url,"workflowtodo");
    		rtn.focus();
			intHand = setInterval("checkWin()",3000);
			return false;
    	});
    	
		$("#myModal").width($(document).width()/3);
		$("#myModal").height($(document).height()/1.5);
  	})
  	
  
  	</script>
<%!
	public String oracleClob2Str(Clob clob) throws Exception {
    return (clob != null ? clob.getSubString(1, (int) clob.length()) : null);
}
%>  	
<%
	UserInfo bo = LoginUtil.getUserInfo(session);
	
	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	if(!"".equals(deptIdParam)){
		list = DataExchangeUtil.tempList(deptIdParam);
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
      	    </div>
        	</div>     
		      <div class="fn clearfix">
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
	                  <a 	data-url="<%=getUrl(id,
	                		  loginName.replace("ST/", "").substring(0,12),
	                		  deptId,
	                		  appName,urlCa,returnUrl+id)%>"
	                  		href="javascript:void(0);" class="todoUrl"><img src="<%=path %>/dataExchange/css/default/images/p_open.gif"></a>
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
