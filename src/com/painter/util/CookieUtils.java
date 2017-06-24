package com.painter.util;


import java.util.Hashtable;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {
  private static final String LANGUAGE_KEY = "Language";
  private static Hashtable supportLanguage;
  private static String defaultLanguage = "zh_TW";

  /**
   * Look for a cookie from a Http request object.
   * May returns null if the cookie is not found.
   *
   * @param request HttpServletRequest
   * @param aCookieName String
   * @return Cookie
   */
  public static Cookie getCookie(HttpServletRequest request, String aCookieName) {
    Cookie[] cookies = request.getCookies(); // may get null if no cookies
    Cookie target = null;

    for (int i = 0; cookies != null && i < cookies.length; ++i) {
      if (cookies[i].getName().equals(aCookieName)) {
        target = cookies[i];
        //break;
      }
    }
    return target;
  }

  /**
   * Get a cookie value from a Http request object.
   * May return the default value if the cookie is not found.
   *
   * @param request HttpServletRequest
   * @param aCookieName String
   * @param aDefault String
   * @return String
   */
  public static String getCookieValue(HttpServletRequest request,
                                      String aCookieName,
                                      String aDefault) {
    Cookie cookie = getCookie(request, aCookieName);

    return (cookie == null) ? aDefault : cookie.getValue();
  }

  /**
   * set specified language string to HTTP response cookie
   *
   * @param response HttpServletResponse
   * @param language String
   */
  public static void setLanguage(HttpServletResponse response,
                                 String language) {
    String value = LANGUAGE_KEY + "=" + language + "; "
        + "path=/; "
        + "expires=Wednesday, 15-Dec-49 8:00:00 GMT";
    response.setHeader("Set-Cookie", value);
  }

  /**
   * get default langugae setting
   *
   * @param language String
   * @return String
   */
  public static String getCharset(String language) {
    return (String) supportLanguage.get(language);
  }

  /**
   * get HTTP request langugae setting
   *
   * @param request HttpServletRequest
   * @return String
   */
  public static String getLanguage(HttpServletRequest request) {
    Cookie cookie = getCookie(request, LANGUAGE_KEY);
    if (cookie != null) {
      return cookie.getValue();
    }
    else {
      return defaultLanguage;
    }
  }

}

