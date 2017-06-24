package com.salmat.jbm.struts.converter;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter implements Converter {

    private final String[] pattern;
    private final static Logger log = Logger.getLogger(DateConverter.class);
    private final static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public DateConverter(String[] pattern) {
        this.pattern = pattern;
    }

    public Object convert(Class clazz, Object o) {        
        Date date = null;
        if (o != null && o instanceof String && ((String) o).trim().length() > 0) {
        	String dateInput = (String)o;
            
            
            try {
            	if(o != null && !"".equals(dateInput.trim()));
                  date = format.parse(dateInput);
            } catch (ParseException e) {
               	log.error("", e);
               	log.error("Date convert error " + dateInput);
                throw new ConversionException("Parameter invalid: \"" + o + "\"");
            }            
            
        }else if (o instanceof Date) {
        	log.info("input date is " + date);
            return (Date) o;
        }else if(o != null){
        	return o;
        }
        return date;

    }
}
