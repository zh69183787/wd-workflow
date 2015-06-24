
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date" %>
<%@ page import="com.wonders.contract.workflow.constants.UltimusConstants" %>
<%@ page import="com.wonders.contract.workflow.process.review.constants.ReviewMainStepConstants" %>
<%@ page import="com.wonders.util.StringUtil" %>
<%@ page import="com.wonders.contract.workflow.constants.LoginConstants" %>

<%@taglib prefix="s" uri="/struts-tags" %>
<%
    String path = request.getContextPath();
    Date today = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String dateTime = sdf.format(today);
    String userName = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.USERNAME));
    String deptId = StringUtil.getNotNullValueString(session.getAttribute(LoginConstants.DEPTID));

%>
<!DOCTYPE html >
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>供应商查看</title>
    <link rel="stylesheet" href="../../css/formalize.css" />
    <link rel="stylesheet" href="../../css/page.css" />
    <link rel="stylesheet" href="../../css/imgs.css" />
    <link rel="stylesheet" href="../../css/reset.css" />
    <link type="text/css" href="../../js/datepicker/css/flick/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
    <link rel="stylesheet" href="../../css/uploadify.css" />
    <style>
        .ui-autocomplete {
            max-height: 150px;
            overflow-y: auto;
            /* prevent horizontal scrollbar */
            overflow-x: hidden;
            /* add padding to account for vertical scrollbar */
            padding-right: 20px;
            max-width: 300px;
            width: 300px;
        }
        /* IE 6 doesn't support max-height
         * we use height instead, but this forces the menu to always be this tall
         */
        * html .ui-autocomplete {
            height: 150px;
        }
    </style>
    <!--[if IE 6.0]>
    <script src="js/iepng.js" type="text/javascript"></script>
    <script type="text/javascript">
        EvPNG.fix('div, ul, ol, img, li, input, span, a, h1, h2, h3, h4, h5, h6, p, dl, dt');
    </script>
    <![endif]-->
    <script src="../../js/jquery-1.7.1.js"></script>
    <script src="../../js/jquery.formalize.js"></script>
    <script src="../../js/jquery-ui-1.8.18.custom.min.js"></script>
    <script src="../../js/datepicker/js/jquery-ui-1.8.18.custom.min.js"></script>
    <script type="text/javascript" src="../../ui/i18n/jquery.ui.datepicker-zh-CN.js"></script>
    <script src="../../js/jquery.uploadify-3.1.js"></script>
    <script src="<%=path %>/contract/js/contextpath.js"></script>
    <script src="<%=path %>/contract/js/html5.js"></script>
    <!--<script src="<%=path %>/contract/js/jquery.formalize.js"></script>  -->

    <script src="<%=path %>/contract/js/jquery-migrate-1.2.1.min.js"></script>
    <script src="<%=path %>/contract/js/jquery-form/jquery.form.min.js"></script>
    <script src="<%=path%>/contract/js/jquery-ui/jquery-ui.js"></script>
    <script src="<%=path %>/contract/js/jquery-ui/jquery.ui.datepicker-zh-CN.js"></script>
    <script src="<%=path %>/contract/js/common/elementControl.js"></script>
    <script src="<%=path %>/contract/js/jquery-qtip/jquery.qtip-1.0.0-rc3.js"></script>

    <%String basePath = request.getContextPath(); %>

    <script type="text/javascript">
        var subTypes=new Array();
        subTypes[0]=['K勘察类','S设计类','Q前期类','G工程类','J监理类','Z咨询类','Y科研类','q其他类'];
        subTypes[1]=['G工程类','J监理类','S设计类','Z咨询类','Y科研类','C采购类','W维护类','R租赁类','N能源类','q其他类'];
        subTypes[2]=['Q其他类'];
        function changeDept(value){
            if(value=="00"){
                $('#fyear option:contains("0000")').attr('selected','selected');

            }else{
                $('#fyear option:contains("2014")').attr('selected','selected');

            }
            adds('contractCode:'+value);

        }
        /**
         *验证发文号
         */
        function checkNum(value){
            var reg=new RegExp(/^\d\d\d$/);
            if(!reg.test(value)){
                $("#fawen").focus();
                alert("请输入三位整数");
                $("#fawen").focus();
            }
            adds('fawen:'+value);
        }
        /**
         *子项目编号补全
         */
        function checkSubProject(value){
            var reg=new RegExp(/^\d$/);
            if(reg.test(value)){
                $("#subPeoject").attr('value',0+value);
            }
            adds('subProject:'+0+value);
        }

        function changes(types){
            var subType=$("#subType");
            $("#subType option").remove();
            if(types!=null) {
                for (var i = 0; i < types.length; i++) {
                    var optEle = new Option(types[i].substr(1), types[i].substr(0, 1));
                    subType.append('<option value='+types[i].substr(0, 1)+'>'+types[i].substr(1)+'</option>');
                }
            }
        }
        /**
         *联动合同子类
         */
        function changeMainType(value){
            if(value=='J'){
                changes(subTypes[0]);
            }else if(value=='Y'){
                changes(subTypes[1]);
            }else if(value=='Q'){
                changes(subTypes[2]);
            }else{
                changes('');
            }
            adds('mainType:'+value);
        }

        /**
         *生成序列号
         */
        function sequence(value){
            var reg=new RegExp(/^\d\d\d\d$/);
            if(reg.test(value)){
                var year='year:'+value;
                adds(year);
            }else{
                adds('exeBody:'+value);
            }
            if($("#year").val()!=''&& $("#exeBody").val()!=''){
                $.ajax({
                    url:'<%=path %>/contract-reviewUtil/getSequence.action?year='+$("#year").val()+'&exeBody='+$("#exeBody").val()+
                            '&depts='+$("#depts").val()+'&fyear='+$("#fyear").val()+'&fawen='+$("#fawen").val()+'&subProject='+$("#subPeoject").val()
                            +'&dept='+$("#dept").val()+'&line='+$("#line").val()+'&mainType='+$("#mainType").val()+'&subType='+$("#subType").val(),
                    type:'post',
                    dataType:'text',
                    error:function(){},
                    success:function(data){
                        $("#lnum").attr('value',data);
                        adds();
                    }
                } );
            }
            adds('sequence:'+$("#lnum").val());
        }

        /**
         *编辑合同编码
         */
        function adds(value){
            var contractNUm;
            if($("#depts").val()!=''){
                $("#contractNUm").text($("#depts").val());
            }else{
                return;
            }
            if($("#fyear").val()!=''){
                $("#contractNUm").text($("#depts").val()+$("#fyear").val());
            }else{
                return;
            }
            if($("#fawen").val()!=''){
                $("#contractNUm").text($("#depts").val()+$("#fyear").val()+$("#fawen").val());
            }else{
                return;
            }
            if($("#subPeoject").val()!=''){
                $("#contractNUm").text($("#depts").val()+$("#fyear").val()+$("#fawen").val()+'-'+$("#subPeoject").val());
            }else{
                return;
            }
            if($("#dept").val()!=''){
                $("#contractNUm").text($("#depts").val()+$("#fyear").val()+$("#fawen").val()+'-'+$("#subPeoject").val()+'-'+$("#dept").val());
            }else{
                return;
            }
            if($("#line").val()!=''){
                $("#contractNUm").text($("#depts").val()+$("#fyear").val()+$("#fawen").val()+'-'+$("#subPeoject").val()+'-'+$("#dept").val()+'-'+$("#line").val());
            }else{
                return;
            }
            if($("#mainType").val()!=''){
                $("#contractNUm").text($("#depts").val()+$("#fyear").val()+$("#fawen").val()+'-'+$("#subPeoject").val()+'-'+$("#dept").val()+'-'+$("#line").val()
                        +'-'+$("#mainType").val());
            }else{
                return;
            }
            if($("#subType").val()!=''){
                $("#contractNUm").text($("#depts").val()+$("#fyear").val()+$("#fawen").val()+'-'+$("#subPeoject").val()+'-'+$("#dept").val()+'-'+$("#line").val()
                        +'-'+$("#mainType").val()+'-'+$("#subType").val());
            }else{
                return;
            }
            if($("#year").val()!=''){
                $("#contractNUm").text($("#depts").val()+$("#fyear").val()+$("#fawen").val()+'-'+$("#subPeoject").val()+'-'+$("#dept").val()+'-'+$("#line").val()
                        +'-'+$("#mainType").val()+'-'+$("#subType").val()+'-'+$("#year").val());
            }else{
                return;
            }
            if($("#exeBody").val()!=''){
                $("#contractNUm").text($("#depts").val()+$("#fyear").val()+$("#fawen").val()+'-'+$("#subPeoject").val()+'-'+$("#dept").val()+'-'+$("#line").val()
                        +'-'+$("#mainType").val()+'-'+$("#subType").val()+'-'+$("#year").val()+'-'+'-'+$("#exeBody").val());
            }else{
                return;
            }
            if($("#lnum").val()!=''){
                $("#contractNUm").text($("#depts").val()+$("#fyear").val()+$("#fawen").val()+'-'+$("#subPeoject").val()+'-'+$("#dept").val()+'-'+$("#line").val()
                        +'-'+$("#mainType").val()+'-'+$("#subType").val()+'-'+$("#year").val()+'-'+$("#lnum").val()+'-'+$("#exeBody").val());
            }
        }


        function ok(){
            if(
                    $("#year").val()==""||$("#exeBody").val()==""||
                    $("#depts").val()==""||$("#line").val()==""||
                    $("#fyear").val()==""||$("#fawen").val()==""||
                    $("#subProject").val()==""||$("#dept").val()==""||$("#mainType").val()==""||$("#subType").val()==""){
                alert("必填字段不能为空！");
                return false;
            }
            $.ajax({
                url:'<%=path %>/contract-reviewUtil/findSequence.action?year='+$("#year").val()+'&exeBody='+$("#exeBody").val()+
                        '&depts='+$("#depts").val()+'&fyear='+$("#fyear").val()+'&fawen='+$("#fawen").val()+'&subProject='+$("#subPeoject").val()
                        +'&dept='+$("#dept").val()+'&line='+$("#line").val()+'&mainType='+$("#mainType").val()+'&subType='+$("#subType").val(),
                type:'post',
                dataType:'text',
                error:function(){},
                success:function(data){

                }
            } );
            var contractNum=$("#contractNUm").text();
            window.returnValue=contractNum;
            window.close()
        }






    </script>



