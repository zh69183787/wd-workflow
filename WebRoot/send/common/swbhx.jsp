<%@page contentType="text/html; charset=UTF-8"
	import="com.wonders.send.util.SWUtil"%>
<html>
	<head></head>
	<script type="text/javascript">
		function init(){
			var temp = document.getElementsByName('raidos');
			temp[0].checked =true;
		}
		function submitWin(){
		var sData = dialogArguments; 
			var temp = document.getElementsByName('raidos');
			for(var i = 0;i<temp.length;i++){
				if(temp[i].checked){
					sData.noSe = temp[i].value;
				}
			}
			sData.setNo();
			window.close();
		}
	</script>
	<body onload="init()">
		<table align="center" height="100%" width="100%">
			<tr valign="top">
				<td>
					<table bordercolorlight="#000000" bordercolordark="#FFFFFF"
						border=1 cellpadding="0" cellspacing="0" align="center" width="100%">
						<tr>
							<td colspan="2" align="center" class="bg">请选择预存号码</td>
						</tr>
						<% 
							//out.println(request.getParameter("typeId"));
							String temp = SWUtil.showYLNew(request.getParameter("drSwtype"),request.getParameter("swYear"),request.getParameter("typeId"),request.getParameter("parentCode"));
							if(temp != null && !"".equals(temp)){
								String[] temps = temp.split(";");
								for(int i = 0; i<temps.length; i++){
								%>
								<tr>
									<td width="1%">
										<input type="radio" id="<%=temps[i]%>" name="raidos" value="<%=temps[i] %>" />
									</td>
									<td width="10%" nowrap="nowrap">
										<input type="text" value="<%=temps[i] %>" class="inputLine" readonly="readonly" size="10%" />
									</td>
								</tr>
								<%
								}
							}
						 %>
						 
						 <tr>
						 	<td colspan="2" align="center">
						 	<input type="hidden"  name="raidos" />
						 		<input type="button" value="提交" onclick="submitWin()" class="btn"/>
						 	</td>
						 </tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
