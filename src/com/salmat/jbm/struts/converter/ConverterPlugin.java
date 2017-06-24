package com.salmat.jbm.struts.converter;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import javax.servlet.ServletException;
import java.util.Date;

public class ConverterPlugin implements PlugIn {

    private String[] datePattern;

    // struts 使用 setter 注入 property 參數
    public void setDatePattern(String[] pattern) {
        this.datePattern = pattern;
    }

    public void init(ActionServlet actionServlet, ModuleConfig moduleConfig)
            throws ServletException {
        // 註冊Date converter
        ConvertUtils.register(new com.salmat.jbm.struts.converter.DateConverter(datePattern), Date.class);
    }


    public void destroy() {
    }
}