</head>

<body>
<div class="main">


<div class="mb10">
<table width="1300px" class="table_1" align="center">
<thead>
<th colspan="9" class="t_r">
     &nbsp;
</th>
</thead>
<tbody id="formBody">
<tr class="content6">
    <td class="content6">项目编号</td>
    <td>子项目编号</td>
    <td>合同出资（签约）主体</td>
    <td>线路</td>
    <td>合同大类</td>
    <td>合同子类</td>
    <td>年份</td>
    <td>序列号</td>
    <td>执行主体</td>
</tr>
<tr>
    <td>
        <div>
            <select id="depts" onchange="changeDept(this.value);">
                <option value="">请选择</option>
                <option value="HF">上海发改委审批项目</option>
                <option value="ST">集团内部立项项目</option>
                <option value="KY">集团科研项目</option>
                <option value="WB">维保中心内部立项项目</option>
                <option value="Y1">运一公司内部立项项目</option>
                <option value="Y2">运二公司内部立项项目</option>
                <option value="Y3">运三公司内部立项项目</option>
                <option value="Y4">运四公司内部立项项目</option>
                <option value="00">无项目合同</option>
            </select>

            <select id="fyear" onchange="adds('fyear:'+this.value);">
                <option value="">请选择</option>
                <option value="2013">2013</option>
                <option  value="2014">2014</option>
                <option value="2015">2015</option>
                <option value="2016">2016</option>
                <option  value="0000">0000</option>
            </select>
            <input  type="text" id="fawen" style="width: 35px" value="000" onblur="checkNum(this.value);" ></div>
    </td>
    <td>
        <input id="subPeoject" value="00" style="width: 30px" onblur="checkSubProject(this.value);" >
    </td>
    <td><select id="dept"  onchange="adds('dept:'+this.value)">
        <option value="">请选择</option>
        <option value="DF">多个出资（签约）主体</option>
        <option value="ST">集团</option>
        <option value="JP">教培中心</option>
        <option value="WB">维保公司</option>
        <option value="Y1">运营一公司</option>
        <option value="Y2">运营二公司</option>
        <option value="Y3">运营三公司</option>
        <option value="Y4">运营四公司</option>
        <option value="BS">宝山线项目公司</option>
        <option value="MZ">明珠线项目公司</option>
        <option value="M2">明珠线（二期）项目公司</option>
        <option value="CN">长宁线项目公司</option>
        <option value="PD">浦东线项目公司</option>
        <option value="YP">杨浦线项目公司</option>
        <option value="SS">申松线项目公司</option>
        <option value="L10">十号线项目公司</option>
        <option value="L11">申嘉线项目公司</option>
        <option value="L7">七号线项目公司</option>
        <option value="L16">十六号线项目公司</option>
        <option value="L12">十二号线项目公司</option>
        <option value="L13">十三号线项目公司</option>
        <option value="L17">十七号线项目公司</option>
    </select>
    </td>
    <td><select id="line" onchange="adds('line:'+this.value)">
        <option value="">请选择</option>
        <option value="00">不涉及任何线路</option>
        <option value="99">多线路</option>
        <option value="01">一号线</option>
        <option value="01B">一号线北延伸</option>
        <option value="01BB">一号线北北延伸</option>
        <option value="02">2号线</option>
        <option value="02X">2号线西延伸</option>
        <option value="02D">2号线东延伸</option>
        <option value="02XX">2号线西西延伸</option>
        <option value="03">3号线</option>
        <option value="03B">3号线北延伸</option>
        <option value="03BG">3号线宝钢车辆段</option>
        <option value="04">4号线</option>
        <option value="05">5号线</option>
        <option value="06">6号线</option>
        <option value="07">7号线</option>
        <option value="07B">罗店中心镇公共交通配套</option>
        <option value="08">8号线</option>
        <option value="08II">8号线二期</option>
        <option value="09">9号线</option>
        <option value="09II">9号线二期</option>
        <option value="09IIID">9号线三期东延伸</option>
        <option value="09IIIN">9号线三期南延伸</option>
        <option value="10">10号线</option>
        <option value="10II">10号线二期</option>
        <option value="11BI">11号线北段一期</option>
        <option value="11BII">11号线北段二期</option>
        <option value="11H">11号线花桥段</option>
        <option value="11D">11号线迪士尼段</option>
        <option value="12">12号线</option>
        <option value="13">13号线（13号线西段）</option>
        <option value="13S">13号线世博段</option>
        <option value="13II">13号线二期（13号线东段）</option>
        <option value="16">16号线</option>
        <option value="17">17号线</option>
    </select>
    </td>
    <td><select id="mainType" onchange="changeMainType(this.value);">
        <option value="">请选择</option>
        <option value="J">建设类</option>
        <option value="Y">运维类</option>
        <option value="Q">其他类</option>
    </select>
    </td>
    <td><select id="subType" onchange="adds('subType:'+this.value)">
        <option value="">请选择</option>
    </select>
    </td>
    <td><select id="year" onchange="sequence(this.value);">
        <option value="">请选择</option>
        <option value="2011">2011</option>
        <option value="2012">2012</option>
        <option value="2013">2013</option>
        <option value="2014">2014</option>
        <option value="2015">2015</option>
        <option value="2016">2016</option>
    </select>
    </td>
    <td><input type="text" id="lnum" value="" readonly style="width:60px ">
    </td>
    <td><select id="exeBody" onchange="sequence(this.value);">
        <option value="">请选择</option>
        <option value="XZ">行政办公室</option>
        <option value="HY">合约管理部</option>
        <option value="RS">组织人事部</option>
        <option value="TZ">投资管理部</option>
        <option value="SJ">审计室</option>
        <option value="CW">财务部</option>
        <option value="ZT">总体规划部</option>
        <option value="JC">监察室</option>
        <option value="QF">企业发展部</option>
        <option value="DB">党委办公部</option>
        <option value="BW">保卫部</option>
        <option value="GH">工会</option>
        <option value="JG">建管中心</option>
        <option value="JS">技术中心</option>
        <option value="JP">教培中心</option>
        <option value="XX">信息中心</option>
        <option value="ZC">资产公司</option>
    </select>
    </td>
</tr>
<tr>
    <td colspan="9">
        <label style="font-weight: bold;">合同编码:</label><lable style="font-weight: bold;" id="contractNUm" style="width: 300px;"></lable>
    </td>
</tr>
</tbody>
<tr class="tfoot">
    <td colspan="9" class="t_r" style="text-align: center;" >
        <input type="button" onclick="ok();" value="确定">
        <input type="button" value="关闭" onclick="window.close();">
    </td>
</tr>
</table>
</div>



</div>
</body>
</html>

