package com.wonders.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.util.AntPathMatcher;

import com.wonders.receive.workflow.common.util.SimpleLogger;
import com.wonders.receive.workflow.constants.LoginConstants;
import com.wonders.util.StringUtil;

public class LoginFilter implements Filter{
	protected final String P_NOT_LOGIN_PAGE = "notLoginPage";
	protected final String P_IGNORE_URLS = "ignoreUrls";
	protected final String URL_SPLITER = ",";
	private String notLoginPage = null;
	private String[] ignoreUrl = null; 
	private AntPathMatcher urlMatcher = new AntPathMatcher();
	Logger log = SimpleLogger.getLogger(this.getClass());
	
	public void destroy() {
		//StrutsPrepareAndExecuteFilter t = null;
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;    
	    HttpServletResponse response = (HttpServletResponse) servletResponse;  
	    //Properties properties = new Properties();
    	//String path = Thread.currentThread().getContextClassLoader().getResource("config.properties").getPath();
	    
	    String url = getCurrUrl(request);
//System.out.println("url:"+url);

	    // 排除跳转页和已配置的不需验证页面
		if (notLoginPage.equals(url)) {
			chain.doFilter(request, response);
			return;
		}
		if (ignoreUrl != null && ignoreUrl.length > 0) {
			for (int i = 0; i < ignoreUrl.length; i++) {
				if(urlMatcher.match(ignoreUrl[i].trim(), url)) {
				    //if (url.equals(ignoreUrl[i].trim())) {
					    chain.doFilter(servletRequest, servletResponse);
					    return ;
				    }
			}
		}
		
		if(url!=null && url.length() > 0 && url.indexOf("print") >= 0){
			chain.doFilter(servletRequest, servletResponse);
			return;
		}
		
		if(url!=null && url.length() > 0 && url.indexOf("printDocSend") >= 0){
			chain.doFilter(servletRequest, servletResponse);
			return;
		}
		
		if(url.startsWith("/attach/")){
			chain.doFilter(servletRequest, servletResponse);
			return;
		}
		
		if(url.startsWith("/contact/ultimus/")){
			chain.doFilter(servletRequest, servletResponse);
			return;
		}
		
		if(url.startsWith("/contact-ultimus/")){
			chain.doFilter(servletRequest, servletResponse);
			return;
		}
		
		if(url.startsWith("/processController/")){
//log.debug("processController:");
			chain.doFilter(servletRequest, servletResponse);
			return;
		}
		
		if(url.startsWith("/contact-api/")){
//log.debug("todo:");
			chain.doFilter(servletRequest, servletResponse);
			return;
		}
		
		String token=getCookieByName(request,LoginConstants.TOKEN);
	    if(token==null || request.getSession().getAttribute(LoginConstants.T_LOGINNAME)==null){
	    	String returnUrl=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ request.getRequestURI();
			if(request.getQueryString()!=null){   
				//连接中中文已编码  encodeURI encodeURIComponent
				//queryString  获取连接中若有中文，中文仍为编码后UTF8码，而getParameter为中文
				returnUrl+="?"+request.getQueryString();
				//对? & 编码 避免参数截取错误
				returnUrl = returnUrl.replace("&","%26").replace("?","%3F").replace("/","%2F");	
			}
			
			response.sendRedirect(request.getContextPath()+"/caClient.jsp?returnUrl="+returnUrl);
	    	return;
	    }else{
	    	chain.doFilter(request, response);
	    	return;
	    }
		/*
	    //System.out.println("ssssssssssssssssssssss");
		Cookie[] cookies = request.getCookies();
		CrossIpLogin crossIpLogin = new CrossIpLogin();
		//UserInfo userInfo = new UserInfo();
		//crossIpLogin.setUserInfo(request,userInfo);
		String userId = crossIpLogin.getCookieByName(request,"userId");
		//CrossIpLogin crossIpLogin = new CrossIpLogin();
		//String token = crossIpLogin.getCookieByName(request,"token");
		properties.load(new FileInputStream(path));
		String filterButton=properties.getProperty("filterButton");
		
		if("on".equals(filterButton)){
			if (null == userId||"".equals(userId)) {
				if(null != crossIpLogin.getCookieByName(request,"token")){
					String token = crossIpLogin.getCookieByName(request,"token");
					String result = crossIpLogin.postSecretXML(token);
					if("error".equals(result)){
						response.sendRedirect(request.getContextPath()+"/pages/error.jsp");
					}else{
						crossIpLogin.saveCookieXML(result,response);
						chain.doFilter(servletRequest, servletResponse);
					}				
				}else{
					response.sendRedirect(request.getContextPath()+"/pages/timeOut.jsp");
					 //response.sendRedirect(request.getContextPath()+notLoginPage);
				}
			}else{
				chain.doFilter(servletRequest, servletResponse);
			}
		}else{
			chain.doFilter(servletRequest, servletResponse);
		}
		*/
	}

	public void init(FilterConfig config) throws ServletException {
		
		notLoginPage = StringUtil.getNotNullValueString(config.getInitParameter(P_NOT_LOGIN_PAGE));
		/*
		if (notLoginPage == null || "".equals(notLoginPage)) {
			String s = "未配置未登录跳转页，"+ this.getClass().getName() +"加载失败";
			//logger.error(s);
			throw new ServletException(s);
		}
		*/
		String ignoreUrls = config.getInitParameter(P_IGNORE_URLS);
		if (ignoreUrls != null) {
			ignoreUrl = ignoreUrls.split(URL_SPLITER); 
		}
	}
	
	
	static String getCurrUrl(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String ctxpath = request.getContextPath();
		if (!"".equals(ctxpath)) {
			return uri.substring(ctxpath.length());
		} else {
			return uri;
		}
	}
	
	public static String getCookieByName(HttpServletRequest request, String name) {
		String cookieValue=null;
		Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (int i = 0; i < cookies.length; i++) {
            	Cookie cookie = cookies[i];
        		
        		if(name.equals(cookie.getName())){
        			try{
        				cookieValue = java.net.URLDecoder.decode(cookie.getValue(),"utf-8");
        			} catch (UnsupportedEncodingException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
        			break;
        		}
            }
        }
        return cookieValue;
	}
  public static void main(String[]args){
	  AntPathMatcher a = new AntPathMatcher();
	  System.out.println(a.match("/login/**", "/login/aaa/path.xxxx"));
	  System.out.println("/send-tDocSend/printDocSend.action".indexOf("/print"));
  }
}
