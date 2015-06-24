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
<meta http-equiv="x-ua-compatible" content="IE=8">
<title>多级工作联系单查询列表</title>
<link rel="stylesheet" href="<%=path %>/contact/css/formalize.css" />
<link rel="stylesheet" href="<%=path %>/contact/css/page.css" />
<link rel="stylesheet" href="<%=path %>/contact/css/imgs.css" />
<link rel="stylesheet" href="<%=path %>/contact/css/reset.css" />
<!--[if IE 6.0]>
<script src="js/iepng.js" type="text/javascript"></script>
<script type="text/javascript">
EvPNG.fix('div, ul, ol, img, li, input, span, a, h1, h2, h3, h4, h5, h6, p, dl, dt');
</script>
<![endif]-->
<script src="<%=path %>/contact/js/html5.js"></script>
<script src="<%=path %>/contact/js/jquery-1.7.1.js"></script>
<!--<script src="../js/iepng.js"></script>-->
<script src="<%=path %>/contact/js/jquery.formalize.js"></script>
<!--  
<script src="../js/My97DatePicker/WdatePicker.js"></script>
-->
<link type="text/css" href="<%=path %>/contact/js/datepicker/css/flick/jquery-ui-1.8.18.custom.css" rel="stylesheet"/>

<script type="text/javascript" src="<%=path %>/contact/js/datepicker/js/jquery-ui-1.8.18.custom.min.js"></script>
<script type="text/javascript" src="<%=path %>/contact/js/ui/i18n/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" src="<%=path %>/contact/js/contextpath.js"></script>

<script type="text/javascript">
    $(document).ready(function() {
        var $tbInfo = $(".filter .query input:text");
        $tbInfo.each(function() {
            $(this).focus(function() {
                $(this).attr("placeholder", "");
            });
        });

        $(".table_1 tbody tr:even").css("background", "#fafafa");

        $('#contact_date_start').datepicker({
    		changeYear:true,
    		changeMonth:true
    	});
    	
    	$('#contact_date_end').datepicker({
    		changeYear:true,
    		changeMonth:true
    	});

    	$('#reply_date_start').datepicker({
    		changeYear:true,
    		changeMonth:true
    	});
    	
    	$('#reply_date_end').datepicker({
    		changeYear:true,
    		changeMonth:true
    	});

		var status = "<s:property value='status'/>";
		if(status!=""){
			$("form:first").find("select>option[value='"+status+"']").attr("selected",true);
		}

		var data_table = $("#data_table");
		
		var limit = 25;
		$(data_table).find("tr[name='data_tr']").find("td:not(:first,:last)").each(function(i,n){
			var text = $(n).html();
			if(text.length>limit){
				//console.log($(n));
				$(n).html(text.substring(0,limit)+"...");
				$(n).attr("title",text);
			}
		});
    });
	
    function goPage(type) {
	    $("#pageMethod").val(type);
	    $("#rand").val(Math.random());

		if(type=="go"){
			$("#currentPage").val($("#pageCnt").val())
		}

        $("#form").submit();
		
    }

    //清除数据
    function clearData() {
        var form = $("form:first");
        $(form).find("input:text").val("");
        $(form).find("select>option:first").attr("selected",true);
    }

    //默认隐藏搜索
    $(document).ready(function() {
        if ($("#hideOrShow").val() == "显示搜索") {
            $(".filter").css("display", "none");
        }
    });

    //隐藏或显示搜索
    function hideOrShow(obj) {
        if ($(obj).val() == "显示搜索") { //展开搜索
            $(obj).val("隐藏搜索");
            $("#hideOrShowParam").val("show");
            $(".filter").slideDown();
        } else if ($(obj).val() == "隐藏搜索") { //隐藏搜索
            $(obj).val("显示搜索");
            $("#hideOrShowParam").val("hide");
            $(".filter").slideUp();
        }
    }

    function openFormFinish(id){
		var url = "<%=path %>/contact-deptContact/viewForward.action?"
			+"id="+id+"&"
			+"rand="+Math.random();
		window.open(url);
		return false;
  	}

