package com.painter.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;

import com.salmat.jbm.hibernate.CodeDAO;
import com.salmat.jbm.hibernate.Code;
import com.salmat.jbm.hibernate.HibernateSessionFactory;



import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Util {

    private static ResourceBundle cResources ;
    private static String NUMBER_BASE_String= "012345678";
    private static String UPPER_STRING_BASE_String= "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static String COUPONCODE_BASE_String= "012345678ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static String activeCodeBaseString="1234567890-=qwertyuiop[]asdfghjkl;zxcvbnm,.QWERTYUIOPASDFGHJKLZXCVBNM";

    private final static Logger log = Logger.getLogger(Util.class);
    private static CodeDAO codeTypeDao ;

    static {
        try {
            cResources = ResourceBundle.getBundle("jbm");
        } catch (MissingResourceException mre) {
            System.err.println("colaz.properties not found");
        }
    }

    /**
     * Returns a string value from a resource key. If the key can't be found,
     * the key is returned as value (so we known which key is missing)
     */
    public static String getFileName() {
        String retStr="";
        Random randomGenerator = new Random();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        retStr =retStr + formatter.format(new Date());
        for (int i=0; i <= 8; i++){
            int randomInt = randomGenerator.nextInt(COUPONCODE_BASE_String.length());
            retStr =retStr + COUPONCODE_BASE_String.charAt(randomInt);
        }

        return retStr;
    }  
    
    
    /**
     * Returns a string value from a resource key. If the key can't be found,
     * the key is returned as value (so we known which key is missing)
     */
    public static String getActiveCode() {
        String retStr="";
        Random randomGenerator = new Random();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        for (int i=0; i <= 16; i++){
            int randomInt = randomGenerator.nextInt(NUMBER_BASE_String.length());
            retStr =retStr + NUMBER_BASE_String.charAt(randomInt);
          }
        retStr =retStr + formatter.format(new Date());
        for (int i=0; i <= 64; i++){
            int randomInt = randomGenerator.nextInt(activeCodeBaseString.length());
            retStr =retStr + activeCodeBaseString.charAt(randomInt);
          }

        return retStr;
    }    
    
    /**
     * Returns a string value from a resource key. If the key can't be found,
     * the key is returned as value (so we known which key is missing)
     */
    public static Integer getPoNo() {
        String retStr="";
        Random randomGenerator = new Random();
        SimpleDateFormat formatter = new SimpleDateFormat("MMddHH");
        retStr = retStr + formatter.format(new Date());
        for (int i=0; i < 3; i++){
            int randomInt = randomGenerator.nextInt(NUMBER_BASE_String.length());
            retStr = retStr + NUMBER_BASE_String.charAt(randomInt);
          }   

        return Integer.parseInt(retStr);
    }
    
    
    

    
    
    public static String getString(String aKey) {
        String str;

        try {
            str = cResources.getString(aKey);
        } catch (MissingResourceException mre) {
            str = aKey;
            System.out.println("resource " + aKey + " not found!");
        }
        return str;

    }
    /**
     * Returns a string value from a resource key. If the key can't be found,
     * the key is returned as value (so we known which key is missing)
     */
    public static String getProperties(String aKey) {
        String str;

        try {
            str = cResources.getString(aKey);
        } catch (MissingResourceException mre) {
            str = aKey;
            System.out.println("resource " + aKey + " not found!");
        }
        return str;
    }

    /**
     * Parameterized message with one argument.
     */
    public static String getString(String aKey, String aParam1) {
        Object[] args = { aParam1 };
        String message = getString(aKey);

        return MessageFormat.format(message, args);
    }

    /**
     * Parameterized message with multiple argument. The second argument is an
     * array of strings. The order of the strings must match the order of
     * parameters in the message.
     */
    public static String getString(String aKey, Object[] aParams) {
        String message = getString(aKey);

        return MessageFormat.format(message, aParams);
    }

    /**
     * Hash the given plaintext with the given digest algorithm, and
     * base64-encode the result.
     * 
     * @param aMD
     *            message digest algorithm to hash with
     * @param aPlaintext
     *            text to hash
     * @return base64-encoded hashed text
     */
    public static String hash(String password) throws Exception {
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(password.getBytes());
    }
    
    public static String dehash(String password) throws Exception {
    	BASE64Decoder base64Decoder = new BASE64Decoder();
    	try {
    		byte[] b = base64Decoder.decodeBuffer(password);
    		return new String(b);
    		} catch (Exception e) { 
    			return null;
        }
    }
    public static void main(String args[]){
    	try {
			String hashPwd = hash("1qazxsw2");
			System.out.println(hashPwd);
			System.out.println(dehash(hashPwd));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

    

    public static String getDecimalFormat(java.math.BigInteger number) {
    	if (null == number) return "0";
    	//DecimalFormat formatter = new DecimalFormat("#,###,###");
    	//String retString = formatter.format(number);         // -1,235
    	return number.toString();
    }
    
    public static String getBooleanFormat(Boolean in) {
    	if (null == in) return "";
    	else if (in) return "true";
    	else return "false";
    	
    }    
    
    public static String getDecimalFormat(Integer number) {
    	if (null == number) return "0";
    	//DecimalFormat formatter = new DecimalFormat("#,###,###");
    	//String retString = formatter.format(number);         // -1,235
    	return number.toString();
    }
    
    public static String getDecimalFormatWithThousandSeparator(Integer number) {
    	if (null == number) return "0";
    	//DecimalFormat formatter = new DecimalFormat("#,###,###");
    	//String retString = formatter.format(number);         // -1,235
    	return number.toString();
    }    
   
    
    public static String getDecimalFormatWithThousandSeparator(Double number) {
    	if (null == number) return "0";
    	//DecimalFormat formatter = new DecimalFormat("#,###,###");
    	//String retString = formatter.format(number);         // -1,235
    	return number.toString();
    }
    
    
    public static String getStringFormat(String string) {
    	if (null == string) return "";
    	else return string;
    }
    
    public static String getDecimalFormat(Double number) {
    	if (null == number) return "0";
    	//DecimalFormat formatter = new DecimalFormat("#,###,###");
    	//String retString = formatter.format(number);         // -1,235
    	return number.toString();
    }
    
    public static String getDecimalFormatToEmpty(Double number) {
    	if (null == number) return "";
    	if (number.compareTo(0D)==0) return "";
    	//DecimalFormat formatter = new DecimalFormat("#,###,###");
    	//String retString = formatter.format(number);         // -1,235
    	return number.toString();
    }    
    
    public static String getDecimalFractionFormat(Double number) {
    	if (null == number) return "0";
    	//DecimalFormat formatter = new DecimalFormat("#,###,###.######");
    	//String retString = formatter.format(number);         // -1,235
    	return number.toString();
    }    
    
    public static String getDecimalFormat(Long number) {
    	if (null == number) return "0";
    	//DecimalFormat formatter = new DecimalFormat("#,###,###");
    	//String retString = formatter.format(number);         // -1,235
    	return number.toString();
    }    
    
    public static String getDateFormat(Date inDate) {
    	if (null == inDate ) return "";
    	String retDate =  new SimpleDateFormat("yyyy-MM-dd").format(inDate);
    	return retDate;

    }    
    
    public static String getDateTimeFormat(Date inDate) {
    	if (null == inDate ) return "";
    	String retDate =  new SimpleDateFormat("yyyy-MM-dd HH:mm").format(inDate);
    	return retDate;
    }    
    
    public static String getCodeTypeValue(String codeType, String codeKey) {
    	if (null == codeKey ) return "";
		try {
			String queryString = "SELECT  {ct.*} " 
    		+ " from code ct where ct.CODE_TYPE_NAME=? and ct.CODE_KEY=? ";
    	
	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
	    	
	        sqlQuery.addEntity("ct", Code.class);
	        sqlQuery.setParameter(0, codeType);
	        sqlQuery.setParameter(1, codeKey);
	        List retList = sqlQuery.list();
	        Code _codeType = (Code)retList.get(0);
            return _codeType.getCodeValueTw();
		    } catch (RuntimeException re) {
		        log.error("getCodeTypeValue", re);
		        return null;
		    }
    }     
    

 
}
