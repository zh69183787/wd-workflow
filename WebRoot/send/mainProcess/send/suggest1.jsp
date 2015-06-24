<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page import="com.wonders.send.approve.model.bo.SendApprovedinfo" %>
<%@page import="com.wonders.send.util.Donull" %>
<%@page import="java.util.List" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="com.wonders.util.DateUtil" %>
<%@page import="com.wonders.util.StringUtil" %>
<%@page import="com.wonders.send.mainProcess.send.model.bo.TDocSend" %>
<%@page import="org.springframework.web.context.WebApplicationContext" %>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@page import="com.wonders.attach.service.FjshService" %>
<%@page import="com.wonders.attach.model.bo.AttachFile" %>
<%@ page import="com.wonders.common.model.vo.TaskUserVo" %>
<%@ page import="com.wonders.constants.LoginConstants" %>
<%
    String path = request.getContextPath();

    Donull donull = new Donull();
    TDocSend model = (TDocSend) request.getAttribute("model");
    String listType = "0";
    if ("新发文流程".equals(model.getModelid()) || "发文流程".equals(model.getModelid())) {
        listType = "1";
    }
    WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
    FjshService fjshService = (FjshService) context.getBean("fjshService");
    List jgApprovedinfoList = (List) request.getAttribute("jgApprovedinfoList");

    String stepName = request.getParameter("stepName");

    String loginName = (String) session.getAttribute("login_name");
//System.out.println("====="+deptId+deptName);
    String assignedtouser = request.getParameter("assignedtouser");
    if (assignedtouser == null) {
        assignedtouser = request.getParameter("taskuser");
    }
    Map<String, TaskUserVo> userMap = (Map<String, TaskUserVo>) session.getAttribute(LoginConstants.DEPT_USER);
    if (userMap != null) {
        TaskUserVo taskUserVo = userMap.get(assignedtouser);
        if (taskUserVo != null) {
            loginName = taskUserVo.getLoginName().replace("ST/", "");
        }
    }
%>
<script>
    var newwindow;
    function attach(url) {
        var num = Math.random()
        url = url + '&random=' + num;
//window.open(url,"","width=700px;height=1px;");
//window.showModalDialog(url,window,"dialogWidth=700px;dialogHeight=300px;help=no;status=no");
        openwindow(url, "f", 700, 500);
//document.execCommand('Refresh');
    }

    function openwindow(url, name, iWidth, iHeight) {
// url 转向网页的地址  
// name 网页名称，可为空  
// iWidth 弹出窗口的宽度  
// iHeight 弹出窗口的高度  
//window.screen.height获得屏幕的高，window.screen.width获得屏幕的宽  
        var iTop = (window.screen.height - 30 - iHeight) / 2; //获得窗口的垂直位置;
        var iLeft = (window.screen.width - 10 - iWidth) / 2; //获得窗口的水平位置;
        newwindow = window.open(url, 't', 'height=' + iHeight + ',width=' + iWidth + ',top=' + iTop + ',left=' + iLeft);
    }

    function attach3() {
//window.open(url,"","width=700px;height=1px;");
        var num = Math.random();
        var testHiddenVal = document.getElementById("contentAttMain").value;

        var url = "<%=request.getContextPath()%>/attach/loadFileList.action?attachMemo=fawen_att_dic&fileGroup=contentAttMain&fileGroupName=contentAttMainGroup&userName=<%=loginName%>&loginName=<%=loginName%>&fileGroupId=" + testHiddenVal + "&procType=<%="" %>&targetType=dialog&listType=<%=listType%>";
        url = url + '&random=' + num;
//window.open(url, window, "height=500, width=700, toolbar= no, menubar=no, scrollbars=no, resizable=no, location=no, status=no,top=100,left=300")
        openwindow(url, "f", 700, 500);
    }
</script>
<tr class="content7">
    <td class="lableTd t_r">校对</td>
    <td colspan="3">
        <%
            if (jgApprovedinfoList != null && jgApprovedinfoList.size() > 0) {
                for (int i = 0; i < jgApprovedinfoList.size(); i++) {
                    SendApprovedinfo approvedinfo = (SendApprovedinfo) jgApprovedinfoList.get(i);
                    if (approvedinfo.getFileGroupId() != null && !"".equals(approvedinfo.getFileGroupId())) {
                        out.println("<div align=left style='display:inline;'>" + donull.dealNull(approvedinfo.getRemark()) + "<a style='display:inline;' href='" + request.getContextPath() + "/attach/loadFileList.action?fileGroup=docSeYjFile&fileGroupName=docSeYjFileGroup&userName=" + loginName + "&loginName=" + loginName + "&fileGroupId=" + approvedinfo.getFileGroupId() + "&procType=view&targetType=frame' target='_blank'><img  alt='附件' style='cursor:hand;display:inline;' src='" + path + "/send/images/fj.gif'/></a></div>");
                    } else {
                        out.println("<div align=left>" + donull.dealNull(approvedinfo.getRemark()) + "</div>");
                    }
                    out.println("<div align=right>" + approvedinfo.getUserfullname() + "&nbsp;" + DateUtil.getDateStr(approvedinfo.getUpddate(), "yyyy-MM-dd") + "</div>");
                }
            }
        %>
    </td>
</tr>
<%
    if ("1".equals(listType)) {
%>
<tr class="content7">
    <td class="lableTd t_r">文件类型</td>
    <td colspan="3">
        <%
            if ("套头".equals(stepName)) {
        %>

        <select id="sendFileType" name="sendFileType">
            <option value="">请选择</option>
            <option value="上行文">上行文</option>
            <option value="下行文">下行文</option>
        </select>

        <%} else {%>
            <%=donull.dealNull(model.getSendFileType())%>
        <%}%>
    </td>
</tr>
<%
    }
%>

<s:if test="#request.print!=1">
    <tr class="content7">
        <td class="lableTd t_r">正式文件</td>
        <td colspan="3">
            <%
                if ((donull.dealNull(model.getContentAttMain()).length() > 0)) {
                    List list = fjshService.findFilesByGroupId(donull.dealNull(model.getContentAttMain()));
                    if (list != null && list.size() > 0) {
                        AttachFile af = (AttachFile) list.get(0);
                        out.println(af.getFileName());
                    }
                }
            %>
            <input type="hidden" id="contentAttMain"
                   name="contentAttMain"
                   value="<%=donull.dealNull(model.getContentAttMain()) %>"/>
            <%if (("套头".equals(stepName)) || (donull.dealNull(model.getContentAttMain()).length() > 0)) { %>
            <img alt="附件"
                    <%if ("套头".equals(stepName)) { %>
                 onClick="attach3();"
                    <%} else { %>
                 onClick="attach('<%=request.getContextPath()%>/attach/loadFileList.action?attachMemo=fawen_att_dic&procType=view&fileGroup=contentAttMain&fileGroupName=contentAttMainGroup&fileGroupId=<%=donull.dealNull(model.getContentAttMain()) %>&userName=<%=loginName %>&loginName=<%=loginName %>&targetType=dialog&listType=<%=listType %>');"
                    <%} %>
                 style="cursor: hand"
                 src="<%=path %>/send/images/fj.gif"/>
            <%} %>
        </td>
    </tr>
</s:if>