</script>
<!-- -->
</head>
<body>
    <div class="main">
        <!--Ctrl-->
        <div class="ctrl clearfix">
            <div class="fl">
                <img src="<%=path %>/contact/images/sideBar_arrow_left.jpg" width="46" height="30" alt="收起">
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
        <!--Tabs_2-->
        <div class="tabs_2">
            <s:if test="#request.hideOrShowParam=='show'">
                <input type="button" id="hideOrShow" value="隐藏搜索" class="new" onclick="hideOrShow(this)" style="float: right;">
            </s:if>
            <s:else>
                <input type="button" id="hideOrShow" value="显示搜索" class="new" onclick="hideOrShow(this)" style="float: right;">
            </s:else>
        </div>
        <!--Tabs_2 End-->
        <!--Filter-->
        <div class="filter">
            <div class="query p8">
            	<form action="<%=path %>/contact-deptContact/queryList.action" method="post" id="form">
                    <input type="hidden" id="hideOrShowParam" value="<s:property value='hideOrShowParam'/>" name="hideOrShowParam"/>
                    <input type="hidden" name="currentPage" id="currentPage" value="<s:property value="#page.currentPage"/>"/>
                    <input type="hidden" name="pageMethod" id="pageMethod"/>
                    <input type="hidden" name="rand" id="rand"/>
                    
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td class="t_r">主送单位</td>
                            <td>
                                <input type="text" value="<s:property value='main_unit'/>" name="main_unit" class="input_large" id="main_unit" />
                            </td>
                            <td class="t_r">抄送单位</td>
                            <td>
                                <input type="text" value="<s:property value='copy_unit'/>" name="copy_unit" class="input_large" id="copy_unit" />
                            </td>
                            <td class="t_r">联系日期</td>
                            <td>
                               <input type="text" id="contact_date_start" name="contact_date_start" class="input_large date" value="<s:property value='contact_date_start'/>" readonly="readonly"/>
                               -
                               <input type="text" id="contact_date_end" name="contact_date_end" class="input_large date" value="<s:property value='contact_date_end'/>" readonly="readonly" />
                            </td>
                        </tr>
                        <tr>
                        	<td class="t_r">状态</td>
                            <td>
                            	<select name="status" id="status" class="input_large">
                                    <option value="">---请选择---</option>
                                    <option value="0">进行中</option>
                                    <option value="1">已完成</option>
                                </select>
                            </td>
                            
                            <td class="t_r">主题</td>
                            <td>
                                <input type="text" id="theme" name="theme" value="<s:property value='theme'/>" class="input_large" />
                            </td>
                            
                            <td class="t_r">要求回复日期</td>
                            <td>
                               <input type="text" id="reply_date_start" name="reply_date_start" class="input_large date" value="<s:property value='reply_date_start'/>" readonly="readonly"/>
                               -
                               <input type="text" id="reply_date_end" name="reply_date_end" class="input_large date" value="<s:property value='reply_date_end'/>" readonly="readonly" />
                            </td>
                        </tr>
                        <tr>
                            <td colspan="6" class="t_c">
                                <input type="submit" value="检 索" onclick="" />
                                &nbsp;&nbsp;
                                <input type="button" value="重 置" onclick="clearData();" />
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
        <!--Filter End-->
        <!--Table-->
        <div class="mb10">
            <table width="100%" class="table_1" id="data_table">
                <thead>
                    <th colspan="15">多级工作联系单</th>
                </thead>
                <tbody>
                    <tr class="tit">
                        <td>主题</td>
                        <td>主送单位</td>
                        <td>抄送单位</td>
                        <td>联系时间</td>
                        <td>要求回复时间</td>
                        <td>发起人</td>
                        <td>发起部门</td>
                        <td>完成状态</td>
                        <td>查看</td>
                        <td></td>
                    </tr>
					
                    <s:iterator value="#request.page.result" var="item">
                        <tr name="data_tr">
                            <td class="t_c"><s:property value="#item[5]"/></td>
                            <td class="t_c"><s:property value="#item[1]"/></td>
                            <td class="t_c"><s:property value="#item[2]"/></td>
                            <td class="t_c"><s:property value="#item[3]"/></td>
                            <td class="t_c"><s:property value="#item[4]"/></td>
                            <td class="t_c"><s:property value="#item[6]"/></td>
                            <td class="t_c"><s:property value="#item[7]"/></td>
                            <td class="t_c"><s:property value="statusMap[#item[8]]"/></td>
                            <td class="t_c">
                                <a onclick="return openFormFinish('<s:property value="#item[0]"/>');" target="_blank" href="javascript:void(0)" />查看</a>
                            </td>
                        </tr>
					</s:iterator>
                
	                <tr class="tfoot">
	                    <td colspan="11">
	                        <div class="clearfix">
	                            <span class="fl">
	                                <s:property value="#page.totalRows" /> 条记录，当前显示
									<s:property value="#page.startRow+1" />-
	                                <s:if test="#page.totalPages==#page.currentPage">
	                                    <s:property value="#page.totalRows" />条
	                                </s:if>
	                                <s:else>
	                                    <s:property value="#page.startRow+#page.pageSize" />条
	                                </s:else>
	                            </span>
	                            <ul class="fr clearfix pager">
	                                <li>
	                                    Pages:
	                                    <s:property value="#page.currentPage" />/<s:property value="#page.totalPages" />
	                                    <input type="text" id="pageCnt" name="pageCnt" min="0 " max="999" step="1 " class="input_tiny " value="<s:property value='#page.currentPage'/>"/>
	                                    <input type="button" " name="button " id="button" value="Go" onclick="goPage('go') ">
	                                </li>
	                                    
	                                <s:if test="#page.currentPage==#page.totalPages">
	                                  <li><a href="javascript:void(0)">&gt;&gt;</a></li>
	                                </s:if>
	                                <s:else>
	                                  <li><a href="javascript:void(0)" onclick="goPage('last')">&gt;&gt;</a></li>
	                               	</s:else>
		                                
	                                <li>
	                                    <s:if test="#page.currentPage==#page.totalPages">
	                                        <!-- <a href="javascript:void(0)">下一页</a> -->
	                                    </s:if>
	                                    <s:else>
	                                        <a href="javascript:void(0)" onclick="goPage('next')">下一页</a>
	                                    </s:else>
	                                </li>
	                                
	                                <li>
	                                    <s:if test="#page.currentPage==1">
											 <!--<a href="javascript:void(0)">上一页</a> -->
	                                    </s:if>
	                                    <s:else>
	                                        <a href="javascript:void(0)" onclick="goPage('previous')">上一页</a>
	                                    </s:else>
	                                </li>
	                                
	                                <s:if test="#page.totalPages==1">
	                                    <li><a href="javascript:void(0)">&lt;&lt;</a></li>
	                                </s:if>
	                                <s:else>
	                                    <li><a href="javascript:void(0)" onclick="goPage('first')">&lt;&lt;</a></li>
	                                </s:else>
	                                
	                            </ul>
	                        </div>
	                    </td>
	                </tr>
                </tbody>
            </table>
        </div>
        <%--
        <div class="t_c">
				<input type="button" value="确定" id="submit_ref" onclick="submit_ref();">
               	&nbsp;
               	<input type="button" value="取消" id="cancel_ref" onclick="cancel_ref();">
            </div>
        <!--Table End-->
        --%>
    </div>
</body>
</html>
