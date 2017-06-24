package com.painter.page;

/**
 * 分頁代碼
 * <p>Title: 分頁</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: BCS</p>
 * @author Alex
 * @version 1.0
 */
public class Pager {
  private int offset;
  private int totalCount;
  private int pagesize;
  private String url;
  private String pageHeader;
  public Pager(int offset, int totalCount, int pagesize, String url, String pageHeader) {
    this.offset = offset;
    this.totalCount = totalCount;
    this.pagesize = pagesize;
    if (url.indexOf("offset")>0) {
    	int ii = url.indexOf("offset");
    	int jj = url.indexOf("&", ii);
    	if (jj<=0) {
    		url = url.substring(0, ii-1);
    		
    	}
    	else {
    		url = url.substring(0, ii-1) + url.substring(jj, url.length());
    		
    	}
    	
    }
    this.url = url;
    this.pageHeader = pageHeader;
  }

  /**
   * 返回分頁導航條
   * @param offset int 起始記錄的位置
   * @param totalCount int 總記錄數
   * @param pagesize int 步長
   * @param url String .do的url
   * @param pageHeader String 導航條的首碼文字提示
   * @return String
   */
  
  
  
  /**
   * 返回分頁導航條
   * @param offset int 起始記錄的位置
   * @param totalCount int 總記錄數
   * @param pagesize int 步長
   * @param url String .do的url
   * @param pageHeader String 導航條的首碼文字提示
   * @return String
   */
  public String getPageNavigationToFirstpage() {
	    String pageNavigation = ""; //最終返回的分頁導航條
	    //記錄數超過一頁,需要分頁
	    if (totalCount > pagesize) {
	      String pref; //首碼
	      if (url.indexOf("?") > -1) {
	        //如果url中已經包含了其他的參數,就把offset參數接在後面
	        pref = "&";
	      }
	      else {
	        //如果url中沒有別的參數
	        pref = "?";
	      }
	      //如果導航條包含header
	      if (pageHeader != null && pageHeader.length() > 0) {
	        pageNavigation = pageHeader + " : ";
	      }
	      //如果不是第一頁,導航條將包含“<<”(第一頁)和“<”(前一頁)
	      if (offset > 0) {
	    	  return url + pref + "offset=0";
	      }
	      
	    }
	   return "#";
  }
  

  /**
   * 返回分頁導航條
   * @param offset int 起始記錄的位置
   * @param totalCount int 總記錄數
   * @param pagesize int 步長
   * @param url String .do的url
   * @param pageHeader String 導航條的首碼文字提示
   * @return String
   */
  public String getPageNavigationToPrevpage() {
    String pageNavigation = ""; //最終返回的分頁導航條
    //記錄數超過一頁,需要分頁
    if (totalCount > pagesize) {
      String pref; //首碼
      if (url.indexOf("?") > -1) {
        //如果url中已經包含了其他的參數,就把offset參數接在後面
        pref = "&";
      }
      else {
        //如果url中沒有別的參數
        pref = "?";
      }
      //如果導航條包含header
      if (pageHeader != null && pageHeader.length() > 0) {
        pageNavigation = pageHeader + " : ";
      }
      //如果不是第一頁,導航條將包含“<<”(第一頁)和“<”(前一頁)
      if (offset > 0) {
    	  return url + pref + "offset=" + (offset - pagesize);
      }
    }
     return "#";

  }  
  
