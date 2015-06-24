<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.wonders.contact.deptSubProcess.constant.*"%>
<%@page import="com.wonders.util.*"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<% 
String steplabel = StringUtil.getNotNullValueString(request.getParameter("steplabel"));
if(steplabel.length()==0) steplabel="detail";
String deptId = StringUtil.getNotNullValueString(request.getParameter("deptId"));
String path = request.getContextPath();
String flowType = StringUtil.getNotNullValueString(request.getParameter("flowType"));
%>
    
    
<%if(steplabel.equals(DeptSubProcessFlowConstants.STEPNAME_DISPATCHER)){%>
<div id="handle_zone" class="f_window" style="display:none;">
	<font id="deptUserTreeZone" class="deptTreeZone" root='<s:property value="params.userInfo.deptId"/>'  checkNode="dealPersonIdStr" nodeLoginName="dealPersonStr"
	nodeId="dealPersonIdStr" nodeName="dealPersonNames"></font>
	<font id="deptLeaderTreeZone" class="deptTreeZone" root='<s:property value="params.userInfo.deptId"/>' checkNode="dealLeaderIdStr" nodeLoginName="dealLeaderStr"
	nodeId="dealLeaderIdStr" nodeName="dealLeaderNames"></font>
	<h3 class="clearfix mb10">
		<span class="fl">部门业务处理员/收发员</span>
		<div class="fr close" id="handleDivClose">关闭窗口</div>
	</h3>
	<div class="con">
     	
		<table id="choiceZone1" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr height="30">
				<td class="td_1">
					<input type="radio" checked="checked" name="choice" value="<%=DeptSubProcessConstants.CHOICE_DISPATCHER_SEND %>"/>
				</td>
				<td>业务转发（不进行具体业务处理）</td>
			</tr>
	           	<tr height="30">
	           		<td></td>
	  			<td>
	  				选择人员：
	  				<input type="text" id="dealPersonNames" name="dealPersonNames" value="" readonly class="inputLine" style="width:250px;height:22px"/>
	  				<input type="hidden" id="dealPersonStr" name="dealPersonStr" value="">
	  				<input type="hidden" id="dealPersonIdStr" name="dealPersonIdStr" value="">
	  				<input type="button" name="choosePerson" value="..." class="btn">
	  				<input type="button" name="clearPerson" value="清除" class="btn"/>
	  			</td>
					</tr>
	  		<tr height="30">
	  			<td style="text-align:right;"><font style="color:red;">*</font></td>
	  			<td>
	  				选择领导：
	  				<input type="text" id="dealLeaderNames" name="dealLeaderNames" value="" readonly class="inputLine" value="" style="width:250px;height:22px"/>
	  				<input type="hidden" id="dealLeaderStr" name="dealLeaderStr" value="">
	  				<input type="hidden" id="dealLeaderIdStr" name="dealLeaderIdStr" value="">
	  				<input type="button" name="chooseLeader" value="..." class="btn"/>
	  				<input type="button" name="clearLeader" value="清除" class="btn"/>
	  			</td>
	  		</tr>
	  		
	  		<tr>
	  			<td colspan=2>
			  		<table id="suggest1" width="100%" border="0" cellspacing="0" cellpadding="0">
				  		<tr>
				  			<td class="td_1" style="text-align:right;">&nbsp;<font style="color:red;display:inline;">*</font></td>
		                	<td textareaId="suggest1" peopleId="choiceZone1">
		                	<span class="fl">
		                	意见：</span>
		                	<span class="fr" style="display:inline;"><a class="createSuggest">生成意见</a></span>
		                	<!--  
		                	<span class="fr" style="display:inline;"><a class="previewSuggest">意见预览</a></span>
		                	-->
		                	</td>
		              	</tr>
		              	<tr>
		              		<td class="td_1">&nbsp;</td>
		                	<td><textarea id="suggest" name="suggest" rows="3"></textarea></td>
		              	</tr>
	              	</table>
           		</td>
           	</tr>
		</table>
		
		<table id="choiceZone2" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr height="30">
				<td class="td_1">
					<input type="radio" name="choice" value="<%=DeptSubProcessConstants.CHOICE_DISPATCHER_DEAL %>"/>
				</td>
				<td>业务处理</td>
			</tr>
			<tr>
				<td class="td_1">&nbsp;</td>
				<td>
					<table id="choiceZone2_1" width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr height="30">
							<td class="td_1">
								<input type="radio" name="choice2" value="<%=DeptSubProcessConstants.CHOICE_DISPATCHER_DEAL_NO_SUGGEST %>" checked/></td>
				  			<td style="width:99%;">本人没有意见</td>
				  		</tr>
				  		<tr>
					  		<td colspan=2>
						  		<table id="suggest2_1" width="100%" border="0" cellspacing="0" cellpadding="0">
							  		<tr>
							  			<td class="td_1">&nbsp;</td>
							  			<td textareaId="suggest2_2" peopleId="choiceZone2">
							  				<span class="fl" >备注：</span>
							  				
							  				<span class="fr" style="display:inline;">
							  				<a name="suggest_attach" class="suggest_attach" target="#">
							  				<input type="hidden" name="attachId" id="attachId_<%=DeptSubProcessConstants.CHOICE_DISPATCHER_DEAL_NO_SUGGEST %>" value=""/>
							  				上传意见附件(<span style="display:inline;" id="fjcount_<%=DeptSubProcessConstants.CHOICE_DISPATCHER_DEAL_NO_SUGGEST %>" class="fjcount">0</span>)
							  				</a>
							  				<!--
							  				<a class="previewSuggest">备注预览</a>
							  				-->
							  				</span>
							  			</td>
							  		</tr>
							  		<tr>
							  			<td class="td_1">&nbsp;</td>
					                	<td>
					                	<textarea id="suggest" name="suggest" rows="3"></textarea></td>
					              	</tr>
				              	</table>
			              	</td>
		              	</tr>
				  	</table>
				  	
				  	<table id="choiceZone2_2" width="100%" border="0" cellspacing="0" cellpadding="0">
				  		<tr height="30">
				  			<td class="td_1">
				  				<input type="radio" name="choice2" value="<%=DeptSubProcessConstants.CHOICE_DISPATCHER_DEAL_HAS_SUGGEST %>"/></td>
				  			<td style="width:99%;">本人有新意见</td>
		  				</tr>
		  				<tr><td colspan=2>
			  				<table id="suggest2_2" width="100%" border="0" cellspacing="0" cellpadding="0">
				  				<tr>
				  					<td class="td_1" style="text-align:right;">&nbsp;<font style="color:red;display:inline;">*</font></td>
						  			<td textareaId="suggest2_2" peopleId="choiceZone2">
						  				<span class="fl">
						  				意见:
						  				</span>
						  				<span class="fr" style="display:inline;">
							  				<a class="createSuggest"/>生成意见</a>
							  				
								  			<a name="suggest_attach" class="suggest_attach" target="#">
								  			<input type="hidden" name="attachId" id="attachId_<%=DeptSubProcessConstants.CHOICE_DISPATCHER_DEAL_HAS_SUGGEST %>" value=""/>
							  				上传意见附件(<span style="display:inline;" class="fjcount" id="fjcount_<%=DeptSubProcessConstants.CHOICE_DISPATCHER_DEAL_HAS_SUGGEST %>">0</span>)
							  				</a>
							  				<!--
							  				<a class="previewSuggest">意见预览</a>
							  				-->
						  				</span>
						  			</td>
						  		</tr>
						  		<tr>
						  			<td class="td_1">&nbsp;</td>
				                	<td>
				                	<textarea id="suggest" name="suggest" rows="3"></textarea></td>
				              	</tr>
			              	</table>
		              	</td></tr>
				  	</table>
				  	
				  	<table width="100%" border="0" cellspacing="0" cellpadding="0">
				  		<tr height="30">
				  			<td></td>
				  			<td>下一步处理人员： </td>
		  				</tr>
		  				
				  		<tr height="30">
				  			<td></td>
				  			<td>
				  				选择人员：
				  				<input type="text" id="dealPersonNames" name="dealPersonNames" value="" readonly class="inputLine" style="width:250px;height: 22px"/>
				  				<input type="hidden" id="dealPersonStr" name="dealPersonStr" value="">
				  				<input type="hidden" id="dealPersonIdStr" name="dealPersonIdStr" value="">
				  				<input name="choosePerson" type="button" value="..." class="btn"/>
				  				<input type="button" name="clearPerson" value="清除" class="btn" />
				  			</td>
				  		</tr>
				  		
				  		<tr height="30">
				  			<td style="text-align:right;"><font style="color:red;">*</font></td>
				  			<td> 
								选择领导：
								<input type="text" id="dealLeaderNames" name="dealLeaderNames" value="" readonly class="inputLine" style="width:250px;height: 22px"/>
				  				<input type="hidden" id="dealLeaderStr" name="dealLeaderStr" value="">
				  				<input type="hidden" id="dealLeaderIdStr" name="dealLeaderIdStr" value="">
				  				<input name="chooseLeader" type="button" value="..." class="btn"/>
				  				<input type="button" name="clearLeader" value="清除" class="btn"/>
				  			</td>
				  		</tr>
		  			</table>
		  		</td>
		  	</tr>
	  		<!--  
            <tr><td class="td_1" >操作栏</td></tr>
           	<tr><td><textarea id="operate_msg" name="operate_msg" rows="2" readonly>(会签)提交至部门领导处理或转发部门业务人员处理。</textarea></td></tr>
      	  	-->
		</table>
	</div>
	<div class="button t_c">
		<input id="handleSubmit" type="button" value="确 认">
		<input id="handleClose" type="button" value="取 消">
		
		<div id="handle_zone_loading" style="width:100%;align:center;line-height:20px;display:none;" class="">
	      <p style="width:auto;display:inline;"><img src="<%=path %>/contact/images/loading.gif" style="display:inline;"/>
	      <b style="color:green;display:inline;">&nbsp;正在提交</b></p>
	    </div>
	</div>
</div>
<%}%>

