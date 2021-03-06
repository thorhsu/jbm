/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.salmat.jbm.struts.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.painter.util.SessionUtil;
import com.salmat.jbm.hibernate.Employee;
import com.salmat.jbm.hibernate.HibernateSessionFactory;
import com.salmat.jbm.hibernate.Holiday;
import com.salmat.jbm.service.HolidayService;
import com.salmat.jbm.struts.form.*;

/**
 * MyEclipse Struts Creation date: 02-23-2007
 * 
 * XDoclet definition:
 * 
 * @struts.action parameter="fid" validate="true"
 * @struts.action-forward name="init" path=".findUserAccount"
 */
public class HolidayAction extends DispatchAction {
	private static Logger log = Logger.getLogger(HolidayAction.class);
	private HolidayService holidayInstanace = HolidayService.getInstance();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	/**
	 * Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	protected ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		return mapping.findForward("homepage");
	}

	private Calendar setYears(HolidayForm myForm) {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		ArrayList<String> years = new ArrayList<String>();
		for (int i = year - 1; i < year + 2; i++) {
			years.add(i + "");
		}
		myForm.setYears(years);
		return cal;

	}

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HolidayForm myForm = (HolidayForm) form;
		try {
			Calendar cal = setYears(myForm);
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			myForm.setYear(year + "");
			myForm.setMonth(month + "");

			request.setAttribute("holidayForm", myForm);

		} catch (Exception e) {
			log.error("", e);
			request.setAttribute("message", "系統失敗 ");
			return mapping.findForward("message");
		}
		return mapping.findForward("init");
	}

	public ActionForward submit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HolidayForm myForm = (HolidayForm) form;
		String [] holidayCheck = myForm.getHolidayCheck();
		
		String year = myForm.getYear();
		String month = myForm.getMonth();
		Calendar begCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();
		begCal.set(new Integer(year), new Integer(month), 1, 0, 0, 0);
		begCal.set(Calendar.MILLISECOND, 0);
		endCal.set(new Integer(year), new Integer(month),
				begCal.getActualMaximum(Calendar.DATE), 23, 59, 59);
		// 先找出這個月中所有的holiday物件
		List<Holiday> holidayList = holidayInstanace.queryByDatePeriod(
				begCal.getTime(), endCal.getTime());
		Employee emp = SessionUtil.getAccount(request);
		String empNo = emp.getEmpNo();
		Date today = new Date();
		Session session = null;
		Transaction tx = null;
		try{
		   session = HibernateSessionFactory.getSession();
		   tx = session.beginTransaction();
		   for(Holiday holiday : holidayList){	
			  holiday.setModifedPerson(empNo);
			  holiday.setIsHoliday(false);
			  holiday.setModifiedDate(today);
		      for(String holidayStr : holidayCheck){
		         Date date = sdf.parse(holidayStr);
		         if(date.getTime() == holiday.getDate().getTime()){
		        	 holiday.setIsHoliday(true);
		         }		      
		      }
		      session.update(holiday);		      
		   }
		   tx.commit();
		}catch(Exception e){
			log.error("", e);
			request.setAttribute("message", "系統失敗 ");
			if (tx != null)
				tx.rollback();
			return mapping.findForward("message");
		}finally{
			if (session != null && session.isOpen())
				session.close();
		}
		
		return query(mapping, form, request, response);
	}
	
	//回傳當月的 Holiday物件
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HolidayForm myForm = (HolidayForm) form;
		Date today = new Date();
		Session session = null;
		Transaction tx = null;
		try {

			setYears(myForm);
			String year = myForm.getYear();
			String month = myForm.getMonth();
			Calendar begCal = Calendar.getInstance();
			Calendar endCal = Calendar.getInstance();
			begCal.set(new Integer(year), new Integer(month), 1, 0, 0, 0);
			begCal.set(Calendar.MILLISECOND, 0);
			endCal.set(new Integer(year), new Integer(month),
					begCal.getActualMaximum(Calendar.DATE), 23, 59, 59);
			// 先找出這個月 內有設定好假期的時間
			List<Holiday> holidayList = holidayInstanace.queryByDatePeriod(
					begCal.getTime(), endCal.getTime());

			ArrayList<Holiday> retList = new ArrayList<Holiday>();
			session = HibernateSessionFactory.getSession();
			session.clear();
			tx = session.beginTransaction();
			while (begCal.compareTo(endCal) < 0) {
				Holiday newDate = new Holiday();
				newDate.setDate(begCal.getTime());
				Boolean isHoliday = newDate.commonHolidayJudge();
				newDate.setIsHoliday(isHoliday);
				newDate.setModifedPerson("SYSTEM");
				newDate.setModifiedDate(today);

				if (holidayList != null) {
					// 如果資料庫已經有設定，就不再insert
					for (Holiday holiday : holidayList) {
						if (holiday.getDate().getTime() == begCal
								.getTimeInMillis()) {
							retList.add(holiday);
							newDate = null;
						}
					}
				}
				// 如果資料庫無設定，就insert
				if (newDate != null) {
					retList.add(newDate);
					session.save(newDate);
				}
				// 加一天，繼續執行
				begCal.add(Calendar.DATE, 1);
			}
			if (tx != null)
				tx.commit();
           
			Holiday firstDay = retList.get(0);									
			begCal.setTime(firstDay.getDate());
			
			//找出一個月的第一天是星期幾
			int dayOfWeek = begCal.get(Calendar.DAY_OF_WEEK);
			int plusDays = dayOfWeek - 1;
			int line = (plusDays + retList.size()) / 7;
			if((plusDays + retList.size()) % 7 > 0){
				line ++;
			}
			Holiday [][] holidayTable = new Holiday[line][7];
			int counter = 0;
			for(int i = 0 ; i < line ; i++){
				for(int j = 0 ; j < 7 ; j++){
					if(i == 0 && j < plusDays){
						holidayTable[i][j] = null;
					}else{
						holidayTable[i][j] = retList.get(counter++);
						if(counter >= retList.size()){
							break;
						}
					}					
				}				
			} 			
			myForm.setHolidayTable(holidayTable);
			myForm.setTableShow(true);
			request.setAttribute("holidayForm", myForm);

		} catch (Exception e) {
			log.error("", e);
			request.setAttribute("message", "系統失敗 ");
			if (tx != null)
				tx.rollback();
			return mapping.findForward("message");
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}

		return mapping.findForward("edit");
	}

}