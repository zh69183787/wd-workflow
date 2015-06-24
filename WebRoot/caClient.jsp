<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.security.MessageDigest"%>
<%@ page import="java.security.NoSuchAlgorithmException"%>
<%@ page import="java.io.UnsupportedEncodingException"%>
<%@ page import="org.dom4j.DocumentHelper"%>
<%@ page import="org.dom4j.Document"%>
<%@ page import="org.dom4j.Element"%>
<%@ page import="org.dom4j.Node"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.io.FileInputStream"%>
<%@ page import="java.util.Properties"%>
<%@ page import="java.net.HttpURLConnection"%>
<%@ page import="java.net.URL"%>
<%@ page import="java.io.BufferedReader"%>
<%@ page import="java.io.InputStreamReader"%>
<%@ page import="java.io.DataOutputStream"%>
<%@ page import="java.io.OutputStreamWriter"%>
<%@ page import="com.wonders.constants.LoginConstants"%>
<%@ page import="com.wonders.common.model.vo.TaskUserVo"%>
<%@ page import="com.wonders.constants.CommonConstants"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	response.setHeader("Cache-Control", "no-store");// Public
	response.setHeader("Pragma", "no-cache");
	response.setHeader("P3P","CP=CAO PSA OUR");
	response.setDateHeader("Expires", 0);
	String action = request.getParameter("action");
	String sessionKey = request.getParameter("sessionKey");
	//System.out.println("sessionKey="+sessionKey);
	String returnUrl = request.getParameter("returnUrl");
	String appName = "";//="workflow"; 
	String secret = "";//="124a77748fcb48a7a0863f30970a2a04";
	String urlCa = "";//="http://10.1.43.32:8088/ca";
	String serverPath = "";//="/services/api";
	String apiName = "";//="dataExchange";
	Properties properties = new Properties();
	String configPath = Thread.currentThread().getContextClassLoader().getResource("config.properties").getPath();
	try {
		FileInputStream fis = new FileInputStream(configPath);
		properties.load(fis);
		appName = properties.getProperty("appName");
		secret = properties.getProperty("secret");
		urlCa = properties.getProperty("urlCa");
		serverPath = properties.getProperty("serverPath");
		apiName = properties.getProperty("apiName");
		fis.close();
	} catch (Exception e) {
		//e.printStackTrace();
		//appName = "workflow";
		//secret = "124a77748fcb48a7a0863f30970a2a04";
		//urlCa = "http://10.1.43.32:8088/ca";
		//serverPath = "/services/api";
		//apiName = "dataExchange";
	}

	if (returnUrl != null) {
		request.getSession().setAttribute("returnUrl", returnUrl);
	}
