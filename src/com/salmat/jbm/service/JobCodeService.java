package com.salmat.jbm.service;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xerces.impl.dv.util.Base64;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

import com.salmat.jbm.hibernate.*;
import com.salmat.jbm.service.*;
import com.painter.util.EnCryptUtils;
import com.painter.util.Global;
import com.painter.util.MailUtil;
import com.painter.util.Util;

import java.io.UnsupportedEncodingException;
import java.lang.Integer;
import java.lang.String;
import java.net.URLEncoder;

public class JobCodeService extends JobCodeDAO {

	/**
	 * Log4j instance.
	 */
	private final static Logger log = Logger.getLogger(JobCodeService.class);

	/* Constants */
	private static final JobCodeService instance = new JobCodeService();

	// private static CustomerDAO CustomerDao;

	private JobCodeService() {
		// CustomerDao = new CustomerDAO();

	}
	
	public List<JobCode> findJobCodeByPattern(JobCode jobCode){
		Query query = getSession().createQuery("select j from JobCode j inner join j.customer c where c.custNo = ? and j.jobCodeNo <> ? and j.filename = ?");
		return query.setString(0, jobCode.getCustomer().getCustNo()).setString(1, jobCode.getJobCodeNo()).setString(2, jobCode.getFilename()).list();
		
	} 

	public static JobCodeService getInstance() {
		return instance;
	}

	public void updateMinimalChargeToNull(String cust_no) {
		try {
			String updateStr = "update job_code set minimal_charge_price = NULL where idf_cust_no = ?";
			SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession()
					.createSQLQuery(updateStr);
			sqlQuery.setParameter(0, cust_no);
			sqlQuery.executeUpdate();
		} catch (Exception e) {
			log.error("", e);
		}
	}

	public void updateJobCodeMinimalChargeToNull(String jobCodeNo) {
		try {
			String updateStr = "update job_code set minimal_charge_price = NULL where job_code_no = ?";
			SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession()
					.createSQLQuery(updateStr);
			sqlQuery.setParameter(0, jobCodeNo);
			sqlQuery.executeUpdate();
		} catch (Exception e) {
			log.error("", e);
		}
	}
	