<%if(steplabel.equals(DeptSubProcessFlowConstants.STEPNAME_DEAL)){%>
<div id="handle_zone" class="f_window" style="display:none;">
	<h3 class="clearfix mb10"><span class="fl">部门业务人员处理</span>
		<div class="fr close" id="handleDivClose">关闭窗口</div>
	</h3>
	<div class="con">
        
		<table id="choiceZone1" width="100%" border="0" cellspacing="0" cellpadding="0">
			<!--<tr><td class="td_1" >业务栏</td></tr>-->
			<tr height="30">
				<td class="td_1"><input type="radio" name="choice" value="<%=DeptSubProcessConstants.CHOICE_DEAL_NO_SUGGEST %>"/></td>
	  			<td style="width:99%;">本人没有意见 </td>
	  		</tr>
	  		<tr>
	  			<td colspan=2><table id="suggest1" width="100%" border="0" cellspacing="0" cellpadding="0" border=1><tbody>
			  		<tr>
			  			<td class="td_1">&nbsp;</td>
			        	<td textareaId="suggest1">
			        		<span style="display:inline;">备注:</span>
			        		
				        	<span class="fr" style="display:inline;">
				        	<a name="suggest_attach" class="suggest_attach" target="#">
							<input type="hidden" name="attachId" id="attachId_<%=DeptSubProcessConstants.CHOICE_DEAL_NO_SUGGEST %>" value=""/>
				        	上传意见附件(<span  style="display:inline;" id="fjcount_<%=DeptSubProcessConstants.CHOICE_DEAL_NO_SUGGEST %>" class="fjcount">0</span>)</a>
				        	<!--
				        	<a class="previewSuggest">意见预览</a></span>
				        	-->
			        	</td>
			        </tr>
			        <tr>
			        	<td class="td_1">&nbsp;</td>
			         	<td><textarea id="suggest" name="suggest" rows="3"></textarea></td>
			       	</tr>
				</tbody></table></td>
           	</tr>
		</table>
		
		<table id="choiceZone2" width="100%" border="0" cellspacing="0" cellpadding="0">

	  		<tr height="30">
	  			<td class="td_1"><input type="radio" name="choice" value="<%=DeptSubProcessConstants.CHOICE_DEAL_HAS_SUGGEST %>"/></td>
	  			<td style="width:99%;">本人有意见</td>
	  		</tr>
	  		
	  		
	  		<tr>
	  			<td colspan=2><table id="suggest2" width="100%" border="0" cellspacing="0" cellpadding="0"><tbody>
			  		<tr>
			  			<td class="td_1">&nbsp;</td>
		                <td textareaId="suggest2">
		                <font style="color: red;display:inline;">*</font>
		                	意见:
		                	
		                	<span class="fr" style="display:inline;">
			                	<a name="suggest_attach" class="suggest_attach" target="#">
								<input type="hidden" name="attachId" id="attachId_<%=DeptSubProcessConstants.CHOICE_DEAL_HAS_SUGGEST %>" value=""/>
			                	上传意见附件(<span style="display:inline;" id="fjcount_<%=DeptSubProcessConstants.CHOICE_DEAL_HAS_SUGGEST %>" class="fjcount">0</span>)</a>
			                	<!--<a class="previewSuggest">意见预览</a>-->
		                	</span>
		                	
		                </td>
		            </tr>
		  			<tr>
		  				<td class="td_1">&nbsp;</td>
		               	<td><textarea id="suggest" name="suggest" rows="3"></textarea>
		               	</td>
		            </tr>
            	</tbody></table></td>
           	</tr>
            
		</table>
		
		<table id="choiceZone3" width="100%" border="0" cellspacing="0" cellpadding="0">

	  		<tr height="30">
	  			<td class="td_1"><input type="radio" name="choice" value="<%=DeptSubProcessConstants.CHOICE_DEAL_NOT_MY_BUSINESS %>"/></td>
	  			<td style="width:99%;">非本人处理业务</td>
	  		</tr>
	  		
	  		<tr>
	  			<td colspan=2><table id="suggest3" width="100%" border="0" cellspacing="0" cellpadding="0"><tbody>
			  		<tr>
			  			<td class="td_1">&nbsp;</td>
						<td textareaId="suggest3">
							备注:
							
							<span class="fr" style="display:inline;">
								<a name="suggest_attach" class="suggest_attach" target="#">
								<input type="hidden" name="attachId" id="attachId_<%=DeptSubProcessConstants.CHOICE_DEAL_NOT_MY_BUSINESS %>" value=""/>
			                	上传意见附件(<span style="display:inline;" id="fjcount_<%=DeptSubProcessConstants.CHOICE_DEAL_NOT_MY_BUSINESS %>" class="fjcount">0</span>)</a>
								<!--<a class="previewSuggest">意见预览</a>-->
							</span>
							
						</td>
		            </tr>
			  		<tr>
			  			<td class="td_1">&nbsp;</td>
			  			<td><textarea id="suggest" name="suggest" rows="3"></textarea>
			  			</td>
			  		</tr>
	  			</tbody></table></td>
           	</tr>
	  		<!--  
	  		<tr><td>操作栏</td></tr>
            <tr><td><textarea id="operate_msg" name="operate_msg" rows="2" readonly>(会签)提交至部门领导处理。</textarea></td></tr>
       	   -->
		 </table>
	</div>
	<div class="button t_c">
		<input id="handleSubmit" type="button" value="确 认">
		<input id="handleClose" type="button" value="取 消">
		
		<div id="handle_zone_loading" style="width:100%;align:center;line-height:20px;display:none;" class="">
	      <p style="width:auto;display:inline;"><img src="<%=path %>/contact/images/loading.gif" style="display:inline;"/>
	      <b style="color:green;display:inline;">&nbsp;正在提交</b></p>
	    </div>
	</div>
</div>
<%}%>