%>
<%!public String getUserLoginName(String loginName){
		if(loginName.length()==0) return "";
		String prefix = CommonConstants.LOGINNAME_PREFIX;
		
		if(!loginName.startsWith(prefix)){
			loginName=prefix + loginName;
		}
		
		return loginName;
	}

	public String getMD5(String value) {
		String result = null;
		try {
			byte[] valueByte = value.getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(valueByte);
			result = toHex(md.digest());
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		return result;
	}

	// 将传递进来的字节数组转换成十六进制的字符串形式并返回  
	private String toHex(byte[] buffer) {
		StringBuffer sb = new StringBuffer(buffer.length * 2);
		for (int i = 0; i < buffer.length; i++) {
			sb.append(Character.forDigit((buffer[i] & 0xf0) >> 4, 16));
			sb.append(Character.forDigit(buffer[i] & 0x0f, 16));
		}
		return sb.toString();
	}

	private void saveCookie(HttpServletResponse response, String Name, String value) throws UnsupportedEncodingException {
		Cookie cookie = new Cookie(Name, java.net.URLEncoder.encode(value, "utf-8"));
		cookie.setPath("/");
		response.addCookie(cookie);
	}%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>flow中转页面</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	</head>

	<body>
		<br>
		<%
			if (action != null && action.equalsIgnoreCase("logout")) {
				Cookie cookie = new Cookie(LoginConstants.TOKEN, null);
				cookie.setMaxAge(0);
				cookie.setPath("/");
				response.addCookie(cookie);
				response.sendRedirect(urlCa + "/logout.jsp");
			} else if(action!=null&&action.equalsIgnoreCase("clear")){
	  			Cookie cookie = new Cookie(LoginConstants.TOKEN, null);  
	  	      	cookie.setMaxAge(0);  
	  	     	cookie.setPath("/");	
	  	      	response.addCookie(cookie);	 
	  			request.getSession().invalidate();		
	  			response.sendRedirect(urlCa + "/login.jsp?appName=" + appName);	
			}else if (sessionKey == null) {
				if (returnUrl != null) {
					returnUrl = returnUrl.replace("&","%26").replace("?","%3F").replace("/","%2F");	
					returnUrl = java.net.URLEncoder.encode(returnUrl,"UTF-8");
				}
				response.sendRedirect(urlCa + "/login.jsp?appName=" + appName + "&returnUrl=" + returnUrl);
			} else {

				URL url = null;
				HttpURLConnection http = null;
				String textEntity = "";
				try {
		//System.out.println(urlCa + serverPath + "/" + apiName);
					url = new URL(urlCa + serverPath + "/" + apiName);
					http = (HttpURLConnection) url.openConnection();
					http.setDoInput(true);
					http.setDoOutput(true);
					http.setUseCaches(false);
					http.setConnectTimeout(50000);
					http.setReadTimeout(50000);
					http.setRequestMethod("POST");
					//http.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");			
					http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
					http.connect();
					String param = "&appName=" + appName + "&token=" + sessionKey + "&method=SESSIONINFO.GET&dataType=xml&sign="
							+ getMD5(appName + sessionKey + "SESSIONINFO.GET" + secret);

					OutputStreamWriter osw = new OutputStreamWriter(http.getOutputStream(), "utf-8");
					osw.write(param);
					osw.flush();
					osw.close();

		//System.out.println(http.getResponseCode());
					
					if (http.getResponseCode() == 200) {
						BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream(), "utf-8"));
						String inputLine;
						while ((inputLine = in.readLine()) != null) {
							textEntity += inputLine;
						}
						in.close();
						try {

		//System.out.println(textEntity);

							Document doc = DocumentHelper.parseText(textEntity);

							Element root = doc.getRootElement();
							if (root.element("code").getTextTrim().equalsIgnoreCase("100")) {
								Element params = root.element("params");
								List<Element> list = params.elements();
								Element eParam = (Element) list.get(0);

								Element eToken = eParam.element("token");
								Element eTuser = eParam.element("tuser");
								Element eCsuser = eParam.element("csuser");
								Element edeptUsers = eParam.element("deptUsers");
								List<Element> userlist = edeptUsers.elements();
								if(userlist != null && userlist.size() > 0){
									Map<String,TaskUserVo> userMap = new HashMap<String,TaskUserVo>();
									for(Element e : userlist){
										String deptUserId = e.element("userId").getTextTrim();
										String deptLoginName = getUserLoginName(e.element("loginName").getTextTrim());
										String deptUserName = e.element("userName").getTextTrim();
										String deptDeptId = e.element("deptId").getTextTrim();
										String deptDeptName = e.element("deptName").getTextTrim();
										String deptLoginNameNoSt = e.element("loginName").getTextTrim();
										TaskUserVo vo = new TaskUserVo(deptUserId,deptLoginName,deptUserName,deptDeptId,deptDeptName,deptLoginNameNoSt);
										userMap.put(deptLoginName,vo);
									}
									session.setAttribute(LoginConstants.DEPT_USER, userMap);
								}
								
								Element eUserId = eCsuser.element("userId");
								Element eLoginName = eCsuser.element("loginName");
								Element eUserName = eCsuser.element("userName");
								Element eDeptId = eCsuser.element("deptId");
								Element eDeptName = eCsuser.element("deptName");
			
								Element etUserId = eTuser.element("id");
								Element etLoginName = eTuser.element("loginName");
								Element etUserName = eTuser.element("name");

								/*具体cookie的存储模式需要客户*/

								String strUserId = eUserId.getTextTrim();
								String strLoginName = eLoginName.getTextTrim();
								String strUserName = eUserName.getTextTrim();
								String strDeptId = eDeptId.getTextTrim();
								String strDeptName = eDeptName.getTextTrim();
								String strToken = eToken.getTextTrim();
								String tLoginName = etLoginName.getTextTrim();
								
								saveCookie(response, "tLoginName", tLoginName);
								saveCookie(response, "userId", strUserId);
								saveCookie(response, "loginName", strLoginName);
								saveCookie(response, "userName", strUserName);
								saveCookie(response, "deptId", strDeptId);
								saveCookie(response, "deptName", strDeptName);
								saveCookie(response, LoginConstants.TOKEN, strToken);
								session.setAttribute(LoginConstants.USERID, strUserId);
								session.setAttribute(LoginConstants.LOGINNAME, strLoginName);
								session.setAttribute(LoginConstants.USERNAME, strUserName);
								session.setAttribute(LoginConstants.DEPTID, strDeptId);
								session.setAttribute(LoginConstants.DEPTNAME, strDeptName);
								session.setAttribute(LoginConstants.TOKEN, strToken);
								session.setAttribute(LoginConstants.T_LOGINNAME, tLoginName);
								//System.out.println("returnUrl"+returnUrl);
								//response.sendRedirect(returnUrl);
		%>
								<script>
			window.location.href = "<%=returnUrl%>";
		</script>
<%
							} else {
		%>
		无法取得相关认证用户信息:<%=root.element("description").getTextTrim()%><br>
		<br>
		请重新
		<a href="<%=urlCa%>/logout.jsp?appName=<%=appName%>&returnUrl=<%=returnUrl%>" target="_self">登录认证中心！</a>

		<%
			}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
		%>
		CA认证中心服务端返回数据格式错误，请联系管理员！

		<%
			}

					} else {
		%>
		无法取得相关认证用户信息
		<br>
		请重新
		<a href="<%=urlCa%>/logout.jsp?appName=<%=appName%>&returnUrl=<%=returnUrl%>" target="_self">登录认证中心！</a>

		<%
			}
				} catch (Exception e) {
					e.printStackTrace();
		%>
		链接CA服务请求出错，请联系管理员！

		<%
			} finally {
					if (http != null)
						http.disconnect();
				}

			}
		%>
	</body>
</html>
