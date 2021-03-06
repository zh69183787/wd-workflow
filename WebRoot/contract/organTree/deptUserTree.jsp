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
	<link rel="stylesheet" href="<%=path %>/contract/organTree/css/demo.css" type="text/css">
	<link rel="stylesheet" href="<%=path %>/contract/organTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="<%=path %>/contract/organTree/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="<%=path %>/contract/organTree/js/jquery.ztree.all-3.5.js"></script>
	
	<script type="text/javascript">
		var zoneId = "<%=zoneId%>";
		var root = "";//树根节点id
		var checkNodes = "";//默认选中id
		
		var zNodes = "";//顶层默认节点 json
		
		var treeId = "treeDemo";//树div的id

		var dealPersonId = "";
		var dealPersonName = "";
		var dealPersonDeptId = "";
		var treeType = ""; 
		

		var setting = {
			async: {
				enable: true,
				url:"<%=path %>/contract-organTree/getDeptUsersOffLeaders.action",
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
				var node = zTree.getNodeByParam("loginName", checkarr[i], null);
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
				v1 += nodes[i].loginName + ",";
				v2 += nodes[i].name + ",";
				v3 += nodes[i].pid + ",";
			}
			if (v1.length > 0 ) v1 = v1.substring(0, v1.length-1);
			var cityObj = $("#dealPersonLoginName");
			cityObj.attr("value", v1);
			
			if (v2.length > 0 ) v2 = v2.substring(0, v2.length-1);
			var cityObj = $("#dealPersonName");
			cityObj.attr("value", v2);
			
			if (v3.length > 0 ) v3 = v3.substring(0, v3.length-1);
			var cityObj = $("#dealPersonDeptId");
			cityObj.attr("value", v3);	
		}	
		
		
		//根据父页面提供信息初始化参数
		function initParamValue(){
			if(zoneId!=""){
				var zone = $("#"+zoneId,window.opener.document);
				//console.log(zone);
				root = $(zone).attr("root");
				dealPersonId = $(zone).attr("dealPersonLoginName");
				dealPersonName = $(zone).attr("dealPersonName");
				dealPersonDeptId = $(zone).attr("dealPersonDeptId");
				treeType = $(zone).attr("treeType");
				if(treeType == "radio"){
					setting.check.chkStyle="radio";
				}
				var checkNodesZoneId = $(zone).attr("checkNode");
				checkNodes = $("input[name='" + checkNodesZoneId + "']:not(:disabled)",window.opener.document).val();
			}else{
				return false;
			}
		}

		//获得根节点json
		function initRootNodes(){
			if(zNodes==""){
				var url = "<%=path %>/contract-organTree/getNodesInfo.action";
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
							json[0].icon = "<%=path %>/contract/organTree/img/com.png";
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
				$("input[name='" + dealPersonId + "']:not(:disabled)",window.opener.document).attr("value",$("#dealPersonLoginName").val());
				$("input[name='" + dealPersonName + "']:not(:disabled)",window.opener.document).attr("value",$("#dealPersonName").val());
				$("input[name='" + dealPersonDeptId + "']:not(:disabled)",window.opener.document).attr("value",$("#dealPersonDeptId").val());
				
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
	<input id="dealPersonName" type="text" readonly value="" style="width:75%;"/>
	<input id="dealPersonLoginName" type="hidden" readonly value="" style="width:80%;" />
	<input id="dealPersonDeptId" type="hidden" readonly value="" style="width:80%;" />
	<input id="choiceOk" type="button" value="确定"/>
	<input id="choiceCancel" type="button" value="取消"/>
</form>
	<ul id="treeDemo" class="ztree" style="border:none;margin-top:0; width:100%; height: 600px;"></ul>
</div>

</body>
</html>