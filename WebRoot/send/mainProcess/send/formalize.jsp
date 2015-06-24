<%@ page language="java" import="com.wonders.send.constant.Constants" pageEncoding="utf-8" %>
<%@ page import="com.wonders.send.mainProcess.send.model.bo.TDocSend" %>
<%@ page import="com.wonders.send.util.CodeUtil" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%
    String path = request.getContextPath();
    TDocSend model = (TDocSend) request.getAttribute("model");
    List memo = (List) request.getAttribute("normativeMemo");
    boolean showFlag = false;
    if (memo != null && memo.size() > 0) {
        showFlag = true;
    }
%>
<script>
    function handleFunc(obj) {
        if (checkForm()) {
            if ($("[name=choice]:checked").val() == "0") {
                if ($.trim($("[name=suggest]:enabled").val()) == "") {
                    alert("请输入备注！");
                    $("[name=suggest]:enabled").focus();
                    return false;
                }
                if ($("[name=attachId]:enabled").val() == "") {
                    alert("请上传意见附件！");
                    return false;
                }

            } else {
                if ($("[name=sendFileType]").val() == "") {
                    alert("请选择文件类型！");
                    return false;
                }
                if ($("[name=contentAttMain]").val() == "") {
                    alert("请上传红头文件！");
                    return false;
                }
            }

            if (confirm("确定处理完成？")) {
                var formOptions = {
                    cache: false,
                    type: 'post',
                    callback: null,
                    dataType: 'json',
                    url: contextpath + "/send-tDocSend/updateForm.action",
                    success: function () {
                        alert("提交成功！");
                        window.location.href = contextpath + "/send-tDocSend/toFormPage.action?incidentNo=" + $("[name=incidentNo]").val()
                                + "&modelName=" + encodeURI($("[name=modelName]").val())
                                + "&processName=" + encodeURI($("[name=processName]").val())
                                + "&pinstanceId=" + $("[name=pinstanceId]").val()
                                + "&taskId=" + $("[name=taskId]").val()
                                + "&taskUserName=" + encodeURI($("[name=taskUserName]").val());
                        //$("#formUpdate").ajaxSubmit(formOptions1);
                        return false;
                    }
                };

                var formOptions1 = {
                    cache: false,
                    type: 'post',
                    callback: null,
                    dataType: 'json',
                    url: contextpath + "/send-tDocSend/deal.action",
                    success: function (data) {
                        if (data != null && data.if_success == "yes") {
                            $("#formUpdate").ajaxSubmit(formOptions);
                        } else {
                            alert("提交失败，请联系管理员！");
                        }

                        return false;
                    }
                };

                $(obj).attr("disabled", true);
                $("#handleClose").attr("disabled", true);
                $("#handle_zone_loading").show();
                if (newwindow && newwindow.open && !newwindow.closed) {
                    newwindow.close();
                }
                $("#formUpdate").ajaxSubmit(formOptions1);
            }
        }
    }

    function chooseRadioFunc(num) {
        $("[name=suggest]").val("");
        $("[name=suggest]").each(function (i) {
            if ((i + 1) == num) {
                $(this).attr("disabled", false);
            } else {
                $(this).attr("disabled", true);
            }
        });
        $("[name=attachId]").each(function (i) {
            if ((i + 1) == num) {
                $(this).attr("disabled", false);
            } else {
                $(this).attr("disabled", true);
            }
        });
    }

    function getStatusCn(type){
        var result = "";
        switch (type){
            case "2" :
                result  = "部分有效";
                break;
            case "3" :
                result  = "失效";
                break;
            case "4" :
                result  = "废止";
                break;
            default :
                break;
        }
        return result;
    }

    $(function () {
        chooseRadioFunc(1);
        $("#titleTr").hide();
        $("#attachTr").hide();
        $("#query").click(function () {
            $.get('<%=path%>/send-tDocSend/queryByCode.action?random=' + Math.random(),
                    {
                        "code1": $("#lastCode1").val(),
                        "code2": $("#lastCode2").val(),
                        "code3": $("#lastCode3").val()
                    },
                    function (obj, textStatus, jqXHR) {
                        var title = obj.title;
                        var attach = obj.attach;
                        var mainId = obj.mainId;
                        var code = obj.code;
                        if (title == "" || attach == "") {
                            $("#setStatus").hide();
                            $("#titleTd").html("");
                            $("#attachFrame").attr("src", "");
                            $("#titleTr").hide();
                            $("#attachTr").hide();
                            $("#lastMainId").val("");
                            $("#lastCode").val("");
                            $("#lastNormative").hide();
                            alert("无法找到匹配的文件！");
                        } else {
                            $("#setStatus").show();
                            $("#titleTr").show();
                            $("#attachTr").show();
                            $("#lastMainId").val(mainId);
                            $("#titleTd").html("文件标题：" + title);
                            $("#lastCode").val(code);
                            $("#lastNormative").show();
                            $("#attachFrame").attr("src",
                                            "<%=path%>/attach/loadFileList.action?attachMemo=fawen_att_dic&procType=edit&fileGroup=contentAttMain&fileGroupName=contentAttMainGroup&fileGroupId=" + attach + "&targetType=dialog&listType=1");
                        }
                    },
                    "json"
            ).error(function () {
                        alert("服务器连接失败，请稍后再试!");
                    });
        });

        $("#setStatus").click(function () {
            $("#editedTr").show();
            var lastMainId = $("#lastMainId").val();
            var lastNormative = $("#lastNormative").val();
            var lastCode = $("#lastCode").val();
            $("#editedTd").append("<span normative='"+lastNormative+"' mainId = '"+lastMainId+"'>"+lastCode+"&nbsp"+getStatusCn(lastNormative) +
            "<img class='lastDel' src='<%=path%>/receive/css/default/images/delete.gif'"+
            "style='cursor: pointer; display: inline;' title='删除附件' /></span>");
            $("#lastObject").val($("#lastObject").val()+lastMainId+":"+lastNormative+";");
            $("#setStatus").hide();
            $("#lastNormative").hide();
        });

        $(document).on("click",".lastDel",function(){
            if($(this).parent().parent().find("span").length == 1){
                $("#editedTr").hide();
            }
            $("#lastObject").val($("#lastObject").val().
                    replace($(this).parent("span").attr("mainId")+":"+$(this).parent("span").attr("normative")+";",""));
            $(this).parent("span").remove();

        });
    });
