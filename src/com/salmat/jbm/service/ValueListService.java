package com.salmat.jbm.service;

import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListHandler;
import net.mlw.vlh.ValueListInfo;
import javax.servlet.http.HttpServletRequest;
import net.mlw.vlh.web.ValueListRequestUtil;
import java.util.Map;

public class ValueListService extends ValueListHandlerFactory {

    private static final String ValueListHandlerImpl = "valueListHandler";

    private static final String ASC = "asc";

    private static final String DESC = "desc";

    private static ValueListService instance = new ValueListService();

    public ValueListService() {
    }

    /* Public Methods */
        /**
         * Singleton pattern get single stance.
         *
         * @return ReportQueueService
         */
        public static ValueListService getInstance() {
            return instance;
        }

    /**
     * getValueList
     *
     * @return ValueList
     */
    public ValueList getValueList(HttpServletRequest request, String entryKey,Map param) {
        ValueListHandler valueListHandler = getValueListHandler(request.
                getSession().getServletContext(), ValueListHandlerImpl);
        ValueListInfo valueListInfo = ValueListRequestUtil.buildValueListInfo(request);
        String pageSize = request.getParameter("pageSize");
        Integer numberPerPage=30;
        if (null == pageSize) 
        	numberPerPage=30;
        else
        	numberPerPage = Integer.parseInt(pageSize);

        valueListInfo.setPagingNumberPer(numberPerPage);
        Map filters = valueListInfo.getFilters();
        filters.putAll(param);


        ValueList valueList = valueListHandler.getValueList(entryKey, valueListInfo);
        return valueList;
    }


}