  /**
   * 返回分頁導航條
   * @param offset int 起始記錄的位置
   * @param totalCount int 總記錄數
   * @param pagesize int 步長
   * @param url String .do的url
   * @param pageHeader String 導航條的首碼文字提示
   * @return String
   */
  public String getPageNavigationToLastpage() {
    String pageNavigation = ""; //最終返回的分頁導航條
    //記錄數超過一頁,需要分頁
    if (totalCount > pagesize) {
      String pref; //首碼
      if (url.indexOf("?") > -1) {
        //如果url中已經包含了其他的參數,就把offset參數接在後面
        pref = "&";
      }
      else {
        //如果url中沒有別的參數
        pref = "?";
      }

      //如果不是最後一頁,導航條將包含“>”(下一頁)和“>>”(最後一頁)
      if (offset < totalCount - pagesize) {
    	  return url + pref + "offset=" + lastPageOffset();
      }
    }
    return "#";

  }
  
  
  /**
   * 返回分頁導航條
   * @param offset int 起始記錄的位置
   * @param totalCount int 總記錄數
   * @param pagesize int 步長
   * @param url String .do的url
   * @param pageHeader String 導航條的首碼文字提示
   * @return String
   */
  public String getPageNavigationToNextpage() {
    String pageNavigation = ""; //最終返回的分頁導航條
    //記錄數超過一頁,需要分頁
    if (totalCount > pagesize) {
      String pref; //首碼
      if (url.indexOf("?") > -1) {
        //如果url中已經包含了其他的參數,就把offset參數接在後面
        pref = "&";
      }
      else {
        //如果url中沒有別的參數
        pref = "?";
      }

      //如果不是最後一頁,導航條將包含“>”(下一頁)和“>>”(最後一頁)
      if (offset < totalCount - pagesize) {
    	  return url + pref + "offset=" +(offset + pagesize);
      }  
    }
    return "#";
  }
  
  
  /**
   * 返回分頁導航條
   * @param offset int 起始記錄的位置
   * @param totalCount int 總記錄數
   * @param pagesize int 步長
   * @param url String .do的url
   * @param pageHeader String 導航條的首碼文字提示
   * @return String
   */
  public String getPageNavigationNumberList() {
    String pageNavigation = ""; //最終返回的分頁導航條
    //記錄數超過一頁,需要分頁
    if (totalCount > pagesize) {
      String pref; //首碼
      if (url.indexOf("?") > -1) {
        //如果url中已經包含了其他的參數,就把offset參數接在後面
        pref = "&";
      }
      else {
        //如果url中沒有別的參數
        pref = "?";
      }

      //導航條中,排頭的那一頁的offset值
      int startOffset;
      //位於導航條中間的那一頁的offset值(半徑)
      int radius = Constants.MAX_PAGE_INDEX / 2 * pagesize;
      //如果當前的offset值小於半徑
      if (offset < radius || this.pageCount() <= Constants.MAX_PAGE_INDEX) {
        //那麼第一頁排頭
        startOffset = 0;
      }
      else if (offset < totalCount - radius) {
        startOffset = offset - radius;
      }
      else {
        startOffset = (totalCount / pagesize - Constants.MAX_PAGE_INDEX) * pagesize;
      }
      for (int i = startOffset;
           i < totalCount && i < startOffset + Constants.MAX_PAGE_INDEX * pagesize;
           i += pagesize) {
        if (i == offset) {
          //當前頁號,加粗顯示
          pageNavigation += "<li class=\"PageButtonAT\">" + (i / pagesize + 1) + "</li>\n";
        }
        else {
          //其他頁號,包含超連結
          pageNavigation += "<li><a href='" + url + pref + "offset=" + i + "'>" +
              (i / pagesize + 1) + "</a></li>\n";
        }
      }
      
      return pageNavigation;
    }
    //記錄不超過一頁,不需要分頁
    else {
      return "";
    }
  }
  
