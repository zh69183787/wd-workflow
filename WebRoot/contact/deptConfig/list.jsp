<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.wonders.util.*"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
%>
<s:set var="page" value="#request.page"></s:set>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>多级工作联系单配置列表</title>
<!--[if IE 6.0]>
<script src="js/iepng.js" type="text/javascript"></script>
<script type="text/javascript">
EvPNG.fix('div, ul, ol, img, li, input, span, a, h1, h2, h3, h4, h5, h6, p, dl, dt');
</script>
<![endif]-->
<script src="<%=path%>/contact/js/html5.js"></script>
<script src="<%=path%>/contact/js/jquery-1.7.1.js"></script>
<!--<script src="../js/iepng.js"></script>-->
<script src="<%=path%>/contact/js/jquery.formalize.js"></script>
<script type="text/javascript" src="jquery.ui/js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript" src="<%=path%>/contact/js/contextpath.js"></script>
<script type="text/javascript" src="<%=path%>/contact/js/form/jquery.form.js"></script>
<script type="text/javascript" src="js/list.js"></script>
<script type="text/javascript" src="js/handle.js"></script>
<script type="text/javascript" src="<%=path%>/contact/js/json2.js"></script>
<link type="text/css" href="jquery.ui/css/flick/jquery-ui-1.8.23.custom.css" rel="stylesheet" />
<link rel="stylesheet" href="<%=path%>/contact/css/formalize.css" />
<link rel="stylesheet" href="<%=path%>/contact/css/page.css" />
<link rel="stylesheet" href="<%=path%>/contact/css/imgs.css" />
<link rel="stylesheet" href="<%=path%>/contact/css/reset.css" />
<script>
//var gg = "<%=path%>";
//alert(gg);
</script>
</head>
<body>
    <div class="main">
        <!--Ctrl-->
        <div class="ctrl clearfix">
            <div class="fl">
                <img src="<%=path%>/contact/images/sideBar_arrow_left.jpg" width="46" height="30" alt="收起">
            </div>
            <div class="posi fl">
                <ul>
                    <li><a href="#">多级工作联系单查询列表</a></li>
                    <li class="fin"></li>
                </ul>
            </div>
            <!--  
            <div class="fr lit_nav">
                <ul>
                    <li class="selected">
                        <a class="print" href="#">打印</a>
                    </li>
                    <li>
                        <a class="query" href="#">查询</a>
                    </li>
                </ul>
            </div>
            -->
        </div>
        <!--Ctrl End-->
        <!--Tabs_2
        <div class="tabs_2">
            <s:if test="hideOrShowParam=='show'">
                <input type="button" id="hideOrShow" value="隐藏搜索" class="new" onclick="hideOrShow(this)" style="float: right;">
            </s:if>
            <s:else>
                <input type="button" id="hideOrShow" value="显示搜索" class="new" onclick="hideOrShow(this)" style="float: right;">
            </s:else>
        </div>
       Tabs_2 End-->
         <%-- 操作页面--%>
		<jsp:include page="handle.jsp"></jsp:include>
        <!--Filter-->
        <div class="filter">
            <div class="query p8">
            	<form name="listForm" id="listForm" method="post">
                    <input type="hidden" name="currentPage" id="currentPage"/>
                    <input type="hidden" name="pageMethod" id="pageMethod"/>
                    <input type="hidden" name="rand" id="rand"/>
                   
                    
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td class="t_r">下级部门名称</td>
                            <td>
                                <input type="text" value="" name="deptName" class="input_large" id="deptName" />
                            </td>
                            <td class="t_r">下级部门接收人</td>
                            <td>
                                <input type="text" value="" name="userName" class="input_large" id="userName" />
                            </td>
                        </tr>
                        <tr>
                            <td colspan="4" class="t_c">
                                <input id="search" type="button" value="检 索" />
                                &nbsp;&nbsp;
                                <input type="button" value="重 置" onclick="clearData();" />
                                <div id="formUpdate_loading" style="width:100%;align:center;line-height:20px;display:none;" class="">
							      <p style="width:auto;display:inline;"><img src="<%=path %>/contact/images/loading.gif" style="display:inline;"/>
							      <b style="color:green;display:inline;">&nbsp;正在提交</b></p>
							    </div>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            <div class="fn clearfix">
				<input type="button" name="create" id="create" value="create" class="fr mr5">
			</div>
        </div>
        <!--Filter End-->
        
        <!--Table-->
        <div class="mb10">
            <table width="100%" class="table_1" id="configTable">
                <thead>
                    <th colspan="15">多级工作联系单</th>
                </thead>
                <tbody>
                    <tr class="tit" id="first">
                    	<td>流程名称</td>
                    	<td>流程节点</td>
                        <td>下级部门名称</td>
                        <td>下级部门接收人</td>
                        <td>状态</td>
                        <td>操作</td>
                    </tr>
                	
	                <tr class="tfoot">
	                    <td colspan="11">
	                        <div class="clearfix">
	                            <span class="fl" id="configTotal">
	                            </span>
	                            <ul class="fr clearfix pager">
	                                <li>
	                                    Pages:
	                                    <font style="display:inline;" id="pageShow"></font>
	                                    <input type="text" id="pageCnt" name="pageCnt" min="0 " max="999" step="1 " class="input_tiny " value=""/>
	                                    <input type="button" " name="button " id="button" value="Go" onclick="goPage('go') ">
	                                </li>
	                                    
	                                <li><a href="javascript:void(0)" onclick="goPage('last')">尾页</a></li>
		                                
	                                <li><a href="javascript:void(0)" onclick="goPage('next')">下一页</a></li>
	                                
	                                <li><a href="javascript:void(0)" onclick="goPage('previous')">上一页</a></li>
	                                
	                                <li><a href="javascript:void(0)" onclick="goPage('first')">首页</a></li>
	                                
	                            </ul>
	                        </div>
	                    </td>
	                </tr>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
