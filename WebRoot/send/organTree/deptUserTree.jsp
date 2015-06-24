<%@ page contentType="text/html; charset=UTF-8"%>
<%
response.setHeader("Charset","UTF-8'");
String path = request.getContextPath();
String zoneId = request.getParameter("zoneId");
%>
<!DOCTYPE html>
<html lang="cn">
<head>
<meta charset="utf-8" />
	<link rel="stylesheet" href="<%=path %>/send/organTree/css/demo.css" type="text/css">
	<link rel="stylesheet" href="<%=path %>/send/organTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<link type="text/css" href="<%=path %>/send/organTree/css/flick/jquery-ui-1.8.21.custom.css" rel="stylesheet" />
	
	<script type="text/javascript" src="<%=path %>/send/organTree/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/send/organTree/js/jquery.ztree.all-3.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/send/organTree/js/jquery-ui-1.8.21.custom.min.js"></script>

	<script type="text/javascript">
		var zoneId = "<%=zoneId%>";
		var root = "";//树根节点id
		var checkNodes = "";//默认选中id
		
		var zNodes = "";//顶层默认节点 json
		
		var treeId = "treeDemo";//树div的id

		var targetLoginName = "";//父页面工号 input的id值
		var targetId = "";//父页面id input的id值
		var targetNameId = "";//父页面名称input的id值

		var setting = {
			async: {
				enable: true,
				url:"<%=path %>/send-organTree/getDeptUsersOffLeaders.action",
				autoParam:["id"],
				dataFilter: filter
			},
			check: {
				enable: true,
				//chkStyle: "radio",
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
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			expand(treeNode);
			var checkarr = checkNodes.split(",");
			for(var i=0;i<checkarr.length;i++){
				var node = zTree.getNodeByParam("id", checkarr[i], null);
				if(node!=null){
				//选中节点
					zTree.checkNode(node, true, false, true);
				}
			}
		}
		
		//展开节点
		function expand(node){
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
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
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			nodes = zTree.getCheckedNodes(true),
			v1 = "";
			v2 = "";
			v3 = "";
			
			for (var i=0, l=nodes.length; i<l; i++) {
				v1 += nodes[i].id + ",";
				v2 += nodes[i].loginName + ",";
				v3 += nodes[i].name + ",";
			}
			if (v1.length > 0 ) v1 = v1.substring(0, v1.length-1);
			var cityObj = $("#nodeId");
			cityObj.attr("value", v1);
			
			if (v2.length > 0 ) v2 = v2.substring(0, v2.length-1);
			var cityObj = $("#nodeLoginName");
			cityObj.attr("value", v2);
			
			if (v3.length > 0 ) v3 = v3.substring(0, v3.length-1);
			var cityObj = $("#tnodeName");
			cityObj.attr("value", v3);	
		}	
		
		
		//根据父页面提供信息初始化参数
		function initParamValue(){
			if(zoneId!=""){
				var zone = $("#"+zoneId,window.opener.document);
				//console.log(zone);
				root = $(zone).attr("root");
				targetId = $(zone).attr("nodeId");
				targetNameId = $(zone).attr("tnodeName");
				targetLoginName = $(zone).attr("nodeLoginName");
				var checkNodesZoneId = $(zone).attr("checkNode");
				checkNodes = $("input[name='" + checkNodesZoneId + "']:not(:disabled)",window.opener.document).val();
			}else{
				return false;
			}
		}

		//获得根节点json
		function initRootNodes(){
			if(zNodes==""){
				var url = "<%=path %>/send-organTree/getNodesInfo.action";
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
							zNodes = json;
							initByZNodes();
						}
					}
				);
			}
		}

		//自动展开并选中指定节点
		function initByZNodes(){
			$.fn.zTree.init($("#"+treeId), setting, zNodes);
			var zTree = $.fn.zTree.getZTreeObj(treeId);
			var node = zTree.getNodeByParam("id", root , null);
			asyncNode(node);
		}

		function intiButton(){
			$("#choiceOk").click(function(){
				$("input[name='" + targetId + "']:not(:disabled)",window.opener.document).attr("value",$("#nodeId").val());
				$("input[name='" + targetNameId + "']:not(:disabled)",window.opener.document).attr("value",$("#tnodeName").val());
				$("input[name='" + targetLoginName + "']:not(:disabled)",window.opener.document).attr("value",$("#nodeLoginName").val());
				
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
			intiButton();
			initRootNodes();
		});
	</script>
	
	<script type="text/javascript">
	var zoneId = "<%=zoneId%>";
	var root = "";//树根节点id
	var checkNodes = "";//默认选中id
	
	var zNodes = "";//顶层默认节点 json
	
	var treeId = "treeDemo";//树div的id
	
	var targetId = "";//父页面id input的id值
	var targetNameId = "";//父页面名称input的id值
	
	$(document).ready(function(){
		$.fn.zTree.init($("#"+treeId), setting, zNodes);
		//$.fn.zTree.init($("#treeDemo"), setting);
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		var node = zTree.getNodeByParam("id", root , null);
		asyncNode(node);
	});
	
	</script>
</head>
<body>

<div style="width:650px;">
<form>
	<font style="font-size:12px;">
		&nbsp;&nbsp;请选择: 
	</font>
	<input id="tnodeName" type="text" readonly value="" style="width:75%;"/>
	<input id="nodeId" type="hidden" readonly value="" style="width:80%;" />
	<input id="nodeLoginName" type="hidden" readonly value="" style="width:80%;" />
	<input id="choiceOk" type="button" value="确定"/>
	<input id="choiceCancel" type="button" value="取消"/>
</form>
	<ul id="treeDemo" class="ztree" style="border:none;margin-top:0; width:100%; height: 600px;"></ul>
</div>

</body>
</html>