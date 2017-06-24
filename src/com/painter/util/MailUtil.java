package com.painter.util;


import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;





public class MailUtil {

    private final static Logger log = Logger.getLogger(MailUtil.class);
    
    private final static String HOST = Util.getString("deal.smtp.host");
    
    private final static String PORT = Util.getString("deal.mail.port");
    
    private final static String USER_NAME = Util.getString("deal.mail.username");
    
    private final static String FROM_USER_NAME = Util.getString("deal.mail.username");
    
    private final static String PASSWORD = Util.getString("deal.mail.password");
    
    public static boolean sendMail(String subject, String context, String[] addrs) {
        boolean isSend = true;
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());       
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", HOST);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", PORT);
        props.setProperty("mail.smtp.socketFactory.port", PORT);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        //props.setProperty("mail.smtp.starttls.enable","true"); 
        props.setProperty("mail.smtp.auth", "true");
        
        //props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
        //props.setProperty("mail.smtp.socketFactory.fallback", "false"); 
        //props.setProperty("mail.smtp.port", "25"); 
        //props.setProperty("mail.smtp.socketFactory.port", "25"); 
        
        
        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USER_NAME, PASSWORD);
                    }
                });
        if (log.isDebugEnabled()) {
            session.setDebug(true);
            session.setDebugOut(System.out);
        }
        // -- Create a new message --
        MimeMessage msg = new MimeMessage(session);
        // -- Set the FROM and TO fields --
        try {
            msg.setFrom(new InternetAddress(FROM_USER_NAME));
            Address[] addresses = new Address[addrs.length];
            for (int i = 0; i < addrs.length; i++) {
                addresses[i] = new InternetAddress(addrs[i]);
            }
            msg.setRecipients(Message.RecipientType.TO, addresses);
            // msg.setRecipients(Message.RecipientType.CC,
            // InternetAddress.parse("ken.chang@bnslink.com", false));
            msg.setSubject(subject, "UTF-8");

            // 
            Multipart mp = new MimeMultipart();
            MimeBodyPart mbp = new MimeBodyPart();
            mbp.setContent(context, "text/html;charset=UTF-8");
            mp.addBodyPart(mbp);
            msg.setContent(mp);
            msg.setSentDate(new Date());
            Transport.send(msg);
        } catch (AddressException e) {
            isSend = false;
            log.error("Send mail fail.", e);
            e.printStackTrace();
        } catch (MessagingException e) {
            isSend = false;
            log.error("Send mail fail.", e);
            e.printStackTrace();
        }

        log.info("Message sent.");

        return true;
    }

    /*
    public static String getOrderContent_UNCOMPLETED(Order order){
    	String NowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getCreateDate());
        String apserverUrl = Util.getString("deal.apserver.url");
        StringBuffer sb = new StringBuffer(2000);
        sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")
        .append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
        .append("<head>")
        .append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />")
        .append("<title>--- Bazaar 天天購便宜 ---</title>")
        .append("<style type=\"text/css\">")
        .append("<!--")
        .append("body {")
        .append("	margin-left: 0px;")
        .append("	margin-top: 0px;")
        .append("	margin-right: 0px;")
        .append("	margin-bottom: 0px;")
        .append("}")
        .append("a {color: #ff9900;text-decoration: none; }")
        .append("a:hover {")
        .append("color: #ff9900;")
        .append("text-decoration:underline;")
        .append("}")
        .append(".style1 {")
        .append("	color: #4caabd;")
        .append("	font-weight: bold;")
        .append("}")
        .append(".style2 {color: #FFFFFF}")
        .append(".style6 {font-size: 80px}")
        .append(".style7 {")
        .append("	font-size: 12px")
        .append("}")
        .append(".style8 {font-size: 24px}")
        .append(".style9 {color: #FFFFFF; font-size: 24px; }")
        .append(".style10 {color: #FF0000}")
        .append(".style12 {")
        .append("	color: #FF0000;")
        .append("	font-weight: bold;")
        .append("	font-size: 24px;")
        .append("}")
        .append("-->")
        .append("</style></head>")
        .append("<body>")
        .append("<table width=\"620\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("  <tr>")
        .append("    <td bgcolor=\"#FFCC00\"  style=\"padding-top:10px; padding-left:10px; padding-bottom:10px\"><table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("      <tr>")
        .append("        <td width=\"600\" bgcolor=\"#FFFFFF\"><table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("            <tr>")
        .append("              <td style=\"font-size:12px; color:#333; font-wieght:700; font-family:Microsoft JhengHei, Arial,Helvetica,sans-serif; padding-top:8px; padding-bottom:10px; padding-left:10px\" >您如果未能閱讀此電郵，請按此處。<br />")
        .append("                請把 "+ Util.getString("apserver.servicemail") +" 加到你的聯絡人清單或安全寄件人清單，好讓我們的Email不會進入垃圾信箱。</td>")
        .append("            </tr>")
        .append("          </table><table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("  <tr>")
        .append("    <td><a href=\""+apserverUrl+"/findSku.do\"><img src=\"images/epaper_logo_top.jpg\" alt=\"Bazaar天天購便宜\" width=\"219\" height=\"143\" /></a></td>")
        .append("    <td><a href=\""+apserverUrl+"/findSku.do\"><img src=\"images/epaper_menu_btn_1.jpg\" alt=\"今日優惠\" width=\"94\" height=\"143\" /></a></td>")
        .append("    <td><a href=\""+apserverUrl+"/findSku.do?fid=viewSkuList\"><img src=\"images/epaper_menu_btn_2.jpg\" alt=\"更多優惠\" width=\"97\" height=\"143\" /></a></td>")
        .append("    <td><a href=\""+apserverUrl+"/staticPage.do?pageName=9_2_payment\"><img src=\"images/epaper_menu_btn_3.jpg\" alt=\"如何購買\" width=\"96\" height=\"143\" /></a></td>")
        .append("    <td><a href=\""+apserverUrl+"/findBonus.do?fid=viewBonusList\"><img src=\"images/epaper_menu_btn_4.jpg\" alt=\"巴札幣\" width=\"94\" height=\"143\" /></a></td>")
        .append("  </tr>")
        .append("</table>")
        
        
        .append("<table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("            <tr>")
        .append("              <td style=\"padding-left:10px; padding-top:10px\"><p class=\"style7\"><span class=\"style10\">謝謝您，您 " +Util.getDateFormat(order.getCreateDate()) + "訂購的 " +order.getSkuName()+ " 我們已經收到了！<P></span></p>")
        .append("                <p class=\"style7\">系統已向發卡行請款。目前這個優惠商品還在募集中。<br />若最後團購無法成立，您將不會被收取任何費用，而您的信用帳單則會出現一筆退款紀錄。</p>")
        .append("                <p class=\"style7\"> <img src=\"images/gold_point_w.jpg\" alt=\"point\" width=\"12\" height=\"12\" align=\"absmiddle\" /> 如需與 Bazaar.com 聯繫，請提供每筆訂單編號<br />")
        .append("                  <img src=\"images/gold_point_w.jpg\" alt=\"point\" width=\"12\" height=\"12\" align=\"absmiddle\" /> 訂單明細、金額等相關資訊，請於登入後至「我的帳戶 / 巴札優惠券」查詢。</p>")
        .append("                <table width=\"581\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("                  <tr>")
        .append("                    <td width=\"581\" height=\"26\" background=\"images/info_bg2.jpg\" class=\"style7\" style=\"padding-left:10px\">訂單編號 ( <span class=\"style11\">"+order.getId()+"</span> )</td>")
        .append("                  </tr>")
        .append("                </table>")
        .append("                <table width=\"580\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("                  <tr>")
        .append("                    <td class=\"style7\">&nbsp;</td>")
        .append("                  </tr>")
        .append("                  <tr>")
        .append("                    <td style=\"padding-left:10px\"><span class=\"style7\">"+order.getShortDesc()+" </span></td>")
        .append("                  </tr>")
        .append("                  <tr>")
        .append("                    <td>&nbsp;</td>")
        .append("                  </tr>")
        .append("                  <tr>")
        .append("                    <td height=\"30\" style=\"padding-right:10px\"><div align=\"right\" class=\"style7\">"+ order.getQty()+" X NT$"+Util.getDecimalFormat( order.getPrice() )+" = NT$ <span class=\"style11\">"+Util.getDecimalFormat( order.getPrice() * order.getQty() )+"</span></div></td>")
        .append("                  </tr>")
        .append("                  <tr>")
        .append("                    <td height=\"30\" style=\"padding-right:10px\"><div align=\"right\" class=\"style7\">【巴札幣扣抵】NT $ <span class=\"style12\">-"+Util.getDecimalFormat( order.getBonusPoint() )+"</span></div></td>")
        .append("                  </tr>")
        .append("                  <tr>")
        .append("                    <td height=\"20\" background=\"images/line2.jpg\">&nbsp;</td>")
        .append("                  </tr>")
        .append("                  <tr>")
        .append("                    <td style=\"padding-right:10px\"><div align=\"right\"><span class=\"style13\">【總付款金額】NT $</span> <span class=\"style14\">"+Util.getDecimalFormat( order.getSum() )+"</span></div></td>")
        .append("                  </tr>")
        .append("                            <tr>")
        .append("                              <td height=\"20\" background=\"images/line2.jpg\">&nbsp;</td>")
        .append("                            </tr>")
        .append("                            <tr>")
        .append("                              <td class=\"style10\" style=\"padding-bottom:10px; padding-top:10px\">請留意您的Email信箱與手機</td>")
        .append("                            </tr>")
        .append("                            <tr>")
        .append("                              <td class=\"style7\" style=\"line-height:20px\">您可於本檔團購截止("+ Util.getDateFormat(order.getSku().getSellingDateEnd()) +" 晚上12:00)之後，至網頁右上方                                  <a href=\""+apserverUrl+"/payment.do?fid=viewOrderList\"><img src=\"images/btn_myCoupon2.jpg\" alt=\"巴札優惠券\" border=\"0\" width=\"93\" height=\"32\" align=\"absmiddle\" /></a> 查看本檔團購最終是否成")
        .append("                                功，以及詳細的兌換方式。(系統也會在團購截止後24小時內，發送提醒簡訊及 Email。由於Email可能進入垃")
        .append("                                圾郵件夾，請您務必回到網站確定團購是否成功喔~)</td>")
        .append("                            </tr>")
        .append("                            <tr>")
        .append("                              <td class=\"style7\">&nbsp;</td>")
        .append("                            </tr>")
        .append("                            <tr>")
        .append("                              <td class=\"style7\"><a href=\""+apserverUrl+"/findBonus.do?fid=viewBonusList\"><img src=\"images/btn_chkaward.jpg\" alt=\"查看我的巴札幣帳戶\" border=\"0\" width=\"157\" height=\"32\" /></a></td>")
        .append("                            </tr>")
        .append("                            <tr>")
        .append("                              <td class=\"style7\">&nbsp;</td>")
        .append("                            </tr>")
        .append("                          </table>")
        .append("                          <table width=\"580\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("                            <tr>")
        .append("                              <td width=\"581\" height=\"53\" background=\"images/info_bg.jpg\" style=\"font-size:12px; color:#333; font-wieght:700; font-family:Microsoft JhengHei, Arial,Helvetica,sans-serif; padding-top:8px; padding-bottom:10px; line-height:18px\"><div align=\"center\">如有服務上的問題與建議，歡迎來函客戶服務中心，我們將有專人為您服務！<br />")
        .append("                                客服信箱：<img src=\"images/icon_mail.jpg\" width=\"16\" height=\"12\" align=\"absmiddle\" /><a href=\"#\" style=\"color:#000000; text-decoration:underline\">service@bazaar.com</a></div></td>")
        .append("                            </tr>")
        .append("                          </table>")

        .append("                <table width=\"580\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("                  <tr>")
        .append("                    <td style=\"font-size:12px; color:#333; font-wieght:700; font-family:Microsoft JhengHei, Arial,Helvetica,sans-serif; padding-top:8px; padding-bottom:10px\" >收到這郵件是由於我們相信其中的訊息與您相關。<br />")
        .append("                      如欲取消接收所有關於本網站的產品或服務的訊息請按 <a href=\""+apserverUrl+"/applyUserAccount.do?fid=unsubscribed&activeCode=" + order.getAccount().getActiveCode()+  "\">取消接收</a>  </td>")
        .append("                  </tr>")
        .append("                </table></td>")
        .append("            </tr>")
        .append("          </table></td>")
        .append("      </tr>")
        .append("    </table></td>")
        .append("  </tr>")
        .append("</table>")
        .append(" </body>")
        .append("</html>");
        
        String context = sb.toString();
        context = context.replaceAll("images/", apserverUrl+"/images/");
        return context;
    }       
    
    public static String getOrderContent_COMPLETED(Order order){
    	String NowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getCreateDate());
        String apserverUrl = Util.getString("deal.apserver.url");
        StringBuffer sb = new StringBuffer(2000);
        sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")
        .append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
        .append("<head>")
        .append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />")
        .append("<title>--- Bazaar 天天購便宜 ---</title>")
        .append("<style type=\"text/css\">")
        .append("<!--")
        .append("body {")
        .append("	margin-left: 0px;")
        .append("	margin-top: 0px;")
        .append("	margin-right: 0px;")
        .append("	margin-bottom: 0px;")
        .append("}")
        .append("a {color: #ff9900;text-decoration: none; }")
        .append("a:hover {")
        .append("color: #ff9900;")
        .append("text-decoration:underline;")
        .append("}")
        .append(".style1 {")
        .append("	color: #4caabd;")
        .append("	font-weight: bold;")
        .append("}")
        .append(".style2 {color: #FFFFFF}")
        .append(".style6 {font-size: 80px}")
        .append(".style7 {")
        .append("	font-size: 12px")
        .append("}")
        .append(".style8 {font-size: 24px}")
        .append(".style9 {color: #FFFFFF; font-size: 24px; }")
        .append(".style10 {color: #FF0000}")
        .append(".style12 {")
        .append("	color: #FF0000;")
        .append("	font-weight: bold;")
        .append("	font-size: 24px;")
        .append("}")
        .append("-->")
        .append("</style></head>")
        .append("<body>")
        .append("<table width=\"620\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("  <tr>")
        .append("    <td bgcolor=\"#FFCC00\"  style=\"padding-top:10px; padding-left:10px; padding-bottom:10px\"><table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("      <tr>")
        .append("        <td width=\"600\" bgcolor=\"#FFFFFF\"><table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("            <tr>")
        .append("              <td style=\"font-size:12px; color:#333; font-wieght:700; font-family:Microsoft JhengHei, Arial,Helvetica,sans-serif; padding-top:8px; padding-bottom:10px; padding-left:10px\" >您如果未能閱讀此電郵，請按此處。<br />")
        .append("                請把 "+ Util.getString("apserver.servicemail") +" 加到你的聯絡人清單或安全寄件人清單，好讓我們的Email不會進入垃圾信箱。</td>")
        .append("            </tr>")
        .append("          </table><table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("  <tr>")
        .append("    <td><a href=\""+apserverUrl+"/findSku.do\"><img src=\"images/epaper_logo_top.jpg\" alt=\"Bazaar天天購便宜\" width=\"219\" height=\"143\" /></a></td>")
        .append("    <td><a href=\""+apserverUrl+"/findSku.do\"><img src=\"images/epaper_menu_btn_1.jpg\" alt=\"今日優惠\" width=\"94\" height=\"143\" /></a></td>")
        .append("    <td><a href=\""+apserverUrl+"/findSku.do?fid=viewSkuList\"><img src=\"images/epaper_menu_btn_2.jpg\" alt=\"更多優惠\" width=\"97\" height=\"143\" /></a></td>")
        .append("    <td><a href=\""+apserverUrl+"/staticPage.do?pageName=9_2_payment\"><img src=\"images/epaper_menu_btn_3.jpg\" alt=\"如何購買\" width=\"96\" height=\"143\" /></a></td>")
        .append("    <td><a href=\""+apserverUrl+"/findBonus.do?fid=viewBonusList\"><img src=\"images/epaper_menu_btn_4.jpg\" alt=\"巴札幣\" width=\"94\" height=\"143\" /></a></td>")
        .append("  </tr>")
        .append("</table>")
        
        
        .append("<table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("            <tr>")
        .append("              <td style=\"padding-left:10px; padding-top:10px\"><p class=\"style7\"><span class=\"style10\">恭喜！您於 " +Util.getDateFormat(order.getCreateDate()) + "訂購的 " +order.getSkuName()+ " 已經完成付款！</span></p>")
        .append("                <p class=\"style7\">Bazaar將不斷推出更多更優惠的團購好康，不要忘記隨時查看以及與親朋好友分享喔</p>")
        .append("                <p class=\"style7\"> <img src=\"images/gold_point_w.jpg\" alt=\"point\" width=\"12\" height=\"12\" align=\"absmiddle\" /> 如需與 Bazaar.com 聯繫，請提供每筆訂單編號<br />")
        .append("                  <img src=\"images/gold_point_w.jpg\" alt=\"point\" width=\"12\" height=\"12\" align=\"absmiddle\" /> 訂單明細、金額等相關資訊，請於登入後至「我的帳戶 / 巴札優惠券」查詢。</p>")
        .append("                <table width=\"581\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("                  <tr>")
        .append("                    <td width=\"581\" height=\"26\" background=\"images/info_bg2.jpg\" class=\"style7\" style=\"padding-left:10px\">訂單編號 ( <span class=\"style11\">"+order.getId()+"</span> )</td>")
        .append("                  </tr>")
        .append("                </table>")
        .append("                <table width=\"580\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("                  <tr>")
        .append("                    <td class=\"style7\">&nbsp;</td>")
        .append("                  </tr>")
        .append("                  <tr>")
        .append("                    <td style=\"padding-left:10px\"><span class=\"style7\">"+order.getShortDesc()+" </span></td>")
        .append("                  </tr>")
        .append("                  <tr>")
        .append("                    <td>&nbsp;</td>")
        .append("                  </tr>")
        .append("                  <tr>")
        .append("                    <td height=\"30\" style=\"padding-right:10px\"><div align=\"right\" class=\"style7\">"+ order.getQty()+" X NT$"+Util.getDecimalFormat( order.getPrice() )+" = NT$ <span class=\"style11\">"+Util.getDecimalFormat( order.getPrice() * order.getQty() )+"</span></div></td>")
        .append("                  </tr>")
        .append("                  <tr>")
        .append("                    <td height=\"30\" style=\"padding-right:10px\"><div align=\"right\" class=\"style7\">【巴札幣扣抵】NT $ <span class=\"style12\">-"+Util.getDecimalFormat( order.getBonusPoint() )+"</span></div></td>")
        .append("                  </tr>")
        .append("                  <tr>")
        .append("                    <td height=\"20\" background=\"images/line2.jpg\">&nbsp;</td>")
        .append("                  </tr>")
        .append("                  <tr>")
        .append("                    <td style=\"padding-right:10px\"><div align=\"right\"><span class=\"style13\">【總付款金額】NT $</span> <span class=\"style14\">"+Util.getDecimalFormat( order.getSum() )+"</span></div></td>")
        .append("                  </tr>")
        .append("                  <tr>")
        .append("                    <td height=\"20\" background=\"images/line2.jpg\">&nbsp;</td>")
        .append("                  </tr>")
        .append("                  <tr>")
        .append("                    <td><div align=\"center\">")
        .append("                      <p class=\"style7\">&nbsp;</p>")
        .append("                      <p class=\"style7\"><a href=\""+apserverUrl+"/findBonus.do?fid=viewBonusList\"><img src=\"images/btn_chkaward.jpg\" alt=\"查看我的巴札幣帳戶\" border=\"0\" width=\"157\" height=\"32\" /></a>")
        .append("                       <a href=\""+apserverUrl+"/payment.do?fid=viewOrderList\"><img src=\"images/btn_myCoupon2.jpg\" alt=\"巴札優惠券\" border=\"0\" width=\"93\" height=\"32\"  /></a></p>")
        .append("                    </div></td>")
        .append("                  </tr>")
        .append("                </table>")
        
        .append("                <table width=\"580\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("                  <tr>")
        .append("                    <td style=\"font-size:12px; color:#333; font-wieght:700; font-family:Microsoft JhengHei, Arial,Helvetica,sans-serif; padding-top:8px; padding-bottom:10px\" >收到這郵件是由於我們相信其中的訊息與您相關。<br />")
        .append("                      如欲取消接收所有關於本網站的產品或服務的訊息請按 <a href=\""+apserverUrl+"/applyUserAccount.do?fid=unsubscribed&activeCode=" + order.getAccount().getActiveCode()+  "\">取消接收</a> </td>")
        .append("                  </tr>")
        .append("                </table></td>")
        .append("            </tr>")
        .append("          </table></td>")
        .append("      </tr>")
        .append("    </table></td>")
        .append("  </tr>")
        .append("</table>")
        .append(" </body>")
        .append("</html>");
        
        String context = sb.toString();
        context = context.replaceAll("images/", apserverUrl+"/images/");
        return context;
    }   
    
    public static String getOrderContent_ATM(Order order){
    	String NowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getCreateDate());
        String apserverUrl = Util.getString("deal.apserver.url");
    	Calendar order_day = Calendar.getInstance();
    	order_day.setTime(order.getCreateDate());
    	order_day.add(Calendar.DAY_OF_YEAR,2);
    	String paymentDeadline =  new SimpleDateFormat("yyyy-MM-dd").format(order_day.getTime());
    	
        StringBuffer sb = new StringBuffer(2000);
        sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")
        .append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
        .append("<head>")
        .append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />")
        .append("<title>--- Bazaar 天天購便宜 ---</title>")
        .append("<style type=\"text/css\">")
        .append("<!--")
        .append("body {")
        .append("	margin-left: 0px;")
        .append("	margin-top: 0px;")
        .append("	margin-right: 0px;")
        .append("	margin-bottom: 0px;")
        .append("}")
        .append("a {color: #ff9900;text-decoration: none; }")
        .append("a:hover {")
        .append("color: #ff9900;")
        .append("text-decoration:underline;")
        .append("}")
        .append(".style1 {")
        .append("	color: #4caabd;")
        .append("	font-weight: bold;")
        .append("}")
        .append(".style2 {color: #FFFFFF}")
        .append(".style6 {font-size: 80px}")
        .append(".style7 {")
        .append("	font-size: 12px")
        .append("}")
        .append(".style8 {font-size: 24px}")
        .append(".style9 {color: #FFFFFF; font-size: 24px; }")
        .append(".style10 {color: #FF0000}")
        .append(".style12 {")
        .append("	color: #FF0000;")
        .append("	font-weight: bold;")
        .append("	font-size: 24px;")
        .append("}")
        .append("-->")
        .append("</style></head>")
        .append("<body>")
        .append("<table width=\"620\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("  <tr>")
        .append("    <td bgcolor=\"#FFCC00\"  style=\"padding-top:10px; padding-left:10px; padding-bottom:10px\"><table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("      <tr>")
        .append("        <td width=\"600\" bgcolor=\"#FFFFFF\"><table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("            <tr>")
        .append("              <td style=\"font-size:12px; color:#333; font-wieght:700; font-family:Microsoft JhengHei, Arial,Helvetica,sans-serif; padding-top:8px; padding-bottom:10px; padding-left:10px\" >您如果未能閱讀此電郵，請按此處。<br />")
        .append("                請把 "+ Util.getString("apserver.servicemail") +" 加到你的聯絡人清單或安全寄件人清單，好讓我們的Email不會進入垃圾信箱。</td>")
        .append("            </tr>")
        .append("          </table><table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("  <tr>")
        .append("    <td><a href=\""+apserverUrl+"/findSku.do\"><img src=\"images/epaper_logo_top.jpg\" alt=\"Bazaar天天購便宜\" width=\"219\" height=\"143\" /></a></td>")
        .append("    <td><a href=\""+apserverUrl+"/findSku.do\"><img src=\"images/epaper_menu_btn_1.jpg\" alt=\"今日優惠\" width=\"94\" height=\"143\" /></a></td>")
        .append("    <td><a href=\""+apserverUrl+"/findSku.do?fid=viewSkuList\"><img src=\"images/epaper_menu_btn_2.jpg\" alt=\"更多優惠\" width=\"97\" height=\"143\" /></a></td>")
        .append("    <td><a href=\""+apserverUrl+"/staticPage.do?pageName=9_2_payment\"><img src=\"images/epaper_menu_btn_3.jpg\" alt=\"如何購買\" width=\"96\" height=\"143\" /></a></td>")
        .append("    <td><a href=\""+apserverUrl+"/findBonus.do?fid=viewBonusList\"><img src=\"images/epaper_menu_btn_4.jpg\" alt=\"巴札幣\" width=\"94\" height=\"143\" /></a></td>")
        .append("  </tr>")
        .append("</table>")
        
        
        .append("<table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("            <tr>")
        .append("              <td style=\"padding-left:10px; padding-top:10px\"><p class=\"style7\"><span class=\"style10\">恭喜！您於 " +Util.getDateFormat(order.getCreateDate()) + "訂購的 " +order.getSkuName()+ "我們已經收到了！您的訂單編號為("+order.getId()+")，請於"+paymentDeadline+" 晚上12:00 前使用ATM或網路ATM轉帳，才算成功訂購喔！<br />")
        .append("                <p class=\"style7\">Bazaar將不斷推出更多更優惠的團購好康，不要忘記隨時查看以及與親朋好友分享喔</p>")
        .append("                <p class=\"style7\"> <img src=\"images/gold_point_w.jpg\" alt=\"point\" width=\"12\" height=\"12\" align=\"absmiddle\" /> 如需與 Bazaar.com 聯繫，請提供每筆訂單編號<br />")
        .append("                  <img src=\"images/gold_point_w.jpg\" alt=\"point\" width=\"12\" height=\"12\" align=\"absmiddle\" /> 訂單明細、金額等相關資訊，請於登入後至「我的帳戶 / 巴札優惠券」查詢。</p>")
        .append("                <table width=\"581\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("                  <tr>")
        .append("                    <td width=\"581\" height=\"26\" background=\"images/info_bg2.jpg\" class=\"style7\" style=\"padding-left:10px\">訂單編號 ( <span class=\"style11\">"+order.getId()+"</span> )</td>")
        .append("                  </tr>")
        .append("                </table>")
        .append("                <table width=\"580\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("                  <tr>")
        .append("                    <td class=\"style7\">&nbsp;</td>")
        .append("                  </tr>")
        .append("                  <tr>")
        .append("                    <td style=\"padding-left:10px\"><span class=\"style7\">"+order.getShortDesc()+" </span></td>")
        .append("                  </tr>")
        .append("                  <tr>")
        .append("                    <td>&nbsp;</td>")
        .append("                  </tr>")
        .append("                  <tr>")
        .append("                    <td height=\"30\" style=\"padding-right:10px\"><div align=\"right\" class=\"style7\">"+ order.getQty()+" X NT$"+Util.getDecimalFormat( order.getPrice() )+" = NT$ <span class=\"style11\">"+Util.getDecimalFormat( order.getPrice() * order.getQty() )+"</span></div></td>")
        .append("                  </tr>")
        .append("                  <tr>")
        .append("                    <td height=\"30\" style=\"padding-right:10px\"><div align=\"right\" class=\"style7\">【巴札幣扣抵】NT $ <span class=\"style12\">-"+Util.getDecimalFormat( order.getBonusPoint() )+"</span></div></td>")
        .append("                  </tr>")
        .append("                  <tr>")
        .append("                    <td height=\"20\" background=\"images/line2.jpg\">&nbsp;</td>")
        .append("                  </tr>")
        .append("                  <tr>")
        .append("                    <td style=\"padding-right:10px\"><div align=\"right\"><span class=\"style13\">【總付款金額】NT $</span> <span class=\"style14\">"+Util.getDecimalFormat( order.getSum() )+"</span></div></td>")
        .append("                  </tr>")
        .append("                  <tr>")
        .append("                    <td height=\"20\" background=\"images/line2.jpg\">&nbsp;</td>")
        .append("                  </tr>")
        .append("                  <tr>")
        .append("                    <td><div align=\"center\">")
        .append("                      <p class=\"style7\">&nbsp;</p>")
        .append("                      <p class=\"style7\"><a href=\""+apserverUrl+"/findBonus.do?fid=viewBonusList\"><img src=\"images/btn_chkaward.jpg\" alt=\"查看我的巴札幣帳戶\" border=\"0\" width=\"157\" height=\"32\" /></a>")
        .append("                       <a href=\""+apserverUrl+"/payment.do?fid=viewOrderList\"><img src=\"images/btn_myCoupon2.jpg\" alt=\"巴札優惠券\" border=\"0\" width=\"93\" height=\"32\"  /></a></p>")
        .append("                    </div></td>")
        .append("                  </tr>")
        .append("                </table>")
        
        .append("                <table width=\"580\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("                  <tr>")
        .append("                    <td style=\"font-size:12px; color:#333; font-wieght:700; font-family:Microsoft JhengHei, Arial,Helvetica,sans-serif; padding-top:8px; padding-bottom:10px\" >收到這郵件是由於我們相信其中的訊息與您相關。<br />")
        .append("                      如欲取消接收所有關於本網站的產品或服務的訊息請按 <a href=\""+apserverUrl+"/applyUserAccount.do?fid=unsubscribed&activeCode=" + order.getAccount().getActiveCode()+  "\">取消接收</a> </td>")
        .append("                  </tr>")
        .append("                </table></td>")
        .append("            </tr>")
        .append("          </table></td>")
        .append("      </tr>")
        .append("    </table></td>")
        .append("  </tr>")
        .append("</table>")
        .append(" </body>")
        .append("</html>");
        
        String context = sb.toString();
        context = context.replaceAll("images/", apserverUrl+"/images/");
        return context;
    }       
    
    public static String getRefundContent(Refund refund){
    	String NowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(refund.getCreateDate());
        String apserverUrl = Util.getString("deal.apserver.url");
        StringBuffer sb = new StringBuffer(2000);
        sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")
        .append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
        .append("<head>")
        .append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />")
        .append("<title>--- Bazaar 天天購便宜 ---</title>")
        .append("<style type=\"text/css\">")
        .append("<!--")
        .append("body {")
        .append("	margin-left: 0px;")
        .append("	margin-top: 0px;")
        .append("	margin-right: 0px;")
        .append("	margin-bottom: 0px;")
        .append("}")
        .append("a {color: #ff9900;text-decoration: none; }")
        .append("a:hover {")
        .append("color: #ff9900;")
        .append("text-decoration:underline;")
        .append("}")
        .append(".style1 {")
        .append("	color: #4caabd;")
        .append("	font-weight: bold;")
        .append("}")
        .append(".style2 {color: #FFFFFF}")
        .append(".style6 {font-size: 80px}")
        .append(".style7 {")
        .append("	font-size: 12px")
        .append("}")
        .append(".style8 {font-size: 24px}")
        .append(".style9 {color: #FFFFFF; font-size: 24px; }")
        .append(".style10 {color: #FF0000}")
        .append(".style12 {")
        .append("	color: #FF0000;")
        .append("	font-weight: bold;")
        .append("	font-size: 24px;")
        .append("}")
        .append("-->")
        .append("</style></head>")
        .append("<body>")
        .append("<table width=\"620\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("  <tr>")
        .append("    <td bgcolor=\"#FFCC00\"  style=\"padding-top:10px; padding-left:10px; padding-bottom:10px\"><table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("      <tr>")
        .append("        <td width=\"600\" bgcolor=\"#FFFFFF\"><table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("            <tr>")
        .append("              <td style=\"font-size:12px; color:#333; font-wieght:700; font-family:Microsoft JhengHei, Arial,Helvetica,sans-serif; padding-top:8px; padding-bottom:10px; padding-left:10px\" >您如果未能閱讀此電郵，請按此處。<br />")
        .append("                請把 "+ Util.getString("apserver.servicemail") +" 加到你的聯絡人清單或安全寄件人清單，好讓我們的Email不會進入垃圾信箱。</td>")
        .append("            </tr>")
        .append("          </table><table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("  <tr>")
        .append("    <td><a href=\""+apserverUrl+"/findSku.do\"><img src=\"images/epaper_logo_top.jpg\" alt=\"Bazaar天天購便宜\" width=\"219\" height=\"143\" /></a></td>")
        .append("    <td><a href=\""+apserverUrl+"/findSku.do\"><img src=\"images/epaper_menu_btn_1.jpg\" alt=\"今日優惠\" width=\"94\" height=\"143\" /></a></td>")
        .append("    <td><a href=\""+apserverUrl+"/findSku.do?fid=viewSkuList\"><img src=\"images/epaper_menu_btn_2.jpg\" alt=\"更多優惠\" width=\"97\" height=\"143\" /></a></td>")
        .append("    <td><a href=\""+apserverUrl+"/staticPage.do?pageName=9_2_payment\"><img src=\"images/epaper_menu_btn_3.jpg\" alt=\"如何購買\" width=\"96\" height=\"143\" /></a></td>")
        .append("    <td><a href=\""+apserverUrl+"/findBonus.do?fid=viewBonusList\"><img src=\"images/epaper_menu_btn_4.jpg\" alt=\"巴札幣\" width=\"94\" height=\"143\" /></a></td>")
        .append("  </tr>")
        .append("</table>")
        .append("          <table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("            <tr>")
        .append("              <td style=\"padding-left:10px; padding-top:10px\"><p class=\"style7\">&nbsp;</p>")
        .append("                <p class=\"style7\">親愛的 "+refund.getAccountByIdfAccount().getNickname()+" 您好，</p>")
        .append("                <p class=\"style7\">您於 "+NowTime+" 向Bazaar網站申請的退貨申請。</p>")
        .append("                <p><span class=\"style7\">訂單編號: </span><span class=\"style10\">"+ refund.getOrder().getId() +"</span></p>")        
        .append("                <p><span class=\"style7\">退款方式: </span><span class=\"style10\">"+Util.getCodeTypeValue("REFUND_TYPE",  refund.getRefundType() )+"</span></p>")        
//        .append("                <p><span class=\"style7\">帳戶名稱: </span><span class=\"style10\">"+withdrawBonus.getBankAccountName()+"</span></p>") 
//        .append("                <p><span class=\"style7\">銀行代碼: </span><span class=\"style10\">"+withdrawBonus.getBankId()+"</span></p>")
//        .append("                <p><span class=\"style7\">轉帳帳號: </span><span class=\"style10\">"+withdrawBonus.getBankAccountId()+"</span></p>")
//        .append("                <p><span class=\"style7\">提領金額: </span><span class=\"style10\">"+withdrawBonus.getBonusPoint()+"</span></p>")        
        .append("                <p><span class=\"style7\">處理結果如下: </span><span class=\"style10\">"+Util.getCodeTypeValue("REFUND_VERIFICATION_STATUS",  refund.getVerificationStatus() )+"</span><BR>"+refund.getRejectNote()+"</p>")
//        .append("                <p><span class=\"style7\">手續費: </span><span class=\"style10\">"+refund.getCommissionCharge()+"</span></p>")
//        .append("                <p><span class=\"style7\">實際轉帳金額: </span><span class=\"style10\">"+Util.getDecimalFormat( refund.getSum() )+"</span></p>")        
        .append("                <p><br />")
        .append("                </p>")
        .append("                <table width=\"580\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("                  <tr>")
        .append("                    <td width=\"581\" height=\"53\" background=\"images/info_bg.jpg\" style=\"font-size:12px; color:#333; font-wieght:700; font-family:Microsoft JhengHei, Arial,Helvetica,sans-serif; padding-top:8px; padding-bottom:10px; line-height:18px\"><div align=\"center\">如有服務上的問題與建議，歡迎來函客戶服務中心，我們將有專人為您服務！<br />")
        .append("                      客服信箱：<img src=\"images/icon_mail.jpg\" width=\"16\" height=\"12\" align=\"absmiddle\" /><a href=\"#\" style=\"color:#000000; text-decoration:underline\">service@bazaar.com</a></div></td>")
        .append("                  </tr>")
        .append("                </table>")
        .append("                <table width=\"580\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("                  <tr>")
        .append("                    <td style=\"font-size:12px; color:#333; font-wieght:700; font-family:Microsoft JhengHei, Arial,Helvetica,sans-serif; padding-top:8px; padding-bottom:10px\" >收到這郵件是由於我們相信其中的訊息與您相關。<br />")
        .append("                      如欲取消接收所有關於本網站的產品或服務的訊息請按 <a href=\""+apserverUrl+"/applyUserAccount.do?fid=unsubscribed&activeCode=" + refund.getAccountByIdfAccount().getActiveCode()+  "\">取消接收</a>  </td>")
        .append("                  </tr>")
        .append("                </table></td>")
        .append("            </tr>")
        .append("          </table></td>")
        .append("      </tr>")
        .append("    </table></td>")
        .append("  </tr>")
        .append("</table>")
        .append(" </body>")
        .append("</html>");
        
        String context = sb.toString();
        context = context.replaceAll("images/", apserverUrl+"/images/");
        return context;
    }        
    
    public static String getWithdrawBonusContent(WithdrawBonus withdrawBonus){
    	String NowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(withdrawBonus.getCreateDate());
        String apserverUrl = Util.getString("deal.apserver.url");
        StringBuffer sb = new StringBuffer(2000);
        Integer netBonus = withdrawBonus.getBonusPoint() - withdrawBonus.getCommissionCharge();
        String content ="";
		if (withdrawBonus.getStatus().equalsIgnoreCase("APPROVED"))
			content= " 處理結果如下: 系統已核准，並且成功轉帳" + netBonus + "元(扣除"+ withdrawBonus.getCommissionCharge() +" 元手續費)"	;
		else
			content= " 處理結果如下: 該筆巴札幣兌現申請失敗！"	;
        
        sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")
        .append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
        .append("<head>")
        .append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />")
        .append("<title>--- Bazaar 天天購便宜 ---</title>")
        .append("<style type=\"text/css\">")
        .append("<!--")
        .append("body {")
        .append("	margin-left: 0px;")
        .append("	margin-top: 0px;")
        .append("	margin-right: 0px;")
        .append("	margin-bottom: 0px;")
        .append("}")
        .append("a {color: #ff9900;text-decoration: none; }")
        .append("a:hover {")
        .append("color: #ff9900;")
        .append("text-decoration:underline;")
        .append("}")
        .append(".style1 {")
        .append("	color: #4caabd;")
        .append("	font-weight: bold;")
        .append("}")
        .append(".style2 {color: #FFFFFF}")
        .append(".style6 {font-size: 80px}")
        .append(".style7 {")
        .append("	font-size: 12px")
        .append("}")
        .append(".style8 {font-size: 24px}")
        .append(".style9 {color: #FFFFFF; font-size: 24px; }")
        .append(".style10 {color: #FF0000}")
        .append(".style12 {")
        .append("	color: #FF0000;")
        .append("	font-weight: bold;")
        .append("	font-size: 24px;")
        .append("}")
        .append("-->")
        .append("</style></head>")
        .append("<body>")
        .append("<table width=\"620\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("  <tr>")
        .append("    <td bgcolor=\"#FFCC00\"  style=\"padding-top:10px; padding-left:10px; padding-bottom:10px\"><table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("      <tr>")
        .append("        <td width=\"600\" bgcolor=\"#FFFFFF\"><table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("            <tr>")
        .append("              <td style=\"font-size:12px; color:#333; font-wieght:700; font-family:Microsoft JhengHei, Arial,Helvetica,sans-serif; padding-top:8px; padding-bottom:10px; padding-left:10px\" >您如果未能閱讀此電郵，請按此處。<br />")
        .append("                請把 "+ Util.getString("apserver.servicemail") +" 加到你的聯絡人清單或安全寄件人清單，好讓我們的Email不會進入垃圾信箱。</td>")
        .append("            </tr>")
        .append("          </table><table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("  <tr>")
        .append("    <td><a href=\""+apserverUrl+"/findSku.do\"><img src=\"images/epaper_logo_top.jpg\" alt=\"Bazaar天天購便宜\" width=\"219\" height=\"143\" /></a></td>")
        .append("    <td><a href=\""+apserverUrl+"/findSku.do\"><img src=\"images/epaper_menu_btn_1.jpg\" alt=\"今日優惠\" width=\"94\" height=\"143\" /></a></td>")
        .append("    <td><a href=\""+apserverUrl+"/findSku.do?fid=viewSkuList\"><img src=\"images/epaper_menu_btn_2.jpg\" alt=\"更多優惠\" width=\"97\" height=\"143\" /></a></td>")
        .append("    <td><a href=\""+apserverUrl+"/staticPage.do?pageName=9_2_payment\"><img src=\"images/epaper_menu_btn_3.jpg\" alt=\"如何購買\" width=\"96\" height=\"143\" /></a></td>")
        .append("    <td><a href=\""+apserverUrl+"/findBonus.do?fid=viewBonusList\"><img src=\"images/epaper_menu_btn_4.jpg\" alt=\"巴札幣\" width=\"94\" height=\"143\" /></a></td>")
        .append("  </tr>")
        .append("</table>")
        .append("          <table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("            <tr>")
        .append("              <td style=\"padding-left:10px; padding-top:10px\"><p class=\"style7\">&nbsp;</p>")
        .append("                <p class=\"style7\">親愛的 "+withdrawBonus.getAccountByIdfAccount().getNickname()+" 您好，</p>")
        .append("                <p class=\"style7\">您於 "+NowTime+" 向Bazaar網站申請的提領巴札幣 "  + withdrawBonus.getBonusPoint() + "</p>")
//        .append("                <p><span class=\"style7\">帳戶名稱: </span><span class=\"style10\">"+withdrawBonus.getBankAccountName()+"</span></p>") 
//        .append("                <p><span class=\"style7\">銀行代碼: </span><span class=\"style10\">"+withdrawBonus.getBankId()+"</span></p>")
//        .append("                <p><span class=\"style7\">轉帳帳號: </span><span class=\"style10\">"+withdrawBonus.getBankAccountId()+"</span></p>")
//        .append("                <p><span class=\"style7\">提領金額: </span><span class=\"style10\">"+withdrawBonus.getBonusPoint()+"</span></p>")        
//        .append("                <p><span class=\"style7\">處理結果如下: </span><span class=\"style10\">"+Util.getCodeTypeValue("REFUND_VERIFICATION_STATUS",  withdrawBonus.getStatus() )+"</span></p>")
//        .append("                <p><span class=\"style7\">手續費: </span><span class=\"style10\">"+withdrawBonus.getCommissionCharge()+"</span></p>")
        .append("                <p><span class=\"style7\">"+content+"</span></p>")        
        .append("                <p><br />")
        .append("                </p>")
        .append("                <table width=\"580\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("                  <tr>")
        .append("                    <td width=\"581\" height=\"53\" background=\"images/info_bg.jpg\" style=\"font-size:12px; color:#333; font-wieght:700; font-family:Microsoft JhengHei, Arial,Helvetica,sans-serif; padding-top:8px; padding-bottom:10px; line-height:18px\"><div align=\"center\">如有服務上的問題與建議，歡迎來函客戶服務中心，我們將有專人為您服務！<br />")
        .append("                      客服信箱：<img src=\"images/icon_mail.jpg\" width=\"16\" height=\"12\" align=\"absmiddle\" /><a href=\"#\" style=\"color:#000000; text-decoration:underline\">service@bazaar.com</a></div></td>")
        .append("                  </tr>")
        .append("                </table>")
        .append("                <table width=\"580\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("                  <tr>")
        .append("                    <td style=\"font-size:12px; color:#333; font-wieght:700; font-family:Microsoft JhengHei, Arial,Helvetica,sans-serif; padding-top:8px; padding-bottom:10px\" >收到這郵件是由於我們相信其中的訊息與您相關。<br />")
        .append("                      如欲取消接收所有關於本網站的產品或服務的訊息請按 <a href=\""+apserverUrl+"/applyUserAccount.do?fid=unsubscribed&activeCode=" + withdrawBonus.getAccountByIdfAccount().getActiveCode()+  "\">取消接收</a>  </td>")
        .append("                  </tr>")
        .append("                </table></td>")
        .append("            </tr>")
        .append("          </table></td>")
        .append("      </tr>")
        .append("    </table></td>")
        .append("  </tr>")
        .append("</table>")
        .append(" </body>")
        .append("</html>");
        
        String context = sb.toString();
        context = context.replaceAll("images/", apserverUrl+"/images/");
        return context;
    }    
    */
    public static String getForgotPasswordContent(String name, String newPassword){
    	String NowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String apserverUrl = Util.getString("deal.apserver.url");
        StringBuffer sb = new StringBuffer(2000);
        sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")
        .append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
        .append("<head>")
        .append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />")
        .append("<title>--- colaz 酷樂網 ---</title>")
        .append("<style type=\"text/css\">")
        .append("<!--")
        .append("body {")
        .append("	margin-left: 0px;")
        .append("	margin-top: 0px;")
        .append("	margin-right: 0px;")
        .append("	margin-bottom: 0px;")
        .append("}")
        .append("a {color: #ff9900;text-decoration: none; }")
        .append("a:hover {")
        .append("color: #ff9900;")
        .append("text-decoration:underline;")
        .append("}")
        .append(".style1 {")
        .append("	color: #4caabd;")
        .append("	font-weight: bold;")
        .append("}")
        .append(".style2 {color: #FFFFFF}")
        .append(".style6 {font-size: 80px}")
        .append(".style7 {")
        .append("	font-size: 12px")
        .append("}")
        .append(".style8 {font-size: 24px}")
        .append(".style9 {color: #FFFFFF; font-size: 24px; }")
        .append(".style10 {color: #FF0000}")
        .append(".style12 {")
        .append("	color: #FF0000;")
        .append("	font-weight: bold;")
        .append("	font-size: 24px;")
        .append("}")
        .append("-->")
        .append("</style></head>")
        .append("<body>")
        .append("<table width=\"620\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("  <tr>")
        .append("    <td bgcolor=\"#FFCC00\"  style=\"padding-top:10px; padding-left:10px; padding-bottom:10px\"><table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("      <tr>")
        .append("        <td width=\"600\" bgcolor=\"#FFFFFF\">")
        .append("          <table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("            <tr>")
        .append("              <td style=\"padding-left:10px; padding-top:10px\"><p class=\"style7\">&nbsp;</p>")
        .append("                <p class=\"style7\">親愛的 "+name+" 您好，</p>")
        .append("                <p class=\"style7\">您於 "+NowTime+" 向colaz 酷樂網查詢密碼，以下是系統為您產生的新密碼，請妥善保存。</p>")
        .append("                <p><span class=\"style7\">您的新密碼為 </span><span class=\"style10\">"+newPassword+"</span></p>")
        .append("                <p><br />")
        .append("                </p>")
        .append("                <table width=\"580\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("                  <tr>")
        .append("                    <td width=\"581\" height=\"53\" background=\"images/info_bg.jpg\" style=\"font-size:12px; color:#333; font-wieght:700; font-family:Microsoft JhengHei, Arial,Helvetica,sans-serif; padding-top:8px; padding-bottom:10px; line-height:18px\"><div align=\"center\">如有服務上的問題與建議，歡迎來函客戶服務中心，我們將有專人為您服務！<br />")
        .append("                      客服信箱：<img src=\"images/icon_mail.jpg\" width=\"16\" height=\"12\" align=\"absmiddle\" /><a href=\"#\" style=\"color:#000000; text-decoration:underline\">service@colaz.com.tw</a></div></td>")
        .append("                  </tr>")
        .append("                </table>")
        .append("                <table width=\"580\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">")
        .append("                  <tr>")
        .append("                    <td style=\"font-size:12px; color:#333; font-wieght:700; font-family:Microsoft JhengHei, Arial,Helvetica,sans-serif; padding-top:8px; padding-bottom:10px\" ><br />")
        .append("                  </tr>")
        .append("                </table></td>")
        .append("            </tr>")
        .append("          </table></td>")
        .append("      </tr>")
        .append("    </table></td>")
        .append("  </tr>")
        .append("</table>")
        .append(" </body>")
        .append("</html>");
        
        String context = sb.toString();
        context = context.replaceAll("images/", apserverUrl+"/images/");
        return context;
    }    


}
