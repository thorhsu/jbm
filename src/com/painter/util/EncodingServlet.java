package com.painter.util;
 
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

 
public class EncodingServlet extends HttpServlet implements Filter
{
    private FilterConfig filterConfig;
 
    //Handle the passed-in FilterConfig
    public void init(FilterConfig filterConfig) throws ServletException
    {
        this.filterConfig = filterConfig;
    }
 
    //Process the request/response pair
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
    {
        try
        {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            if (httpServletRequest.getMethod().equals("POST")) {
                    //request.setCharacterEncoding("UTF-8");
                    //request.setCharacterEncoding(request.getParameter("encoding"));
            } else if (httpServletRequest.getMethod().equals("GET")) {
            }
            filterChain.doFilter(request, response);
        }
        catch(ServletException sx)
        {
            filterConfig.getServletContext().log(sx.getMessage());
        }
        catch(IOException iox)
        {
            filterConfig.getServletContext().log(iox.getMessage());
        }
    }
 
    //Clean up resources
    public void destroy()
    {
    }
}

