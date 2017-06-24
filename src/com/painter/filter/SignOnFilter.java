package com.painter.filter;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.*;

import com.salmat.jbm.bean.*;
import com.salmat.jbm.hibernate.Employee;
import com.salmat.jbm.hibernate.MenuItemPermission;
import com.salmat.jbm.service.MenuItemPermissionService;
import com.painter.util.CookieUtils;
import com.painter.util.Global;
import com.painter.util.SessionUtil;
import com.painter.util.Util;

public class SignOnFilter implements Filter {
    private static Logger log = Logger.getLogger(SignOnFilter.class);
    private static MenuItemPermissionService pemissionService = MenuItemPermissionService.getInstance();
	
    private FilterConfig config = null;
 
    public static String SIGNON_PAGE = "Login.jsp";
    
    public static String BACKEND_SIGNON_PAGE = "adminLogin.do";

    public static String FORM_HIME_PAGE_URL = "homePage.do";
    
    public static String FORM_SIGNON_URL = "login.do";
    
    private static String[] normalUrl = {"login.do"};
    
    
    public void init(FilterConfig config) throws ServletException {
        this.config = config;
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hreq = (HttpServletRequest) request;
        HttpServletResponse hres = (HttpServletResponse) response;
        //log.info(hreq.getPathInfo());
        process(request, response, chain);
        //log.info("SignOnFilter - completed !");
    }