<%if(steplabel.equals(DeptSubProcessFlowConstants.STEPNAME_LEADER)){%>
<div id="handle_zone" class="f_window" style="display:none;">
		<font id="deptUserTreeZone" class="deptTreeZone" root='<s:property value="params.userInfo.deptId"/>' checkNode="dealPersonIdStr" nodeLoginName="dealPersonStr"
		nodeId="dealPersonIdStr" nodeName="dealPersonNames"></font>
		<font id="deptLeaderTreeZone" class="deptTreeZone" root='<s:property value="params.userInfo.deptId"/>' checkNode="dealLeaderIdStr" nodeLoginName="dealLeaderStr"
		nodeId="dealLeaderIdStr" nodeName="dealLeaderNames"></font>

   	  <h3 class="clearfix mb10"><span class="fl">部门领导审核</span>
   	  	<div class="fr close" id="handleDivClose">关闭窗口</div>
   	  </h3>
      <div class="con">
        	<table id="choiceZone1" width="100%" border="0" cellspacing="0" cellpadding="0">
        	  	<tr height="30">
	  				<td class="td_1">
	  					<input type="radio" name="choice" value="<%=DeptSubProcessConstants.CHOICE_LEADER_PASS %>"/>
	  				</td>
	  				<td style="width:99%;">审核通过(形成部门意见)</td>
  				</tr>
  				
  				<tr>
	  				<td colspan=2><table id="suggest1" width="100%" border="0" cellspacing="0" cellpadding="0"><tbody>
		  				<tr>
		                	<td>
		                		备注：
			                	<span class="fr" style="display:inline;">
				                	<a name="suggest_attach" class="suggest_attach" target="#">
									<input type="hidden" name="attachId" id="attachId_<%=DeptSubProcessConstants.CHOICE_LEADER_PASS %>" value=""/>
				                	上传意见附件(<span style="display:inline;" id="fjcount_<%=DeptSubProcessConstants.CHOICE_LEADER_PASS %>" class="fjcount">0</span>)</a>
				                	<!--<a class="previewSuggest">备注预览</a>-->
			                	</span>
		                		
		                	</td>
		              	</tr>
		              	<tr>
		                	<td><textarea id="suggest" name="suggest" rows="3"></textarea></td>
		              	</tr>
	              	</tbody></table>
	              	</td>
              	</tr>
              	
       	    </table>
       	    
       	    <table id="choiceZone2" width="100%" border="0" cellspacing="0" cellpadding="0">
	  			<tr height="30">
	  				<td class="td_1">
	  					<input type="radio" name="choice" value="<%=DeptSubProcessConstants.CHOICE_LEADER_FAILED %>"/>
	  				</td>
	  				<td style="width:99%;">审核不通过,有新意见(形成部门意见) </td>
	  			</tr>
	  			
	  			<tr>
  					<td colspan=2>
	  					<table id="suggest2" width="100%" border="0" cellspacing="0" cellpadding="0">
		  					<tbody>
				  				<tr>
				                	<td>
					                	<font style="color: red;display:inline;">*</font>
					                	意见：
					                	
					                	<span  class="fr" style="display:inline;">
					                	<a name="suggest_attach" class="suggest_attach" target="#">
										<input type="hidden" name="attachId" id="attachId_<%=DeptSubProcessConstants.CHOICE_LEADER_FAILED %>" value=""/>
					                	上传意见附件(<span style="display:inline;" id="fjcount_<%=DeptSubProcessConstants.CHOICE_LEADER_FAILED %>" class="fjcount">0</span>)</a>
					                	<!--<a class="previewSuggest">意见预览</a>-->
					                	</span>
					                	
				                	</td>
				              	</tr>
					  			<tr>
				                	<td><textarea id="suggest" name="suggest" rows="3"></textarea></td>
				              	</tr>
			              	</tbody>
		              	</table>
	              	</td>
				</tr>
			</table>
              	
			<table id="choiceZone3" width="100%" border="0" cellspacing="0" cellpadding="0">
		  		<tr>
		  			<td class="td_1">
		  				<input type="radio" name="choice" value="<%=DeptSubProcessConstants.CHOICE_LEADER_CONTINUE %>"/>
		  			</td>
		  			<td style="width:99%;">还需要本部门业务人员继续处理 </td>
		  		</tr>
		  		<tr>
  					<td colspan=2><table id="suggest3" width="100%" border="0" cellspacing="0" cellpadding="0"><tbody>
		  				<tr>
		                	<td textareaId="suggest3" peopleId="choiceZone3">
			                	<font style="color:red;display:inline;">*</font>
			                	意见：
			                	<span class="fr" style="display:inline;">
				                	<a class="createSuggest">生成意见</a>
				                	
				                	<a name="suggest_attach" class="suggest_attach" target="#">
										<input type="hidden" name="attachId" id="attachId_<%=DeptSubProcessConstants.CHOICE_LEADER_CONTINUE %>" value=""/>
					                	上传意见附件(<span style="display:inline;" id="fjcount_<%=DeptSubProcessConstants.CHOICE_LEADER_CONTINUE %>" class="fjcount">0</span>)</a>
				                	<!--<a class="previewSuggest">意见预览</a>-->
			                	</span>
		                	</td>
		              	</tr>
				  		<tr>
				  			<td><textarea id="suggest" name="suggest" rows="3"></textarea></td>
				  		</tr>
				  		</tbody></table>
		  			</td>
		  		</tr>
		  		
		  		<tr>
		  			<td></td>
			  		<td>
				  		<table id="choiceZone3_1" width="100%" border="0" cellspacing="0" cellpadding="0">
					  		<tr>
					  			<td class="td_1">
					  				<input type="radio" name="choice2" value="<%=DeptSubProcessConstants.CHOICE_LEADER_CONTINUE_DEAL %>" checked/>
					  			</td>
					  			<td style="width:99%;">需本部门其他人员继续处理,转业务经办人 </td>
					  		</tr>
					  		</table>
					  		
					  		<table id="choiceZone3_2" width="100%" border="0" cellspacing="0" cellpadding="0">
					  		<tr>
					  			<td class="td_1">
					  				<input type="radio" name="choice2" value="<%=DeptSubProcessConstants.CHOICE_LEADER_CONTINUE_CHOICE_PERSON %>"/>
					  			</td>
					  			<td style="width:99%;">直接选择处理人员</td>
					  		</tr>
					  		<tr>
					  			<td></td>
					  			<td>
					  				<table width="100%" border="0" cellspacing="0" cellpadding="0">
								  		<tr height="30">
								  			<td>
								  				选择人员：
								  				<input type="text" name="dealPersonNames" value="" readonly class="inputLine" value="" style="width:250px;height:22px"/>
								  				<input type="hidden" name="dealPersonStr" value="">
								  				<input type="hidden" id="dealPersonIdStr" name="dealPersonIdStr" value="">
								  				<input type="button" name="choosePerson" value="..." class="btn">
								  				<input type="button" name="clearPerson" value="清除" class="btn"/>
								  			</td>
						  				</tr>
								  		<tr height="30">
								  			<td>
								  				选择领导：
								  				<input type="text" name="dealLeaderNames" value="" readonly class="inputLine" value="" style="width:250px;height:22px"/>
								  				<input type="hidden" name="dealLeaderStr" value="">
								  				<input type="hidden" id="dealLeaderIdStr" name="dealLeaderIdStr" value="">
								  				<input type="button" name="chooseLeader" value="..." class="btn"/>
								  				<input type="button" name="clearLeader" value="清除" class="btn"/>
								  			</td>
								  		</tr>
								  		<tr><td><a href="" onclick="return false;"><font style="color:red;display:inline;">注：人员和领导不能同时为空！</font></a></td></tr>
								  	</table>
					  			</td>
					  		</tr>
				  		</table>
			  		</td>
		  		</tr>
		  		<!--  
		  		<tr><td>操作栏</td></tr>
	            <tr><td><textarea id="operate_msg" name="operate_msg" rows="2" readonly>提交至主管部门经办人汇总。</textarea></td></tr>
	       	  	-->
			</table>
		</div>
		<div class="button t_c">
			<input id="handleSubmit" type="button" value="确 认">
			<input id="handleClose" type="button" value="取 消">
			
			<div id="handle_zone_loading" style="width:100%;align:center;line-height:20px;display:none;" class="">
		      <p style="width:auto;display:inline;"><img src="<%=path %>/contact/images/loading.gif" style="display:inline;"/>
		      <b style="color:green;display:inline;">&nbsp;正在提交</b></p>
		    </div>
		</div>
</div>
<%}%>
