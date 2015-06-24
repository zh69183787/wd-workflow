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
	<link rel="stylesheet" href="<%=path %>/receive/organTree/css/demo.css" type="text/css">
	<link rel="stylesheet" href="<%=path %>/receive/organTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	
	<script type="text/javascript" src="<%=path %>/receive/organTree/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="<%=path %>/receive/organTree/js/jquery.ztree.all-3.5.js"></script>
	
	<script type="text/javascript">
		var zoneId = "<%=zoneId%>";
		var root = "";//树根节点id
		var checkNodes = "";//默认选中id
		
		var zNodes = "";//顶层默认节点 json
		
		var treeId = "treeDemo";//树div的id

		var ldsId = "";
		var ldsName = "";
		var ldsDepId = "";
		var secsId = "";
		var secsName = "";
		var secsDepId = "";
		
		var setting = {
			async: {
				enable: true,
				url:"<%=path %>/receive-organTree/getComLeaders.action",
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
		
		var v1 = new Array();
		var v2 = new Array();
		var v3 = new Array();
		var v4 = new Array();
		var v5 = new Array();
		var v6 = new Array();
		
		function onCheck(e, treeId, treeNode) {
			if(treeNode.checked){
				v1.push(treeNode.id);
				v2.push(treeNode.leaderName);
				v3.push(treeNode.leaderDeptId);
				v4.push(treeNode.secretaryLoginName);
				v5.push(treeNode.secretaryName);
				v6.push(treeNode.secretaryDeptId);
			}else{
				var pos = $.inArray(treeNode.id,v1);
				v1.splice(pos,1);
				v2.splice(pos,1);
				v3.splice(pos,1);
				v4.splice(pos,1);
				v5.splice(pos,1);
				v6.splice(pos,1);
				
			}
			
			var cityObj = $("#leaderLoginName");
			cityObj.attr("value", v1.join());
			
			var cityObj = $("#leaderName");
			cityObj.attr("value", v2.join());
			
			var cityObj = $("#leaderDeptId");
			cityObj.attr("value", v3.join());	
			var cityObj = $("#secretaryLoginName");
			cityObj.attr("value", v4.join());
			
			var cityObj = $("#secretaryName");
			cityObj.attr("value", v5.join());
			
			var cityObj = $("#secretaryDeptId");
			cityObj.attr("value", v6.join());	
		}	
		
		
		//根据父页面提供信息初始化参数
		function initParamValue(){
			if(zoneId!=""){
				var zone = $("#"+zoneId,window.opener.document);
				//console.log(zone);
				root = $(zone).attr("root");
				ldsId = $(zone).attr("comLeaderLoginName");
				ldsName = $(zone).attr("comLeaderName");
				ldsDepId = $(zone).attr("comLeaderDeptId");
				secsId = $(zone).attr("comSecLoginName");
				secsName = $(zone).attr("comSecName");
				secsDepId = $(zone).attr("comSecDeptId");
				var checkNodesZoneId = $(zone).attr("checkNode");
				checkNodes = $("input[name='" + checkNodesZoneId + "']:not(:disabled)",window.opener.document).val();
			}else{
				return false;
			}
		}

		//获得根节点json
		function initRootNodes(){
			if(zNodes==""){
				
				var url = "<%=path %>/receive-organTree/getNodesInfo.action";
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
							json[0].icon = "<%=path %>/receive/organTree/img/users.png";
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
				$("input[name='" + ldsId + "']:not(:disabled)",window.opener.document).attr("value",$("#leaderLoginName").val());
				$("input[name='" + ldsName + "']:not(:disabled)",window.opener.document).attr("value",$("#leaderName").val());
				$("input[name='" + ldsDepId + "']:not(:disabled)",window.opener.document).attr("value",$("#leaderDeptId").val());
				$("input[name='" + secsId + "']:not(:disabled)",window.opener.document).attr("value",$("#secretaryLoginName").val());
				$("input[name='" + secsName + "']:not(:disabled)",window.opener.document).attr("value",$("#secretaryName").val());
				$("input[name='" + secsDepId + "']:not(:disabled)",window.opener.document).attr("value",$("#secretaryDeptId").val());
				
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
	
</head>
<body>

<div style="width:650px;">
<form>
	<font style="font-size:12px;">
		&nbsp;&nbsp;请选择: 
	</font>
	<input id="leaderName" type="text" readonly value="" style="width:75%;"/>
	<input id="leaderLoginName" type="hidden" readonly value="" style="width:80%;" />
	<input id="leaderDeptId" type="hidden" readonly value="" style="width:80%;" />
	<input id="secretaryLoginName" type="hidden" readonly value="" style="width:80%;" />
	<input id="secretaryName" type="hidden" readonly value="" style="width:80%;" />
	<input id="secretaryDeptId" type="hidden" readonly value="" style="width:80%;" />
	<input id="choiceOk" type="button" value="确定"/>
	<input id="choiceCancel" type="button" value="取消"/>
</form>
	<ul id="treeDemo" class="ztree" style="border:none;margin-top:0; width:100%; height: 600px;"></ul>
</div>

</body>
</html>