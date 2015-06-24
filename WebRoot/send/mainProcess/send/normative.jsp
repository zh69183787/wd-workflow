<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.wonders.send.mainProcess.send.model.bo.TNormativeDoc"%>
<%@page import="com.wonders.send.util.Donull"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.wonders.util.DateUtil"%>
<%@page import="com.wonders.util.StringUtil"%>
<%@page import="com.wonders.send.mainProcess.send.model.bo.TDocSend"%>
<%@page import="com.wonders.constants.WorkflowConstants"%>
<style>
.normative td{
    border:1px solid #000;
    text-align:center;
    vertical-align:middle;
}
.arrow{
    cursor:pointer;
}
#normativeMemoTable a{
    color:#605f5f;
    display:block;
    margin-left : 0;
}
</style>
<%
	String path = request.getContextPath();
	Donull donull = new Donull();
	TDocSend model = (TDocSend) request.getAttribute("model");
	String stepName = StringUtil.getNotNullValueString(request.getAttribute("stepName"));
	String normative = StringUtil.getNotNullValueString(request.getAttribute("normative"));
	List<TNormativeDoc> memo = (List<TNormativeDoc>)request.getAttribute("normativeMemo");

	String radioChecked = "1".equals(normative) ? " checked " : " ";
	String spanIsShow = "1".equals(normative) ? " style=\"display: inline;\" " : " style=\"display: none;\" ";
	String isShow = memo!=null && memo.size() > 0 ? " " : " style=\"display: none;\" ";

	String checkboxChecked = memo!=null && memo.size() > 0 ? " checked " : " ";
	String viewFlag = StringUtil.getNotNullValueString(request.getParameter("viewFlag"));
	String isBegin = StringUtil.getNotNullValueString(request.getParameter("isBegin"));
%>

