package com.salmat.jbm.hibernate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.painter.util.ChineseCalendar;

/**
 * Holiday entity. @author MyEclipse Persistence Tools
 */

public class Holiday implements java.io.Serializable {
	// Fields
	private Date date;
	private Boolean isHoliday;
	private Date modifiedDate;
	private String modifedPerson;
	
	private static HashMap<String, String> nationalHolidays = new HashMap<String, String>();
	private static HashMap<String, String> lunarNationalHolidays = new HashMap<String, String>();
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
	private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
	private Calendar cal = Calendar.getInstance();
	private Date today = new Date();
	
	//一般假日判斷
	public Boolean commonHolidayJudge(){
		if(date != null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			//是不是國曆假期
			if(nationalHolidays.containsKey(sdf.format(date))){
				return true;
			}else{
				   ChineseCalendar c = new ChineseCalendar();
				   c.setGregorian(date);
				   c.computeChineseFields();
				   
				   Date nextDay = new Date(date.getTime() + 24 * 60 * 60 * 1000);
				   ChineseCalendar nextc = new ChineseCalendar();
				   nextc.setGregorian(nextDay);
				   nextc.computeChineseFields();
				   
				   int month = c.getChineseMonth();
				   int date = c.getChineseDate();
				   
				   int nextMonth = nextc.getChineseMonth();
				   int nextDate = nextc.getChineseDate();
				   //是不是農曆假期
				   if(lunarNationalHolidays.containsKey(month + "/" + date)){
					    return  true;
				   }else if(nextMonth == 1 && nextDate == 1){
					   //是不是除夕
					   return  true;
				   }else if(dayOfWeek == 7 || dayOfWeek == 1){
					   //是不是六日
					   return true;
				   }else{
					   return false;
				   }
			}
		}else{
			return null;
		}
	}
	
	public String getHolidayName(){
		
		//假日才進入判斷
		if(isHoliday != null && isHoliday){
		   if(date != null){
			 //國曆假期
			   if(nationalHolidays.containsKey(sdf.format(date))){
				    return  nationalHolidays.get(sdf.format(date));
			   }else{
				   
				   ChineseCalendar c = new ChineseCalendar();
				   c.setGregorian(date);
				   c.computeChineseFields();
				   
				   Date nextDay = new Date(date.getTime() + 24 * 60 * 60 * 1000);
				   ChineseCalendar nextc = new ChineseCalendar();
				   nextc.setGregorian(nextDay);
				   nextc.computeChineseFields();
				   
				   int month = c.getChineseMonth();
				   int date = c.getChineseDate();
				   
				   int nextMonth = nextc.getChineseMonth();
				   int nextDate = nextc.getChineseDate();
				   //農曆假期
				   if(lunarNationalHolidays.containsKey(month + "/" + date)){
					    return  lunarNationalHolidays.get(month + "/" + date);
				   }else if(nextMonth == 1 && nextDate == 1){
					   return  lunarNationalHolidays.get("1/0");
				   }else{
					   //一般假日
					   return "假日";
				   }				   
			   }
		   }else{
			   return null;
		   }
		}else{
			return "非假日";
		}
	}

	public boolean getIsToday(){
		if(date == null)
			date = new Date();

		if(this.date != null && sdf1.format(today).equals(sdf1.format(this.date)))
			return true;
		else
			return false;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Boolean getIsHoliday() {
		return isHoliday;
	}
	public void setIsHoliday(Boolean isHoliday) {
		this.isHoliday = isHoliday;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getModifedPerson() {
		return modifedPerson;
	}
	public void setModifedPerson(String modifedPerson) {
		this.modifedPerson = modifedPerson;
	}

	public static HashMap<String, String> getNationalHolidays() {
		return nationalHolidays;
	}
	public void setNationalHolidays(HashMap<String, String> nationalHolidays) {
		Holiday.nationalHolidays = nationalHolidays;
	}
	public static HashMap<String, String> getLunarNationalHolidays() {
		return lunarNationalHolidays;
	}
	public void setLunarNationalHolidays(
			HashMap<String, String> lunarNationalHolidays) {
		Holiday.lunarNationalHolidays = lunarNationalHolidays;
	}
	
	public int getDaysfMonth(){
         cal.setTime(date);
         return cal.get(Calendar.DATE);				
	}
	
	public String getDateStr(){
		return sdf1.format(date);
	}
		
}