</script>
<div id="handle_zone" class="f_window" style="display:none;width:600px;">
    <input type="hidden" id="lastObject" name="lastObject" value = "">
    <h3 class="clearfix mb10">
        <span class="fl">套头</span>
        <div class="fr close" id="handleDivClose">关闭窗口</div>
    </h3>
    <div class="con">
        <jsp:include page="normative.jsp"/>
        <table id="choiceZone2" width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr height="20">
                <td class="td_1">
                    <input type="radio" name="choice" checked="checked" value="1" onclick="chooseRadioFunc(1)"/>
                </td>
                <td>确认发文</td>
            </tr>
            <tr height="20" id="editedTr" style="display: none;">
                <td class="td_1"></td>
                <td id="editedTd"></td>
            </tr>

            <tr height="20" id="lastDocTr">
                <td class="td_1"></td>
                <td>
                    过往文件设置：
                    <select name="lastCode1" id="lastCode1">
                        <%
                            List list = CodeUtil.findCodeInfoByCode(Constants.DocSend__Dictionary, model.getSendType() + "_ZH");
                            if (list != null && list.size() > 0) {
                                if ("上海申通地铁集团有限公司办公室".equals(model.getTypeTitle())) {
                                    Iterator iter = list.iterator();
                                    while (iter.hasNext()) {
                                        String temp = (String) iter.next();
                                        String temps[] = temp.split(":");
                                        if ("沪地铁办发".equals(temps[1])) {
                        %>
                        <option value="<%=temps[1]%>"><%=temps[1] %>
                        </option>
                        <%
                                break;
                            }%>
                        <%}%>
                        <%
                        } else {
                            Iterator iter = list.iterator();
                            while (iter.hasNext()) {
                                String temp = (String) iter.next();
                                String temps[] = temp.split(":");
                                if (!"沪地铁办发".equals(temps[1])) {
                        %>
                        <option value="<%=temps[1]%>"><%=temps[1] %>
                        </option>
                        <%}%>
                        <%}%>
                        <%
                                }
                            }
                        %>
                    </select>
                    <select name="lastCode2" id="lastCode2">
                        <% Calendar c = Calendar.getInstance();
                            int year = c.get(Calendar.YEAR);
                            for (int i = 0; i < 11; i++) { %>
                        <option value="<%=(year-i) %>"
                                <%if (i == 0) { %>
                                selected="selected"
                                <%} %>
                                ><%=(year - i) %>
                        </option>
                        <%} %>
                    </select>
                    <input type="text" name="lastCode3" id="lastCode3" class="input_tiny"/>
                    <select name="lastNormative" id="lastNormative" style="display: none;">
                        <option value="2">部分有效</option>
                        <option value="3">失效</option>
                        <option value="4">废止</option>
                    </select>
                    <input style="display: none;" type="button" name="setStatus" id="setStatus" class="input_small" value="设置"/>
                    <input type="button" name="query" id="query" class="input_small" value="查询"/>
                </td>
            </tr>

            <tr height="20" id="titleTr">
                <td class="td_1"><input type="hidden" id="lastCode" name="lastCode"/>
                    <input type="hidden" id="lastMainId" name="lastMainId"/></td>
                <td id="titleTd"></td>
            </tr>

            <tr height="20" id="attachTr">
                <td class="td_1"></td>
                <td id="attachTd">
                    <iframe scrolling="auto" height="150" frameborder="0" marginheight="0" marginwidth="0" width="100%"
                            id="attachFrame" name="attachFrame"
                            src=""></iframe>
                </td>
            </tr>
            <tr height="20">
                <td class="td_1"></td>
                <td>
                    备注：
					<span class="fr" style="display:inline;">
	                	<a name="suggest_attach" class="suggest_attach" target="#">
                            <input type="hidden" name="attachId" id="attachId_1" value=""/>
                            上传意见附件(<span style="display:inline;" id="fjcount_1" class="fjcount">0</span>)</a>
	               	</span>
                    <br/>
                    <textarea rows="3" name="suggest" id="suggest"></textarea>
                </td>
            </tr>
        </table>

        <table id="choiceZone2" width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr height="20">
                <td class="td_1">
                    <input type="radio" name="choice" value="0" onclick="chooseRadioFunc(2)"/>
                </td>
                <td>返回修改</td>
            </tr>
            <tr height="20">

                <td style="text-align:right;"><font style="color:red;">*</font></td>
                <td style="display:inline;">
                    备注：
					<span class="fr" style="display:inline;">
	                	<a name="suggest_attach" class="suggest_attach" target="#" style="display:inline;">
                            <input type="hidden" name="attachId" id="attachId_2" value=""/>
                            上传意见附件(<span style="display:inline;" id="fjcount_2" class="fjcount">0</span>)</a>
	                	<font style="color:red;display:inline;">*</font>
	               	</span>
                    <br/>
                    <textarea rows="3" name="suggest" id="suggest"></textarea>
                </td>
            </tr>
        </table>
    </div>
    <div class="button t_c">
        <input id="handleSubmit" type="button" value="确 认" onclick="handleFunc(this);">
        <input id="handleClose" type="button" value="取 消">
        <div id="handle_zone_loading" style="width:100%;align:center;line-height:20px;display:none;" class="">
            <p style="width:auto;display:inline;"><img src="<%=path %>/send/images/loading.gif"
                                                       style="display:inline;"/>
                <b style="color:green;display:inline;">&nbsp;正在提交</b></p>
        </div>
    </div>
</div>

