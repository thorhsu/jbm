package com.painter.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.salmat.jbm.hibernate.HibernateSessionFactory;

public class HibernateSessionCloser implements Filter{

    protected FilterConfig filterConfig = null;

    public void init(FilterConfig filterConfig)throws ServletException{
        this.filterConfig = filterConfig;
    }
    
    public void destroy(){
        this.filterConfig = null;
    }    

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
        throws IOException, ServletException {
        try{
            chain.doFilter(request, response);
        }
        finally{
            try{
            	if(HibernateSessionFactory.getSession().isOpen() && !HibernateSessionFactory.getSession().getTransaction().wasCommitted())
                   HibernateSessionFactory.commitTransaction();
            }catch (Exception e){
            	if(HibernateSessionFactory.getSession().isOpen() && !HibernateSessionFactory.getSession().getTransaction().wasRolledBack())
                   HibernateSessionFactory.rollbackTransaction();
            }finally{
            	if(HibernateSessionFactory.getSession().isOpen())
                   HibernateSessionFactory.closeSession();
            }   
        }

    }
}