<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.salmat.jbm.service.*"%>
<%@ taglib prefix="vlh" uri="http://valuelist.sourceforge.net/tags-valuelist" %>
<%@ taglib uri='/WEB-INF/fmt.tld' prefix='fmt'%>
<%

	SchedulerService service = SchedulerService.getInstance();
	service.execLogistic();
	service.execOneGold();
	service.execCiticard();
	service.execAmex();
	service.execDamage();
	service.execHScard();
	service.zipCodeScheduleJob();
	service.execFbRegister();
	service.zipCodeScheduleJob();
%>