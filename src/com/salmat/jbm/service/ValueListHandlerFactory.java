package com.salmat.jbm.service;

import javax.servlet.ServletContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import net.mlw.vlh.ValueListHandler;
import net.mlw.vlh.ValueList;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public abstract class ValueListHandlerFactory {
    public ValueListHandlerFactory() {
    }
    protected ValueListHandler getValueListHandler(ServletContext servletContext, String beanID) {
            WebApplicationContext context = WebApplicationContextUtils.
                    getWebApplicationContext(servletContext);
            return (ValueListHandler) context.getBean(beanID,
                    ValueListHandler.class);
        }

    public abstract ValueList getValueList(HttpServletRequest request, String entryKey,Map param);

}
