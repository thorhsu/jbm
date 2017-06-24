package com.painter.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;








import org.hibernate.Query;
import org.hibernate.Session;
import org.jfree.util.Log;
import org.mozilla.javascript.edu.emory.mathcs.backport.java.util.Arrays;
import org.quartz.Scheduler;
import org.quartz.impl.StdScheduler;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.salmat.jbm.hibernate.HibernateSessionFactory;
import com.salmat.jbm.hibernate.SchedulerTime;
import com.salmat.jbm.struts.action.ReportAction;

public class SetParamValFilter implements Filter {

    private static ServletContext servletContext;
    private static String contextPath;
    private static WebApplicationContext ctx;
	// 產生可供jQuery填值的javascript
	public static String getRequestParamsVal(HttpServletRequest req) {		
		//取得需回填值的parameter
		String includeParamStr = req.getParameter("includeParams_value");
		java.util.List<String> includeParams = null;
        if(includeParamStr != null)
        	includeParams = Arrays.asList(includeParamStr.split(","));

		java.util.Enumeration<String> namesEnu = req.getParameterNames();
		String jQueryStr = "$(\r\n		  function() {\r\n";
		while (null!= includeParams && namesEnu.hasMoreElements() ) {
			// 抓出所有的parameter name
			String param = namesEnu.nextElement();
			// 取出所有parameter value
			String[] paramVals = req.getParameterValues(param);

			if ( null!= includeParams && includeParams.contains(param) && !param.equals("includeParams_value")) {
				// 先轉成陣列再塞值
				// var arrayVal = ["INIT", "COMPLETED_LP"]
				// $("[name='jobBagStatuses']").val(arrayVal);
				if (paramVals != null && paramVals.length >= 1) {
					String arrayVal = "";
					for (String paramVal : paramVals) {
						arrayVal += "\"" + paramVal + "\",";
					}
					if (arrayVal.endsWith(",")) {
						arrayVal = arrayVal.substring(0, arrayVal.length() - 1);
					}
					jQueryStr += "var " + param + "Val = [" + arrayVal
							+ "];\r\n";
					jQueryStr += "$(\"[name='" + param + "']\").val(" + param
							+ "Val);\r\n";
				} 
			}
		}
		jQueryStr += "		  }\r\n   );\r\n";

		return jQueryStr;
	}

	public void destroy() {
		// TODO Auto-generated method stub

	}
    public static WebApplicationContext getApplicationContext(){
       if(ctx == null)
	     ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
	   return ctx;
    }
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		if(contextPath == null)
			contextPath = ((HttpServletRequest)request).getContextPath();
		if(ctx == null){
			ctx = getApplicationContext();
			schedulerMailInit();
		}
		request.setCharacterEncoding("UTF-8");
		String jqueryStr = getRequestParamsVal((HttpServletRequest) request);
		request.setAttribute("setParamJqueryStr", jqueryStr);
		if(request.getAttribute("backToListURL") == null && request.getParameter("backToListURL") != null && !"".equals(request.getParameter("backToListURL"))){
		    //System.out.println(request.getParameter("backToListURL"));
			request.setAttribute("backToListURL", URLEncoder.encode(request.getParameter("backToListURL"), "UTF-8"));
		}
		//以下部分是將parameter傳入的值放到attribute中，方便進行作業 
		//在查詢頁面查詢出來的所有的id list
		if(request.getAttribute("idList") == null && request.getParameter("idList") != null && !"".equals(request.getParameter("idList"))){
			request.setAttribute("idList", request.getParameter("idList"));
		}
		//下一筆id
		if(request.getAttribute("nextId") == null && request.getParameter("nextId") != null && !"".equals(request.getParameter("nextId"))){
			request.setAttribute("nextId", request.getParameter("nextId"));
		}
		//上一筆id
		if(request.getAttribute("prevId") == null && request.getParameter("prevId") != null && !"".equals(request.getParameter("prevId"))){
			request.setAttribute("prevId", request.getParameter("prevId"));
		}
		// 目前在第幾筆，例：8/30,9/30
		if(request.getAttribute("pagesIndex") == null && request.getParameter("pagesIndex") != null && !"".equals(request.getParameter("pagesIndex"))){
			request.setAttribute("pagesIndex", request.getParameter("pagesIndex"));
		}
		
		
		filterChain.doFilter(request, response);

	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		SetParamValFilter.servletContext = filterConfig.getServletContext();		
	}
	
	public void schedulerMailInit(){
		Session session = null;
		try{
		   session = HibernateSessionFactory.getSession();
		   //CT mail
		   List<SchedulerTime> sts = session.createQuery("from SchedulerTime").list();
		   
		   
		   ctx = getApplicationContext();		   
       	   StdScheduler sf = (StdScheduler) ctx.getBean("scheduleTasks");
       	   for(SchedulerTime st : sts){
       		  if(st.getCronTrigger() != null){
       	         CronTriggerBean  triggerBean = (CronTriggerBean) ctx.getBean(st.getCronTrigger());
       	         if(triggerBean != null){
       	        	//0 35 * * * ?
       	        	String cronExpression = "0 " + st.getSchedulerMin() + " " 
          	        	   + st.getSchedulerHour() + " " + st.getSchedulerDate() + " " 
       	        		   + st.getSchedulerMonth() + " " + st.getSchedulerWeekDay() 
       	        		   + " " + st.getSchedulerYear(); 
       	            triggerBean.setCronExpression(cronExpression);
       	            triggerBean.afterPropertiesSet();        	
       	            sf.rescheduleJob(st.getCronTrigger(), Scheduler.DEFAULT_GROUP, triggerBean);
       	         }
       		  }
       	   }       	          	   
    	           	        	

		}catch(Exception e){
		   Log.error("",  e);
		}finally{
			if(session != null)
				session.close();
		}
	}
	
	public String getContextPath(){
		if(contextPath == null){
			contextPath = servletContext.getContextPath();
		}
		return contextPath;
	}
	
	
	public static ServletContext getServletContext() {
		return servletContext;
	}
    
	//  /pdf/檔名  前面不用加application path
	public static InputStream getInputStream(String path) {		
		if (servletContext != null)
		  return servletContext.getResourceAsStream(path);
		else 
		  return null;
	}
	
	// 取得絕對目錄 
	public static String getRealPath(String path){
		if (servletContext != null)
		  return servletContext.getRealPath(path);
		else 
		  return null;
	}
	
	public static URL getURL(String path) throws MalformedURLException{
		if (servletContext != null)
		  return servletContext.getResource(path);
		else 
	   	  return null;
	}


}