  /**
   * 返回分頁導航條
   * @param offset int 起始記錄的位置
   * @param totalCount int 總記錄數
   * @param pagesize int 步長
   * @param url String .do的url
   * @param pageHeader String 導航條的首碼文字提示
   * @return String
   */
  public String getPageNavigation() {
    String pageNavigation = ""; //最終返回的分頁導航條
    //記錄數超過一頁,需要分頁
    if (totalCount > pagesize) {
      String pref; //首碼
      if (url.indexOf("?") > -1) {
        //如果url中已經包含了其他的參數,就把offset參數接在後面
        pref = "&";
      }
      else {
        //如果url中沒有別的參數
        pref = "?";
      }
      //如果導航條包含header
      if (pageHeader != null && pageHeader.length() > 0) {
        pageNavigation = pageHeader + " : ";
      }
      //如果不是第一頁,導航條將包含“<<”(第一頁)和“<”(前一頁)
      if (offset > 0) {
        pageNavigation += "<a href='" + url + pref + "offset=0'>[<<]</a>\n" +
            "<a href='" + url + pref + "offset=" + (offset - pagesize) +
            "'>[<]</a>\n";
      }
      //導航條中,排頭的那一頁的offset值
      int startOffset;
      //位於導航條中間的那一頁的offset值(半徑)
      int radius = Constants.MAX_PAGE_INDEX / 2 * pagesize;
      //如果當前的offset值小於半徑
      if (offset < radius || this.pageCount() <= Constants.MAX_PAGE_INDEX) {
        //那麼第一頁排頭
        startOffset = 0;
      }
      else if (offset < totalCount - radius) {
        startOffset = offset - radius;
      }
      else {
        startOffset = (totalCount / pagesize - Constants.MAX_PAGE_INDEX) * pagesize;
      }
      for (int i = startOffset;
           i < totalCount && i < startOffset + Constants.MAX_PAGE_INDEX * pagesize;
           i += pagesize) {
        if (i == offset) {
          //當前頁號,加粗顯示
          pageNavigation += "<b>" + (i / pagesize + 1) + "</b>\n";
        }
        else {
          //其他頁號,包含超連結
          pageNavigation += "<a href='" + url + pref + "offset=" + i + "'>" +
              (i / pagesize + 1) + "</a>\n";
        }
      }
      //如果不是最後一頁,導航條將包含“>”(下一頁)和“>>”(最後一頁)
      if (offset < totalCount - pagesize) {
        pageNavigation += "<a href='" + url + pref + "offset=" +
            (offset + pagesize) + "'>[>]</a>\n" +
            "<a href='" + url + pref + "offset=" + lastPageOffset() +
            "'>[>>]</a>\n";
      }
//      System.out.println("radius : " + radius);
//      System.out.println("start offset : " + startOffset);
      return pageNavigation;
    }
    //記錄不超過一頁,不需要分頁
    else {
      return "";
    }
  }

  /**
   * 返回分頁後的總頁數
   * @param totalCount int 總記錄數
   * @param pagesize int 每頁的記錄數
   * @return int
   */
  public int pageCount() {
    int pagecount = 0;
    if (totalCount % pagesize == 0) {
      pagecount = totalCount / pagesize;
    }
    else {
      pagecount = totalCount / pagesize + 1;
    }
    return pagecount;
  }

  /**
   * 返回最後一頁的記錄數
   * @param totalCount int 總記錄數
   * @param pagesize int 每頁的記錄數
   * @return int
   */
  public int lastPageSize() {
    int lastpagesize = 0;
    if (totalCount % pagesize == 0) {
      lastpagesize = pagesize;
    }
    else {
      lastpagesize = totalCount % pagesize;
    }
    return lastpagesize;
  }

  /**
   * 返回最後一頁的起始記錄位置
   * @param totalCount int 總記錄數
   * @param pagesize int 每頁的記錄數
   * @return int
   */
  public int lastPageOffset() {
    return totalCount - lastPageSize();
  }

  public int getOffset() {
    return offset;
  }

  public void setOffset(int offset) {
    this.offset = offset;
  }

  public int getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }

  public int getPagesize() {
    return pagesize;
  }

  public void setPagesize(int pagesize) {
    this.pagesize = pagesize;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getPageHeader() {
    return pageHeader;
  }

  public void setPageHeader(String pageHeader) {
    this.pageHeader = pageHeader;
  }
}