    private void process(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws ServletException, IOException {

        HttpServletRequest hreq = (HttpServletRequest) request;
        Enumeration o = hreq.getParameterNames();
           
        
        HttpServletResponse hres = (HttpServletResponse) response;
        String language = CookieUtils.getLanguage((HttpServletRequest)request);
        String targetURL = getTargetURL(hreq);
        String queryString = hreq.getQueryString();
        if (queryString == null) queryString="";
        

        
        
        request.setAttribute("targetURI", targetURL);
        request.setAttribute("queryString", queryString);
        
        List<String> normalUrlList = Arrays.asList(normalUrl);
        //log.info("process  url >>>> " + hreq.getRequestURL().toString());
        //log.info("targetURL >>>>>>> "+ targetURL);

        /*
        if ( (targetURL != null) && backendUrlList.contains(targetURL)) {
        	if (targetURL.equalsIgnoreCase("applyUserAccount.do") ) {
        		// �w��|��A��, ���� subMenu
        		//if (isAuthorized(hreq)) 
        			//processMenu(hreq);
        	}
            chain.doFilter(request, response);
             log.info("after validateSignOn()");
            return;
        }
        
        
        if (!isFacebookSignedOn(hreq)) {
        	// String fbLoginURL="https://graph.facebook.com/oauth/authorize?client_id=147574771933336&redirect_uri=http://192.168.2.99:8080";
            log.info("user did not logged");
            ServletContext sc = config.getServletContext();
            //((HttpServletResponse) response).sendRedirect("http://www.facebook.com/connect/uiserver.php?display=page&next=https://graph.facebook.com/oauth/authorize_success%3Fredirect_uri%3Dhttp%253A%252F%252Fapps.facebook.com%252Fsharethrift%252F%26client_id%3D141301989228105%26type%3Dweb_server&cancel_url=https://graph.facebook.com/oauth/authorize_cancel%3Fredirect_uri%3Dhttp%253A%252F%252Fapps.facebook.com%252Fsharethrift%252F%26client_id%3D141301989228105%26type%3Dweb_server&return_session=1&canvas=1&app_id=141301989228105&method=permissions.request&perms=read_insights,read_friendlists,publish_stream,email,user_checkins,friends_checkins,manage_pages&from_login=1");
            RequestDispatcher rd = sc.getRequestDispatcher("/FacebookLogin.jsp");
            //RequestDispatcher rd = sc.getRequestDispatcher("http://www.facebook.com/connect/uiserver.php?display=page&next=https://graph.facebook.com/oauth/authorize_success%3Fredirect_uri%3Dhttp%253A%252F%252Fapps.facebook.com%252Fsharethrift%252F%26client_id%3D141301989228105%26type%3Dweb_server&cancel_url=https://graph.facebook.com/oauth/authorize_cancel%3Fredirect_uri%3Dhttp%253A%252F%252Fapps.facebook.com%252Fsharethrift%252F%26client_id%3D141301989228105%26type%3Dweb_server&return_session=1&canvas=1&app_id=141301989228105&method=permissions.request&perms=read_insights,read_friendlists,publish_stream,email,user_checkins,friends_checkins,manage_pages&from_login=1");
            rd.forward(request, response);
            return;
        }    
        */
        
        if ( (targetURL != null) && ! normalUrlList.contains(targetURL)) {        
	        if (!isSignedOn(hreq)  ) {
	            log.info("user did not logged");
	            ServletContext sc = config.getServletContext();
	            hres.sendRedirect("/jbm/");
	            //RequestDispatcher rd = 
	            //rd.forward(request, response);
	            return;
	        } 
	        if (!isAuthorized(hreq) ) {
	            log.info("Authorized Error");
	            ServletContext sc = config.getServletContext();
	            hres.sendRedirect("/jbm/");
	            //RequestDispatcher rd = 
	            //rd.forward(request, response);
	            return;
	        } 	        
        }
        
    	chain.doFilter(request, response);
    	return;
    }



	private void forwardToLogin(ServletRequest request, ServletResponse response) throws IOException, ServletException {
        ServletContext sc = config.getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(SIGNON_PAGE);
        rd.forward(request, response);
    }

	
	

	
	
    private boolean isSignedOn(HttpServletRequest hreq) {
         //log.info("isSignedOn() " + hreq.getRequestURL());

         Employee account = SessionUtil.getAccount(hreq);
         
        if (null == account) {
            return false;
        } else {
            return true;
        }
    }
    
    public static boolean isAuthorized(HttpServletRequest hreq) {
        String fid="";
        
        fid="fid=" + hreq.getParameter("fid"); 
        
        String currentURL = hreq.getRequestURI() + "?" + fid;
        String context = hreq.getContextPath();


        
        if (null == currentURL ) {
            log.debug("URL does not end with '.do', RUL is '" + currentURL);
            return true;
        }

        Employee employee = SessionUtil.getAccount(hreq);
        if (null == employee) return false;
        

        
        String newURL = null;
        if (currentURL.startsWith(context)) {
            newURL = currentURL.substring(context.length(), currentURL.length());
        }

        if (null != newURL && newURL.startsWith("/")) {
            newURL = newURL.substring(1, newURL.length());
        }
        log.debug("URL=" + newURL);

        Boolean _permission = false;
        List list =  pemissionService.findByURI(newURL);
        if (null == list ) return true; //沒定義的URI 視為不控管
        
        for (int i=0; i<list.size(); i++) {
        	MenuItemPermission _menu = (MenuItemPermission)list.get(i);
        
	        if (null!= _menu) {
		    	if (null!= employee.getRole1c() && null!= _menu.getRoles1c() && employee.getRole1c() && _menu.getRoles1c())  _permission = true;
		    	if (null!= employee.getRole1s() && null!= _menu.getRoles1s() && employee.getRole1s() && _menu.getRoles1s())  _permission = true;
		    	if (null!= employee.getRole2c() && null!= _menu.getRoles2c() && employee.getRole2c() && _menu.getRoles2c())  _permission = true;
		    	if (null!= employee.getRole2s() && null!= _menu.getRoles2s() && employee.getRole2s() && _menu.getRoles2s())  _permission = true;
		    	if (null!= employee.getRole3c() && null!= _menu.getRoles3c() && employee.getRole3c() && _menu.getRoles3c())  _permission = true;
		    	if (null!= employee.getRole3s() && null!= _menu.getRoles3s() && employee.getRole3s() && _menu.getRoles3s())  _permission = true;
		    	if (null!= employee.getRole4c() && null!= _menu.getRoles4c() && employee.getRole4c() && _menu.getRoles4c())  _permission = true;
		    	if (null!= employee.getRole4s() && null!= _menu.getRoles4s() && employee.getRole4s() && _menu.getRoles4s())  _permission = true;
		    	if (null!= employee.getRole5c() && null!= _menu.getRoles5c() && employee.getRole5c() && _menu.getRoles5c())  _permission = true;
		    	if (null!= employee.getRole5s() && null!= _menu.getRoles5s() && employee.getRole5s() && _menu.getRoles5s())  _permission = true;	        
		    	if (null!= employee.getRole6m() && null!= _menu.getRoles6m() && employee.getRole6m() && _menu.getRoles6m())  _permission = true;	
	        } else
	        	_permission = true;//沒定義的URI 視為不控管
	        }
        
    	if (_permission) 
    		return true;
    	else
    		return false;
    	
    }
    


    private String getTargetURL(HttpServletRequest hreq) {
        String currentURL = hreq.getRequestURI();
        
        int firstSlash = currentURL.indexOf("/", 1);
        // jump past the starting slash
        String targetURL = null;
        if (firstSlash != -1) {
            targetURL = currentURL.substring(firstSlash + 1, currentURL.length());
        }
        return targetURL;
    }

    public void destroy() {
        config = null;
    }
}