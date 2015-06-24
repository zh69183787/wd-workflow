<%@ page contentType="text/html; charset=UTF-8"%>
<%
response.setHeader("Charset","UTF-8'");
String path = request.getContextPath();
String zoneId = request.getParameter("zoneId");
String processName = request.getParameter("processName");
 %>
<!DOCTYPE html>
<html lang="cn">
<head>
<meta charset="utf-8" />
<link rel="stylesheet" href="<%=path %>/discipline/organTree/css/demo.css" type="text/css">
	<link rel="stylesheet" href="<%=path %>/discipline/organTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="<%=path %>/discipline/organTree/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="<%=path %>/discipline/organTree/js/jquery.ztree.all-3.5.js"></script>
	
	<script type="text/javascript">
		var zoneId = "<%=zoneId%>";
		var root = "";//树根节点id
		var checkNodes = "";//默认选中id
		var first = "0";
		var zNodes = "";//顶层默认节点 json
		
		var deptsId = "";
		var deptsName = "";
		var recvsId = "";
		var recvsName = "";
		var ldsId = "";
		var ldsName = "";
		var treeType = ""; 
		
		var arr = new Array();
		
		var setting = {
			async: {
				enable: true,
				url:"<%=path %>/discipline-organTree/getChildNodes.action",
				//autoParam:["id=nodesId"],
				autoParam:["id"],
				otherParam: ["processName", "<%=processName%>"],
				dataFilter: filter
			},
			check: {
				enable: true,
				chkboxType: {"Y":"", "N":""}
			},
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey:"pid"
				}
			},
			callback: {
				beforeClick: beforeClick,
				onCheck: onCheck,
				onAsyncSuccess: onAsyncSuccess
			}
		};

		//强行异步加载某个父节点下的子节点
		function asyncNode(node){
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.reAsyncChildNodes(node, "refresh", true);
		}
		
		//异步加载成功回调函数
		function onAsyncSuccess(event, treeId, treeNode, msg) {
			if(treeNode.id==2500 && first == "0"){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var tempArr = new Array();
				var m = 0;	
				for(var i=0;i<arr.length;i++){
					var node = zTree.getNodeByParam("id", arr[i] , null);
					if(node!=null){
						if(node.isParent){
							tempArr[m] = arr[i];
							m++;
						}
					}
				}	
				arr = tempArr;
			}
			
			if(first == "1"){
				expand(treeNode);
			}else if(arr.length>0 && first == "0"){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				expand(treeNode);
				arr.splice(0,1);
				var node = zTree.getNodeByParam("id", arr[0] , null);
				if(arr.length>0){
					asyncNode(node);
				}else{
					var checkarr = checkNodes.split(",");
					for(var i=0;i<checkarr.length;i++){
						var node = zTree.getNodeByParam("id", checkarr[i], null);
						if(node!=null){
						//选中节点
							zTree.checkNode(node, true, false, true);
						}
					}
				}
			}
		}
		
		//展开节点
		function expand(node){
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			status = "expand";
			zTree.expandNode(node, true, false, false);
		}

		function filter(treeId, parentNode, childNodes) {
				if (!childNodes) return null;
				for (var i=0, l=childNodes.length; i<l; i++) {
					childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
				}
				return childNodes;
			}

		function beforeClick(treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		}
		
		function onCheck(e, treeId, treeNode) {
			/**/
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			nodes = zTree.getCheckedNodes(true),
			v1 = "";
			v2 = "";
			v3 = "";
			v4 = "";
			v5 = "";
			v6 = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				v1 += nodes[i].id + ",";
				v2 += nodes[i].name + ",";
				v3 += nodes[i].recvLoginName + ",";
				v4 += nodes[i].recvName + ",";
				v5 += nodes[i].ldLoginName + ",";
				v6 += nodes[i].ldName + ",";
			
			}
			if (v1.length > 0 ) v1 = v1.substring(0, v1.length-1);
			var cityObj = $("#deptId");
			cityObj.attr("value", v1);

			if (v2.length > 0 ) v2 = v2.substring(0, v2.length-1);
			var cityObj = $("#deptName");
			cityObj.attr("value", v2);
			
			if (v3.length > 0 ) v3 = v3.substring(0, v3.length-1);
			var cityObj = $("#recvLoginName");
			cityObj.attr("value", v3);
			
			if (v4.length > 0 ) v4 = v4.substring(0, v4.length-1);
			var cityObj = $("#recvName");
			cityObj.attr("value", v4);
			
			if (v5.length > 0 ) v5 = v5.substring(0, v5.length-1);
			var cityObj = $("#ldLoginName");
			cityObj.attr("value", v5);
			
			if (v6.length > 0 ) v6 = v6.substring(0, v6.length-1);
			var cityObj = $("#ldName");
			cityObj.attr("value", v6);
			
		}	
		
		
		//初始化树后  若有checknode  ajax得到该节点经过的路径  若路径最高点 大于 传入的 根节点  则舍弃
		function getNodePath(){
			$.ajax({
				type: 'POST',
				url: '<%=path %>/discipline-organTree/getRelatedNodes.action?random='+Math.random(),
				data:{
						"id":checkNodes
					},
				dataType:'json',
				cache : false,
				error:function(){alert('系统连接失败，请稍后再试！')},
				success: function(obj){
					var j = 0;	
					for(var i=0;i<obj.length;i++){
						if(obj[i].id>=root*1){
							arr[j] = obj[i].id;
							j++;
						}
					}
					var zTree = $.fn.zTree.getZTreeObj("treeDemo");
					var node = zTree.getNodeByParam("id", root , null);
					asyncNode(node);
				}  
			});
		}

		//根据父页面提供信息初始化参数
		function initParamValue(){
			if(zoneId!=null){
				var zone = $("#"+zoneId,self.opener.document);
				root = $(zone).attr("root");
				deptsId = $(zone).attr("deptIds");
				deptsName = $(zone).attr("deptNames");
				recvsId = $(zone).attr("deptRecvsId");
				recvsName = $(zone).attr("deptRecvsName");
				ldsId = $(zone).attr("deptLdsId");
				ldsName = $(zone).attr("deptLdsName");
				treeType = $(zone).attr("treeType");
				if(treeType == "radio"){
					setting.check.chkStyle="radio";
				}
				var checkNodesZoneId = $(zone).attr("checkNode");
				checkNodes = $("#"+checkNodesZoneId,self.opener.document).val();
			}else{
				alert("zone null");
				return false;
			}
		}

		//自动展开并选中指定节点
		function initByZNodes(){
			$.fn.zTree.init($("#"+"treeDemo"), setting, zNodes);
			if(checkNodes.length>0){
				getNodePath();
			}else{
				first = "1";
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var node = zTree.getNodeByParam("id", root , null);
				asyncNode(node);
			}
		}

		//获得根节点json
		function initRootNodes(){
			if(zNodes==""){
				var url = "<%=path %>/discipline-organTree/getNodesInfo.action";
				$.getJSON(
					url,
					{
						id:root,
						rand:new Date().getTime()
					},
					function(json){
						if(json){
							json[0].nocheck=true;
							root = json[0].id;
							json[0].icon = "<%=path %>/discipline/organTree/img/com.png";
							zNodes = json;
							initByZNodes();
						}
					}
				);
			}else{
				//alert(zNodes);
			}
		}

		function intiButton(){
			$("#choiceOk").click(function(){
				$("#"+deptsId,window.opener.document).attr("value",$("#deptId").val());
				$("#"+deptsName,window.opener.document).attr("value",$("#deptName").val());
				$("#"+recvsId,window.opener.document).attr("value",$("#recvLoginName").val());
				$("#"+recvsName,window.opener.document).attr("value",$("#recvName").val());
				$("#"+ldsId,window.opener.document).attr("value",$("#ldLoginName").val());
				$("#"+ldsName,window.opener.document).attr("value",$("#ldName").val());
				
				
				self.close();
				return false;
			});

			$("#choiceCancel").click(function(){
				self.close();
				return false;
			});
		}
		
		$(document).ready(function(){
			initParamValue();
			initRootNodes();
			intiButton();
		});
		
	</script>
	
</head>
<body>
<div style="width:650px;">
	<ul>
		<form>
		<font style="font-size:12px;">
		&nbsp;&nbsp;请选择: 
		</font>
		<input id="deptName" type="text" readonly value="" style="width:75%;"/>
		<input id="deptId" type="hidden" readonly value="" style="width:80%;"/>
		<input id="recvLoginName" type="hidden" readonly value="" style="width:80%;"/>
		<input id="recvName" type="hidden" readonly value="" style="width:80%;"/>
		<input id="ldLoginName" type="hidden" readonly value="" style="width:80%;"/>
		<input id="ldName" type="hidden" readonly value="" style="width:80%;"/>
		
		<input id="choiceOk" type="button" value="确定"/>
		<input id="choiceCancel" type="button" value="取消"/>
		</form>
	</ul>
	<ul id="treeDemo" class="ztree" style="border:none;margin-top:0; width:100%; height: 600px;"></ul>
</div>

</body>
</html>