	public List<Object[]> queryJobTransfer(Date queryDate, boolean queryDateIsHoliday, Date lastNotHoliday, HashMap<String, String> category, String custNo) {
		try {			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");			
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(queryDate);
			boolean todayIsMonthEnd = false;
			String todayDate = StringUtils.leftPad(cal.get(Calendar.DATE) + "", 2, '0');
			int monthEndDate = cal.getActualMaximum(Calendar.DATE);
			if(monthEndDate == cal.get(Calendar.DATE))
				todayIsMonthEnd = true;			
			int todayWeekDay = cal.get(Calendar.DAY_OF_WEEK);
			//類別CycleDate是， cycleDate是 queryDate
			category.put("CYCLE_DATE", sdf.format(queryDate));
			
			if(!queryDateIsHoliday){
				//非假日時，類別Working_Day時， cycleDate是 queryDate
				category.put("WORKING_DAY", sdf.format(queryDate));
				//非假日時，類別COMBINE_HOLIDAY時， cycleDate是 上個非假日
				category.put("COMBINE_HOLIDAY", sdf.format(lastNotHoliday));
			}else{
				//假日時，此二類不傳檔， cycleDate是 null
				category.put("WORKING_DAY", null);
				category.put("COMBINE_HOLIDAY", null);
			}
			
			
			//昨天
			cal.add(Calendar.DATE, -1);
			String yesterDate = StringUtils.leftPad(cal.get(Calendar.DATE) + "", 2, '0');
			boolean yesterdayIsMonthEnd = false;
			monthEndDate = cal.getActualMaximum(Calendar.DATE);
			if(monthEndDate == cal.get(Calendar.DATE))
				yesterdayIsMonthEnd = true;						
			int yesterWeekDay = cal.get(Calendar.DAY_OF_WEEK);
			//類別NEXT_DATE時， cycleDate是 queryDate的前一天			
			category.put("NEXT_DATE", sdf.format(cal.getTime()));
			
			//非星期天時類別COMBINE_SUNDAY時， cycleDate是 queryDate的昨天
			category.put("COMBINE_SUNDAY", sdf.format(cal.getTime()));
			
			
			//前天
			cal.add(Calendar.DATE, -1);
			String twoBeforeDate = StringUtils.leftPad(cal.get(Calendar.DATE) + "", 2, '0');
			boolean twoDaysAgoIsMonthEnd = false;
			monthEndDate = cal.getActualMaximum(Calendar.DATE);
			if(monthEndDate == cal.get(Calendar.DATE))
				twoDaysAgoIsMonthEnd = true;
			int twoBeforeWeekDay = cal.get(Calendar.DAY_OF_WEEK);
						
			//combine_sunday的query
	        String combineSunday = " or (c.code_key = 'COMBINE_SUNDAY' and (j.CYCLE" + yesterDate + " = 1 or j.weekDay" + yesterWeekDay + " = 1))";
	        if(yesterdayIsMonthEnd)
	        	combineSunday = " or (c.code_key = 'COMBINE_SUNDAY' and (j.CYCLE" + yesterDate + " = 1 or j.weekDay" + yesterWeekDay + " = 1 or j.cycleEnd = 1))";
	        //如果是星期一，查詢星期天或星期六
	        if(todayWeekDay == 2){
	        	combineSunday = " or (c.code_key = 'COMBINE_SUNDAY' and (j.CYCLE" + yesterDate + " = 1 or j.CYCLE" + twoBeforeDate + " = 1 or j.weekDay" + yesterWeekDay + " = 1 or j.weekDay" + twoBeforeWeekDay + " = 1))"; //combine_sunday的query
	        	if(yesterdayIsMonthEnd || twoDaysAgoIsMonthEnd){
	        		combineSunday = " or (c.code_key = 'COMBINE_SUNDAY' and (j.CYCLE" + yesterDate + " = 1 or j.CYCLE" + twoBeforeDate + " = 1 or j.weekDay" + yesterWeekDay + " = 1 or j.weekDay" + twoBeforeWeekDay + " = 1 or j.cycleEnd = 1))"; //combine_sunday的query
	        	}
	        	//非星期天時類別COMBINE_SUNDAY時， cycleDate是 queryDate的前天
	        	category.put("COMBINE_SUNDAY", sdf.format(cal.getTime()));
	        }else if(todayWeekDay == 1  && queryDateIsHoliday){
	        	combineSunday = "";
	        	//星期天時類別COMBINE_SUNDAY時， cycleDate是null
	        	category.put("COMBINE_SUNDAY", null);
	        }	        
	        
	        
	        String whereStr = " where j.IS_ENABLED_CONTRACT = 1 and ((c.code_key = 'CYCLE_DATE' and (j.CYCLE" + todayDate + " = 1 or j.weekDay" + todayWeekDay + " = 1))" //cycle_date的query
	                          + " or (c.code_key = 'NEXT_DATE' and (j.CYCLE" + yesterDate + " = 1 or j.weekDay" + yesterWeekDay + " = 1))"; //next_date的query
	        if(todayIsMonthEnd){
	        	whereStr = " where j.IS_ENABLED_CONTRACT = 1 and ((c.code_key = 'CYCLE_DATE' and (j.CYCLE" + todayDate + " = 1 or j.weekDay" + todayWeekDay + " = 1 or j.cycleEnd = 1))" //cycle_date的query
                           + " or (c.code_key = 'NEXT_DATE' and (j.CYCLE" + yesterDate + " = 1 or j.weekDay" + yesterWeekDay + " = 1))"; //next_date的query
	        }else if(yesterdayIsMonthEnd){	        	
	        	whereStr = " where j.IS_ENABLED_CONTRACT = 1 and ((c.code_key = 'CYCLE_DATE' and (j.CYCLE" + todayDate + " = 1 or j.weekDay" + todayWeekDay + " = 1))" //cycle_date的query
                           + " or (c.code_key = 'NEXT_DATE' and (j.CYCLE" + yesterDate + " = 1 or j.weekDay" + yesterWeekDay + " = 1 or j.cycleEnd = 1))"; //next_date的query
	        }
	        
			String jobCodeStr = "select j.idf_CUST_NO, j.job_code_no, j.prog_nm, c.code_key, jb.job_bag_no,  jb.cycle_date, jb.create_date, jb.afp_name, jb.JOB_BAG_STATUS from Job_Code j inner join Code c on c.id = j.TRANSFER_TYPE left join job_bag jb on j.JOB_CODE_NO = jb.idf_JOB_CODE_NO "  
			        + whereStr;
			
			String jobCodeStr1 = "select j.idf_CUST_NO, j.job_code_no, j.prog_nm, c.code_key, null as job_bag_no, null as cycle_date, null as create_date, null as afp_name, null as JOB_BAG_STATUS from Job_Code j inner join Code c on c.id = j.TRANSFER_TYPE  "  
		            + whereStr;
			
			if(!queryDateIsHoliday){
				//workingDay的query
				String workDay = " or (c.code_key = 'WORKING_DAY' and (j.CYCLE" + todayDate + " = 1 or j.weekDay" + todayWeekDay + " = 1))"; 
				if(todayIsMonthEnd){
					workDay = " or (c.code_key = 'WORKING_DAY' and (j.CYCLE" + todayDate + " = 1 or j.weekDay" + todayWeekDay + " = 1 or j.cycleEnd = 1))";
				}
				jobCodeStr += workDay;
				jobCodeStr1 += workDay;
				
				//COMBINE_HOLIDAY
				String combineWeekend = "";
				for(Date myQueryDate = lastNotHoliday ; myQueryDate.compareTo(queryDate) < 0 ; ){
					cal.setTime(myQueryDate);
					String date = StringUtils.leftPad(cal.get(Calendar.DATE) + "", 2, '0');
					boolean isMonthEnd = false;
					int monthEnd = cal.getActualMaximum(Calendar.DATE);
					if(monthEnd == cal.get(Calendar.DATE))
						isMonthEnd = true;					
					int weekDay = cal.get(Calendar.DAY_OF_WEEK);					
					
					if(isMonthEnd){
						combineWeekend += " or (c.code_key = 'COMBINE_HOLIDAY' and (j.CYCLE" + date + " = 1 or j.weekDay" + weekDay + " = 1 or j.cycleEnd = 1))";
					}else{
						combineWeekend += " or (c.code_key = 'COMBINE_HOLIDAY' and (j.CYCLE" + date + " = 1 or j.weekDay" + weekDay + " = 1))";
					}
					//加一天
					cal.add(Calendar.DATE, 1);
					myQueryDate = cal.getTime();
				}
				jobCodeStr += combineWeekend;
				jobCodeStr1 += combineWeekend;
				
			}
			jobCodeStr += (combineSunday + ")");
			jobCodeStr1 += (combineSunday + ")");
			if(custNo != null && !custNo.equals("all")){
				jobCodeStr += " and j.idf_CUST_NO = ? ";
				jobCodeStr1 += " and j.idf_CUST_NO = ? ";
			}
			jobCodeStr = jobCodeStr1 + " union " + jobCodeStr + 
			        " and jb.create_date between '" + sdf.format(queryDate) + " 00:00:00.000' and '" +  sdf.format(queryDate) + 
			        " 23:59:59.998' and (jb.IS_DAMAGE = 0  or jb.IS_DAMAGE is null) and (jb.IS_DELETED = 0 or jb.IS_DELETED is null) " +
					" order by j.idf_CUST_NO, j.job_code_no, job_bag_no";
			SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession()
			                    .createSQLQuery(jobCodeStr);
			if(custNo != null && !custNo.equals("all")){
				sqlQuery.setString(0, custNo);
				sqlQuery.setString(1, custNo);
			}
	        
 	        List<Object[]> retList = sqlQuery.list();
 	        
 	        return retList;			
			
		} catch (Exception e) {
			log.error("", e);
			e.printStackTrace();
			return null;
		}
	}

}