<%if(!"1".equals(viewFlag) && (model.getModelid() == null ||
		(WorkflowConstants.NEW_DOC_SEND.equals(model.getModelid())
        && ("拟稿人修改".equals(stepName)||"起草".equals(stepName)
        ||"套头".equals(stepName)||"审稿".equals(stepName) ||"排版".equals(stepName))))
		){%>
	<table id="normativeTable" width="100%" border="0" cellspacing="0"
            cellpadding="0">
            <%if(!"1".equals(isBegin)){ %>
            <tr height="30">
                <td class="td_1">
                <font style="color: red;">*</font></td>
                <td>规范性文件设置</td>
            </tr>
            <%} %>
            <tr height="30">
                <td style="border:0;" class="td_1"></td>
                <td style="border:0;">
                    <input <%=("0".equals(normative)||"".equals(normative))?" checked ":" " %> type="radio" id="normative" name="normative" value="0" />非规范性文件
                    <input <%="1".equals(normative)?" checked ":" " %> type="radio" id="normative" name="normative" value="1" />规范性文件
                    <span id="overrideSpan" <%=spanIsShow %>>
                        <input <%=checkboxChecked %> type="checkbox" id="override" name="override" value="1" />须修改或替代已有文件
                    </span>
                </td>
            </tr>
            <tr id="normativeAddTr" <%=isShow %>>
                <td style="border:0;"></td>
                <td style="border:0;">请填写需修改相关文件名称、文件字号、修改内容并选择状态调整建议&nbsp; <input type="button"
                    value="新增" id="normativeAddButton">
                </td>
            </tr>
            <tr id="normativeShowTr" height="30" <%=isShow %>">
                <td style="border:0;" class="td_1"></td>
                <td style="border:0;">
                    <table class="normative" id="normativeMemoTable" width="100%">
                        <tr>
                            <td style="width: 5%;">序</td>
                            <td style="width: 25%;">相关已有文件标题</td>
                            <td style="width: 25%;">文件字号</td>
                            <td style="width: 20%;">状态调整建议</td>
                            <td style="width: 15%;">修改内容</td>
                            <td style="width: 10%;">操作</td>
                        </tr>
                        <%if(memo != null && memo.size() > 0){
                            for(int i=0;i<memo.size();i++){
                        %>
                        <tr>
                            <td>
                            <input type="hidden" name="normativeId"  id="normativeId"
                            value="<%=StringUtil.getNotNullValueString(memo.get(i).getId())%>">
                            <input type="hidden" id="normativeRemoved" name="normativeRemoved" value="0">
                            <strong name="num"><%=(i+1) %></strong>
                            </td>
                            <td>
                            <input type="hidden" name="normativeTitle"
                            value="<%=StringUtil.getNotNullValueString(memo.get(i).getTitle())%>">
                            <%=StringUtil.getNotNullValueString(memo.get(i).getTitle())%></td>
                            <td>
                            <input type="hidden" name="normativeCode"
                            value="<%=StringUtil.getNotNullValueString(memo.get(i).getCode())%>">
                            <%=StringUtil.getNotNullValueString(memo.get(i).getCode())%></td>
                            <td>
                            <input type="hidden" name="normativeStatus"
                            value="<%=StringUtil.getNotNullValueString(memo.get(i).getStatus())%>">
                            <%if(!"套头".equals(stepName)){%>建议调整为<%} %>
                            <%=StringUtil.getNotNullValueString(memo.get(i).getStatusCn())%></td>
                            <td>
                            <input type="hidden" name="normativeAttach"
                            value="<%=StringUtil.getNotNullValueString(memo.get(i).getAttach())%>"><input type="hidden" name="normativeAttachCount"><center>
                            <span class="arrow">
                            <%if(memo.get(i).getAttach()!=null) {%><img alt='附件' src='<%=path%>/send/images/fj.gif'/><%} %>
                            </span>
                            </center>
                            </td>
                            <td>
	                            <span>
	                            <a class="deleteWin" href="javascript:void(0);">删除</a>
	                            <a style="display:none;" class="attachWin" href="javascript:void(0);">附件</a>
	                            <a class="editWin" href="javascript:void(0);">修改</a>
	                            </span>
                            </td>
                        </tr>

                        <%}} %>
                    </table>
                </td>
            </tr>

        </table>
        <%if("套头".equals(stepName)){ %>
        <div class="button t_c">
            <input type="hidden" name="ttConfirm" id="ttConfirm">
            <input id="ttButton" type="button" value="确 认">
        </div>
            <%} %>


<%}else if(WorkflowConstants.NEW_DOC_SEND.equals(model.getModelid())){%>
	<table width="100%" border="0" cellspacing="0"
            cellpadding="0">
            <tr height="30">
                <td style="border:0;padding:0;">
                    <%if("1".equals(normative)) {%>本文件为规范性文件
	                    <%if(memo!=null && memo.size() > 0){%>
	                    , 须修改或替代已有文件
                    <%}
                    }else if("0".equals(normative)){ %>本文件为非规范性文件
                    <%}else{%>
                    	未设置文件规范性
                    <%} %>
                </td>
            </tr>
            <%if("1".equals(normative) && memo!=null && memo.size() > 0) {%>
            <tr  height="30">
                <td style="border:0;padding:0;">
                    <table class="normative" width="100%">
                        <tr>
                            <td style="width: 25%;">相关已有文件标题</td>
                            <td style="width: 25%;">文件字号</td>
                            <td style="width: 20%;">状态调整建议</td>
                            <td style="width: 10%;">修改内容</td>
                        </tr>
                        <%
                        for(int i=0;i<memo.size();i++){
                        %>
                        <tr>
                            <td><%=StringUtil.getNotNullValueString(memo.get(i).getTitle())%></td>
                            <td><%=StringUtil.getNotNullValueString(memo.get(i).getCode())%></td>
                            <td>
                            <%if(!"0".equals(model.getFlag())){%>建议调整为<%}%>
                            <%=StringUtil.getNotNullValueString(memo.get(i).getStatusCn())%></td>
                            <td>

                           <%
	                           String attachUrl = path+"/attach/loadFileList.action?"+
	                        		   "fileGroup=contentAttachmentId&fileGroupName=projectAttachIdGroup"+
	                        		"&fileGroupId="+StringUtil.getNotNullValueString(memo.get(i).getAttach())+
	                        		"&procType=view&targetType=frame&listType=normative";

                           %>
                            <center>
                            <span style="cursor:pointer;" onclick="window.open('<%=attachUrl %>','att','height=200,width=400');">
                            <%if(memo.get(i).getAttach()!=null) {%><img alt='附件' src='<%=path%>/send/images/fj.gif'/><%} %>
                            </span>
                            </center>
                            </td>
                        </tr>

                        <%}%>
                    </table>
                </td>
            </tr>
            <%} %>
        </table>
<%} %>