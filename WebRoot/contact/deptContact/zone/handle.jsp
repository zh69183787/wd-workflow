<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.wonders.contact.deptContact.constant.*"%>
<%@page import="com.wonders.util.*"%>
<% 
String steplabel = StringUtil.getNotNullValueString(request.getParameter("steplabel"));
if(steplabel.length()==0) steplabel="detail";
String path = request.getContextPath();
String flowType = StringUtil.getNotNullValueString(request.getParameter("flowType"));
%>

<%if(steplabel.equals(DeptContactFlowConstants.STEPNAME_APPLY)){%>
<div id="handle_zone" class="f_window" style="display:none;">
   	  <h3 class="clearfix mb10">
   	  	<span id="" class="fl">申请</span>
   	 	<span class="fr close" id="handleDivClose">关闭窗口</span>
   	  </h3>
        <div class="con">
			<table id="choiceZone" width="100%" cellspacing="0" cellpadding="0" border="0">
              <tr>
                <td class="td_1"><input type="radio" checked="checked" name="choice" value="<%=DeptContactConstants.CHOICE_APPLY_TO_SIGN%>"/></td>
                <td>部门领导签发</td>
              </tr>
              <tr id="sign_leader_div">
                <td class="td_1" style="text-align:right;">&nbsp;<font style="color: red;display:inline;">*</font></td>
                <td>请
                	<select name="SIGN_LEADER" id="SIGN_LEADER" class="input_large">
						<option value="">---请选择---</option>
					</select>
					签发&nbsp;
					<script>
					$(document).ready(function(){
						//if(typeof signLeader!='undefined'&&typeof(signLeader)=="function"){
						signLeader("SIGN_LEADER","");
						//}
					});
					</script>
                </td>
              </tr>
              
			</table>
              
			
			
				
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<%if(!DeptContactConstants.STATUS_LOWER_STR.equals(flowType)){%>
				<tr>
					<td class="td_1"><input type="radio" name="choice" value="<%=DeptContactConstants.CHOICE_APPLY_TO_CANCEL%>"/></td>
					<td>取消工作联系单</td>
				</tr>
				<%}%>
				<tr>
					<td class="td_1">&nbsp;</td>
					<td><span class="fl">意见</span></td>
				</tr>
				<tr>
					<td class="td_1">&nbsp;</td>
					<td><textarea rows="5" name="suggest" id="suggest"></textarea></td>
				</tr>
			</table>
            
            
      </div>
      <div class="button t_c">
       	<input id="handleSubmit" type="button" value="确 认">
		&nbsp;
		<input id="handleClose" type="button" value="取 消">
		
		<div id="handle_zone_loading" style="width:100%;align:center;line-height:20px;display:none;" class="">
	      <p style="width:auto;display:inline;"><img src="<%=path %>/contact/images/loading.gif" style="display:inline;"/>
	      <b style="color:green;display:inline;">&nbsp;正在提交</b></p>
	    </div>
      </div>
</div>
<%
	}
%>


<%if(steplabel.equals(DeptContactFlowConstants.STEPNAME_SIGN)){%>
<div id="handle_zone" class="f_window" style="display:none;">
   	  <h3 class="clearfix mb10"><span id="" class="fl">部门内部签发</span>
   	  <span class="fr close" id="handleDivClose">关闭窗口</span>
   	  </h3>
        <div class="con">
        	<table width="100%" cellspacing="0" cellpadding="0" border="0"><tbody>
              <tr>
                <td class="td_1"><input type="radio" checked name="choice" value="<%=DeptContactConstants.CHOICE_SIGN_AGREE%>" onclick="hideRedMark('suggestRedMark');"/></td>
                <td>同意签发</td>
              </tr>
              <tr>
                <td class="td_1"><input type="radio" name="choice" value="<%=DeptContactConstants.CHOICE_SIGN_TO_APPLY%>" onclick="hideRedMark('suggestRedMark');"/></td>
                <td>返回修改</td>
              </tr>
              
              <%if(DeptContactConstants.STATUS_LOWER_STR.equals(flowType)){%>
              <tr>
                <td class="td_1"><input type="radio" name="choice" value="<%=DeptContactConstants.CHOICE_SIGN_FINISH%>" onclick="showRedMark('suggestRedMark');"/></td>
                <td>结束下级流程</td>
              </tr>
              <%}%>
              
              <tr>
                <td class="td_1" style="text-align:right;">&nbsp;<font id="suggestRedMark" class="redMark">*</font>
                <script>
                $(document).ready(function(){
					$("input[name='choice']:checked").click();					
                });
                </script>
                </td>
                <td><span class="fl">意见</span></td>
              </tr>
              
              <tr>
                <td class="td_1">&nbsp;</td>
                <td><textarea rows="5" name="suggest" id="suggest"></textarea></td>
              </tr>
            </tbody></table>
      </div>
      &nbsp;
      
      <div class="button t_c">

       	<input id="handleSubmit" type="button" value="确 认">
		&nbsp;
		<input id="handleClose" type="button" value="取 消">
		
		<div id="handle_zone_loading" style="width:100%;align:center;line-height:20px;display:none;" class="">
	      <p style="width:auto;display:inline;"><img src="<%=path %>/contact/images/loading.gif" style="display:inline;"/>
	      <b style="color:green;display:inline;">&nbsp;正在提交</b></p>
	    </div>

		
      </div>
</div>
<%} %>
    