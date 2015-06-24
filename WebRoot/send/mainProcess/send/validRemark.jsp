<%@ page language="java" import="com.wonders.send.model.bo.ValidFile" pageEncoding="utf-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
    ValidFile validFile = request.getAttribute("validFile") == null ? null : (ValidFile) request.getAttribute("validFile");
    if (validFile != null &&
            (("2").equals(validFile.getStatus())
                    || ("3").equals(validFile.getStatus())
                    || ("4").equals(validFile.getStatus())
            )) {
%>
<div class="seal <%=("2".equals(validFile.getStatus())?"seal_bg2":"seal_bg1")%> ">
    <h1><%
        if ("2".equals(validFile.getStatus())) {%>
        部分有效
        <%} else if ("3".equals(validFile.getStatus())) {%>
        失效
        <%} else if ("4".equals(validFile.getStatus())) {%>
        废止
        <%}%>
    </h1>
    <h6 style="cursor: pointer;" id="vrk"
        mainId="<%=request.getAttribute("validAttach")==null ? "": (String)request.getAttribute("validAttach")%>">点击查看相关备注</h6>
    <h6><%=validFile.getOperateTime().substring(0, 10)%></h6>
</div>

<%
    }
%>