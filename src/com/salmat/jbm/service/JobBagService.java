package com.salmat.jbm.service;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;



import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.salmat.jbm.bean.AmexCSVNameBean;
import com.salmat.jbm.hibernate.*;

import java.lang.Integer;
import java.lang.String;
import java.lang.reflect.InvocationTargetException;


public class JobBagService extends JobBagDAO {

	/**
	 * Log4j instance.
	 */
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private final static Logger log = Logger.getLogger(JobBagService.class);

	/* Constants */
	private static final JobBagService instance = new JobBagService();
	private static final JobBagSpliteService jobbagSpliteService = JobBagSpliteService
			.getInstance();
	private static final SyslogService syslogService = SyslogService
			.getInstance();

	private JobBagService() {
		// CustomerDao = new CustomerDAO();

	}

	public static JobBagService getInstance() {
		return instance;
	}
	
	public List<JobBag> findJobBagNosByLike(String jobBagNo){
		  String queryStr = " from JobBag  where jobBagNo like :jobBagNo";
		  getSession().clear();
		  Query query = getSession().createQuery(queryStr);	 	      
		  query.setString("jobBagNo", jobBagNo + "%");
	      List<JobBag> retList = query.list();
	      return retList;
		
	}
	
	public List<JobBag> findJobBagsByCycleAndCustNoAndFile(String afpName, String custNo, Date cycleDate){

		  getSession().clear();
		  Customer customer = (Customer)getSession().get(
					"com.salmat.jbm.hibernate.Customer", custNo);		  
		  Criteria criteria = getSession().createCriteria(JobBag.class);
		  //cycle date要用between來query
		  Calendar calEnd = Calendar.getInstance();
		  calEnd.setTime(cycleDate);
		  calEnd.set(calEnd.get(Calendar.YEAR), calEnd.get(Calendar.MONTH), calEnd.get(Calendar.DATE), 23, 59, 59);
		  Date cycleEnd = calEnd.getTime();
		  List<JobBag> retList = criteria.add(Restrictions.eq("customer", customer))
		      .add(Restrictions.like("afpName", "%"+ afpName + "%"))
		      .add(Restrictions.between("cycleDate", cycleDate, cycleEnd))
		      .add(Restrictions.or(Restrictions.eq("isDamage", false), Restrictions.isNull("isDamage")))
		      .add(Restrictions.or(Restrictions.eq("isDeleted", false), Restrictions.isNull("isDeleted"))).list();		  

	      return retList;
		
	}
	
	public JobBag createNewJobBag(JobCode jobcode, Integer accounts,
			Integer pages, Integer sheets, String afpName, Date cycleDate,
			Date receiveDate, String logFilename, Integer feeder2,
			Integer feeder3, Integer feeder4, Integer feeder5, Integer tray1,
			Integer tray2, Integer tray3, Integer tray4, Integer tray5,
			Integer tray6, Integer tray7, Integer tray8, String lineData,
			Integer p1accounts, Integer p2accounts, Integer p3accounts,
			Integer p4accounts, Integer p5accounts, Integer p6accounts,
			Integer pxaccounts){
		return createNewJobBag(jobcode, accounts,
				pages, sheets, afpName, cycleDate,
				receiveDate, logFilename, feeder2,
				feeder3, feeder4, feeder5, tray1,
				tray2, tray3, tray4, tray5,
				tray6, tray7, tray8, lineData,
				p1accounts, p2accounts, p3accounts,
				p4accounts, p5accounts, p6accounts,
				pxaccounts, false, false);
		
	}
	public JobBag createNewJobBag(JobCode jobcode, Integer accounts,
			Integer pages, Integer sheets, String afpName, Date cycleDate,
			Date receiveDate, String logFilename, Integer feeder2,
			Integer feeder3, Integer feeder4, Integer feeder5, Integer tray1,
			Integer tray2, Integer tray3, Integer tray4, Integer tray5,
			Integer tray6, Integer tray7, Integer tray8, String lineData,
			Integer p1accounts, Integer p2accounts, Integer p3accounts,
			Integer p4accounts, Integer p5accounts, Integer p6accounts,
			Integer pxaccounts, boolean ingoreEndandBeging){
		return createNewJobBag(jobcode, accounts,
				pages, sheets, afpName, cycleDate,
				receiveDate, logFilename, feeder2,
				feeder3, feeder4, feeder5, tray1,
				tray2, tray3, tray4, tray5,
				tray6, tray7, tray8,lineData,
				p1accounts, p2accounts, p3accounts,
				p4accounts, p5accounts, p6accounts,
				pxaccounts, ingoreEndandBeging, false);
		
	}
	
	public JobBag createNewJobBag(JobCode jobcode, Integer accounts,
			Integer pages, Integer sheets, String afpName, Date cycleDate,
			Date receiveDate, String logFilename, Integer feeder2,
			Integer feeder3, Integer feeder4, Integer feeder5, Integer tray1,
			Integer tray2, Integer tray3, Integer tray4, Integer tray5,
			Integer tray6, Integer tray7, Integer tray8,String lineData,
			Integer p1accounts, Integer p2accounts, Integer p3accounts,
			Integer p4accounts, Integer p5accounts, Integer p6accounts,
			Integer pxaccounts, Integer ctAcctsOri){
		    JobBag jobBag =  createNewJobBag(jobcode, accounts,
				pages, sheets, afpName, cycleDate,
				receiveDate, logFilename, feeder2,
				feeder3, feeder4, feeder5, tray1,
				tray2, tray3, tray4, tray5,
				tray6, tray7, tray8,lineData,
				p1accounts, p2accounts, p3accounts,
				p4accounts, p5accounts, p6accounts,
				pxaccounts, false, false);
		    if(jobBag != null){
		       jobBag.setCtAcctsOri(ctAcctsOri);
		       this.save(jobBag);
		    }
		    return jobBag;
		
	}
	
	
	
	/**
	 * 產生JobBag 處理時, 需要的處理動作, 統一處理
	 * 
	 */
	public JobBag createNewJobBag(JobCode jobcode, Integer accounts,
			Integer pages, Integer sheets, String afpName, Date cycleDate,
			Date receiveDate, String logFilename, Integer feeder2,
			Integer feeder3, Integer feeder4, Integer feeder5, Integer tray1,
			Integer tray2, Integer tray3, Integer tray4, Integer tray5,
			Integer tray6, Integer tray7, Integer tray8,String lineData,
			Integer p1accounts, Integer p2accounts, Integer p3accounts,
			Integer p4accounts, Integer p5accounts, Integer p6accounts,
			Integer pxaccounts, boolean ignoreNotActive, boolean notMergerAnyMore) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumIntegerDigits(3);
		nf.setMinimumIntegerDigits(3);
		
		//先檢查是不是在不產生工單的區間中
		Date jobBagEnd = jobcode.getNoJobBagEnd();
		Date jobBagFrom = jobcode.getNoJobBagFrom();
		boolean isActive = jobcode.getIsEnabledContract() == null? false : jobcode.getIsEnabledContract();
		if(!ignoreNotActive && !isActive){
			log.info( jobcode.getJobCodeNo() + " job code was not be active. stop generated job bag");
			SysLog syslog = new SysLog();
			syslog.setLogType("CREATE_JOBBAG_ABORT");
			syslog.setSubject("job code was not active" );
			syslog.setMessageBody( "When job-code's isEnabledContract attribute was not true, The job bag couldn't be produced :" + jobcode.getJobCodeNo() + ":" + afpName);
			syslog.setIsException(false);
			Date now = new Date();
			syslog.setCreateDate(now);
			syslogService.save(syslog);
			return null;
		}				
		if(!ignoreNotActive && (jobBagEnd != null || jobBagFrom != null)){						
			if(jobBagFrom == null && jobBagEnd != null && cycleDate != null){
				//有設定End無設定From的狀況
				if(jobBagEnd.getTime() > cycleDate.getTime()){
					SysLog syslog = new SysLog();
					syslog.setLogType("CREATE_JOBBAG_ABORT");
					syslog.setSubject("not in produced period" );
					syslog.setMessageBody( "Job bag produced time was not in active period :" + jobcode.getJobCodeNo() + ": jobbag not be active till:" + sdf.format(jobBagEnd) + ":" + afpName);
					syslog.setIsException(false);
					Date now = new Date();
					syslog.setCreateDate(now);
					syslogService.save(syslog);					
				   return null;
				}				   
			}else if(jobBagEnd == null && jobBagFrom != null && cycleDate != null){
				//有設定From無設定End的狀況
				if(jobBagFrom.getTime() < cycleDate.getTime()){
					SysLog syslog = new SysLog();
					syslog.setLogType("CREATE_JOBBAG_ABORT");
					syslog.setSubject("not in produced period" );
					syslog.setMessageBody( "Job bag produced time was not in active period :" + jobcode.getJobCodeNo() + ": jobbag not be active from:" + sdf.format(jobBagFrom) + ":" + afpName);
					syslog.setIsException(false);
					Date now = new Date();
					syslog.setCreateDate(now);
					syslogService.save(syslog);
				   return null;
				}				   
			}else if(jobBagEnd != null && jobBagFrom != null && cycleDate != null){
				//有設定End也設定From的狀況
				if(jobBagFrom.getTime() < cycleDate.getTime() && cycleDate.getTime() < jobBagEnd.getTime()){
					SysLog syslog = new SysLog();
					syslog.setLogType("CREATE_JOBBAG_ABORT");
					syslog.setSubject("not in produced period" );
					syslog.setMessageBody( "Job bag produced time was not in active period :" + jobcode.getJobCodeNo() + ": jobbag not be active from:" + sdf.format(jobBagFrom) + ": jobbag not be active till:" + sdf.format(jobBagEnd) + ":" + afpName);
					syslog.setIsException(false);
					Date now = new Date();
					syslog.setCreateDate(now);
					syslogService.save(syslog);
				   return null;
				}
			}
		
			
		}
		
		
		// 檢查是否重複轉入, 若是已經存在的工單, 更新 accounts/pages/sheets..等資訊即可
		// if 十個月內的 logFilename + lineData ==> 重複轉入, return null
		// else job_code_no + logFile + cycleDate 相同, 更新 accounts/pages/sheets..等資訊即可
		// else add new job_Bag
		// 新增工單
		JobBag jobbag = new JobBag();

		try {

			Boolean retCheckExistJobBagSplite = false;
			if (null != logFilename && logFilename.length() > 0
					&& lineData.length() > 0
					&& !logFilename.startsWith("手動開單")) {
				retCheckExistJobBagSplite = checkExistLogFilenameLineData(
						logFilename, lineData);
				if (retCheckExistJobBagSplite) {
					log.info("重複轉入, log File Name: " + logFilename + "Line:"
							+ lineData);
					return null;
				}
			}

			if (null != jobcode && null != logFilename
					&& logFilename.length() > 0 && null != afpName
					&& afpName.length() > 0
					&& !logFilename.startsWith("手動開單")) {
				JobBagSplite jobBagSplite = checkExistJobBagSplite(jobcode,
						logFilename, afpName, cycleDate);
				if (null != jobBagSplite) {
					
					log.info("更新工單, log File Name: " + logFilename
							+ "jobBagSplite:"
							+ jobBagSplite.getJobBagSpliteNo());
					
					String jobBagNo = null;
					if(jobBagSplite.getJobBag() != null)
					   jobBagNo = jobBagSplite.getJobBag().getJobBagNo();
					else
					   jobBagNo = jobBagSplite.getJobBagSpliteNo().substring(0,	jobBagSplite.getJobBagSpliteNo().length() - 3);

					String rootJobbagNo = "";

					String logFileSeq = jobcode.getLogFileSeq();
					try{
						logFileSeq = nf.format(Integer.parseInt(logFileSeq));
					}catch(Exception e){
						log.error("logFileSeq is:" + logFileSeq );
						log.error("", e);
						logFileSeq = "001";
					}

					jobcode.setLogFileSeq(logFileSeq);
					
					// 非第一批工單批號
					/*
					if (!jobcode.getLogFileSeq().equalsIgnoreCase("001")) {
						
						Integer jobbagLast3Code = Integer.parseInt(jobBagNo
								.substring(jobBagNo.length() - 3,
										jobBagNo.length()));
						jobbagLast3Code = jobbagLast3Code - 1; // 上一筆

						if (jobbagLast3Code > 100)
							rootJobbagNo = jobBagNo.substring(0,
									jobBagNo.length() - 3)
									+ jobbagLast3Code.toString();
						else if (jobbagLast3Code > 10)
							rootJobbagNo = jobBagNo.substring(0,
									jobBagNo.length() - 3)
									+ "0" + jobbagLast3Code.toString();
						else
							rootJobbagNo = jobBagNo.substring(0,
									jobBagNo.length() - 3)
									+ "00" + jobbagLast3Code.toString();
					} else {
						rootJobbagNo = jobBagNo; // 若是第一批
					}
					*/
					rootJobbagNo = jobBagNo;


					JobBag rootJobbag = this.findById(rootJobbagNo);

					// JobBag jobbag = this.findById(jobBagNo);
					// 重新計算 accounts/pages/.... 等資訊
					// 第一批工單批號, 以更新後的工單批號, 壓掉原來的資訊
					//if (jobcode.getLogFileSeq().equalsIgnoreCase("001")) {
						if (null != jobBagSplite.getAccounts()
								&& null != accounts && accounts >= 0) {
							rootJobbag.setAccounts(accounts);
						}
						if (null != jobBagSplite.getPages() && null != pages
								&& pages >= 0) {
							rootJobbag.setPages(pages);
						}
						if ( null != sheets	&& sheets > 0) {
							rootJobbag.setSheets(sheets);
						}else{
							if(sheets == null)
								sheets = 0;
							
							//從Code去取得單面或雙面列印
							String printType = null;
							Code code = rootJobbag.getCodeByLpCodePrintType();
							if(code != null){
							   printType = code.getCodeValueTw();
							}
							int doubleOrSingle = 1;
							if(printType != null && printType.indexOf("雙面") >= 0)
								doubleOrSingle = 2;
							
							//只在有pages且 pages > 0 且 無sheets時，才把sheets算出
							if((pages != null && pages > 0) && (sheets == null || sheets == 0) ){
								sheets = pages / doubleOrSingle;
								if(pages % doubleOrSingle == 1)
									sheets++;
							}
							int rootSheets = rootJobbag.getSheets() == null ? 0 : rootJobbag.getSheets(); 
							
							rootJobbag.setSheets(sheets);
							
							
						}
						if (null != jobBagSplite.getFeeder2()
								&& null != feeder2 && feeder2 >= 0) {
							
							rootJobbag.setFeeder2(feeder2);
						}
						if (null != jobBagSplite.getFeeder3()
								&& null != feeder3 && feeder3 >= 0) {
							rootJobbag.setFeeder3(feeder3);
						}
						if (null != jobBagSplite.getFeeder4()
								&& null != feeder4 && feeder4 >= 0) {
							rootJobbag.setFeeder4(feeder4);
						}
						if (null != jobBagSplite.getFeeder5()
								&& null != feeder5 && feeder5 >= 0) {
							rootJobbag.setFeeder5(feeder5);
						}

						if (null != jobBagSplite.getTray1() && null != tray1
								&& tray1 >= 0) {
							rootJobbag.setTray1(tray1);
						}
						if (null != jobBagSplite.getTray2() && null != tray2
								&& tray2 >= 0) {
							rootJobbag.setTray2(tray2);
						}
						if (null != jobBagSplite.getTray3() && null != tray3
								&& tray3 >= 0) {
							rootJobbag.setTray3(tray3);
						}
						if (null != jobBagSplite.getTray4() && null != tray4
								&& tray4 >= 0) {
							rootJobbag.setTray4(tray4);
						}
						if (null != jobBagSplite.getTray5() && null != tray5
								&& tray5 >= 0) {
							rootJobbag.setTray5(tray5);
						}
						if (null != jobBagSplite.getTray6() && null != tray6
								&& tray6 >= 0) {
							rootJobbag.setTray6(tray6);
						}
						if (null != jobBagSplite.getTray7() && null != tray7
								&& tray7 >= 0) {
							rootJobbag.setTray7(tray7);
						}
						if (null != jobBagSplite.getTray8() && null != tray8
								&& tray8 >= 0) {
							rootJobbag.setTray8(tray8);
						}

						if (null != jobBagSplite.getP1accounts()
								&& null != p1accounts && p1accounts >= 0) {
							rootJobbag.setP1accounts(p1accounts);
						}
						if (null != jobBagSplite.getP2accounts()
								&& null != p2accounts && p2accounts >= 0) {
							rootJobbag.setP2accounts(p2accounts);
						}
						if (null != jobBagSplite.getP3accounts()
								&& null != p3accounts && p3accounts >= 0) {
							rootJobbag.setP3accounts(p3accounts);
						}
						if (null != jobBagSplite.getP4accounts()
								&& null != p4accounts && p4accounts >= 0) {
							rootJobbag.setP4accounts(p4accounts);
						}
						if (null != jobBagSplite.getP5accounts()
								&& null != p5accounts && p5accounts >= 0) {
							rootJobbag.setP5accounts(p5accounts);
						}
						if (null != jobBagSplite.getP6accounts()
								&& null != p6accounts && p6accounts >= 0) {
							rootJobbag.setP6accounts(p6accounts);
						}
						if (null != jobBagSplite.getPxaccounts()
								&& null != pxaccounts && pxaccounts >= 0) {
							rootJobbag.setPxaccounts(pxaccounts);
						}
					//}
					/* 不同 seqNo的不再合併，所以以下程式碼不再使用
					else {
						Integer _accounts = 0;
						Integer _pages = 0;
						Integer _sheets = 0;
						// 非一批工單批號, 須累計accounts/pages
						if (null != jobBagSplite.getAccounts()
								&& null != accounts && accounts >= 0) {
							_accounts = rootJobbag.getAccounts()
									+ accounts;
							rootJobbag.setAccounts(_accounts);
						}
						if (null != jobBagSplite.getPages() && null != pages
								&& pages >= 0) {
							_pages = rootJobbag.getPages() + pages;
							rootJobbag.setPages(_pages);
						}
						//累加紙張數 
						if (null != jobBagSplite.getSheets() && null != sheets
								&& sheets > 0) {
							_sheets = rootJobbag.getSheets() + sheets;
							rootJobbag.setSheets(_sheets);
						}else if(pages != null && pages > 0 && (sheets == null || sheets == 0) ){
							if(sheets == null)
								sheets = 0;
							
							//從Code去取得單面或雙面列印
							String printType = null;
							Code code = rootJobbag.getCodeByLpCodePrintType();
							if(code != null){
							   printType = code.getCodeValueTw();
							}
							int doubleOrSingle = 1;
							if(printType != null && printType.indexOf("雙面") >= 0)
								doubleOrSingle = 2;
							
							//只在有pages且 pages > 0 且 無sheets時，才把sheets算出
							if((pages != null && pages > 0) && (sheets == null || sheets == 0) )
								sheets = pages / doubleOrSingle;
							int rootSheets = rootJobbag.getSheets() == null ? 0 : rootJobbag.getSheets(); 
							_sheets = rootSheets + sheets;
							rootJobbag.setSheets(_sheets);
						}
						if (null != jobBagSplite.getFeeder2()
								&& null != feeder2 && feeder2 >= 0) {
							Integer _feeder2 = rootJobbag.getFeeder2()
									+ feeder2;
							
							rootJobbag.setFeeder2(_feeder2);
						}
						if (null != jobBagSplite.getFeeder3()
								&& null != feeder3 && feeder3 >= 0) {
							Integer _feeder3 = rootJobbag.getFeeder3()
									+ feeder3;
							rootJobbag.setFeeder3(_feeder3);
						}
						if (null != jobBagSplite.getFeeder4()
								&& null != feeder4 && feeder4 >= 0) {
							Integer _feeder4 = rootJobbag.getFeeder4()
									+ feeder4;
							rootJobbag.setFeeder4(_feeder4);
						}
						if (null != jobBagSplite.getFeeder5()
								&& null != feeder5 && feeder5 >= 0) {
							Integer _feeder5 = rootJobbag.getFeeder5()
									+ feeder5;
							rootJobbag.setFeeder5(_feeder5);
						}

						if (null != jobBagSplite.getTray1() && null != tray1
								&& tray1 >= 0) {
							Integer _tray1 = rootJobbag.getTray1() + tray1;
							rootJobbag.setTray1(_tray1);
						}
						if (null != jobBagSplite.getTray2() && null != tray2
								&& tray2 >= 0) {
							Integer _tray2 = rootJobbag.getTray2() + tray2;
							rootJobbag.setTray2(_tray2);
						}
						if (null != jobBagSplite.getTray3() && null != tray3
								&& tray3 >= 0) {
							Integer _tray3 = rootJobbag.getTray3() + tray3;
							rootJobbag.setTray3(_tray3);
						}
						if (null != jobBagSplite.getTray4() && null != tray4
								&& tray4 >= 0) {
							Integer _tray4 = rootJobbag.getTray4() + tray4;
							rootJobbag.setTray4(_tray4);
						}

						if (null != jobBagSplite.getP1accounts()
								&& null != p1accounts && p1accounts >= 0) {
							Integer _p1accounts = rootJobbag.getP1accounts()
									+ p1accounts;
							rootJobbag.setP1accounts(_p1accounts);
						}
						if (null != jobBagSplite.getP2accounts()
								&& null != p2accounts && p2accounts >= 0) {
							Integer _p2accounts = rootJobbag.getP2accounts()
									+ p2accounts;
							rootJobbag.setP2accounts(_p2accounts);
						}
						if (null != jobBagSplite.getP3accounts()
								&& null != p3accounts && p3accounts >= 0) {
							Integer _p3accounts = rootJobbag.getP3accounts()
									+ p3accounts;
							rootJobbag.setP3accounts(_p3accounts);
						}
						if (null != jobBagSplite.getP4accounts()
								&& null != p4accounts && p4accounts >= 0) {
							Integer _p4accounts = rootJobbag.getP4accounts()
									+ p4accounts;
							rootJobbag.setP4accounts(_p4accounts);
						}
						if (null != jobBagSplite.getP5accounts()
								&& null != p5accounts && p5accounts >= 0) {
							Integer _p5accounts = rootJobbag.getP5accounts()
									+ p5accounts;
							rootJobbag.setP5accounts(_p5accounts);
						}
						if (null != jobBagSplite.getP6accounts()
								&& null != p6accounts && p6accounts >= 0) {
							Integer _p6accounts = rootJobbag.getP6accounts()
									+ p6accounts;
							rootJobbag.setP6accounts(_p6accounts);
						}
						if (null != jobBagSplite.getPxaccounts()
								&& null != pxaccounts && pxaccounts >= 0) {
							Integer _pxaccounts = rootJobbag.getPxaccounts()
									+ pxaccounts;
							rootJobbag.setPxaccounts(_pxaccounts);
						}

					}
					*/

					// rootJobbag.setAcctSum1s(null); //避免 Found shared
					// references to a collection:
					// com.salmat.jbm.hibernate.JobBag.acctSum1s
					
					//更新JobBag後，狀態也要變成初始化的狀態
					if (rootJobbag.getIsEdd() != null && rootJobbag.getIsEdd() && !"mail".equals(rootJobbag.getMailType()))
						rootJobbag.setJobBagStatus("EDD"); // EDD
					else if (rootJobbag.getIsLp() != null && !rootJobbag.getIsLp() && rootJobbag.getMpDmPs() != null && rootJobbag.getMpDmPs().length() == 0
							&& rootJobbag.getIsLg() != null && rootJobbag.getIsLg())
						rootJobbag.setJobBagStatus("NON_MP"); // 不需 列印 也不需 裝封
					else if (rootJobbag.getIsLp() != null && !rootJobbag.getIsLp() && rootJobbag.getMpDmPs() != null && rootJobbag.getMpDmPs().length() > 0
							&& rootJobbag.getIsLg() != null && rootJobbag.getIsLg())
						rootJobbag.setJobBagStatus("NON_LP"); // 不需 列印 但需 裝封
					else
						rootJobbag.setJobBagStatus("INIT");
					rootJobbag.setIsDeleted(false); //重新轉檔後，工單重新生成，標記刪除消失
					
					this.save(rootJobbag);

					// 調整rootJobbag 對應的 批次工單 accounts/pages/...等資訊
					Set<JobBagSplite> rootJobbagSplites = rootJobbag
							.getJobBagSplites();
					Iterator iterator = rootJobbagSplites.iterator();
					while (iterator.hasNext()) {
						JobBagSplite rootJobbagSplite = (JobBagSplite) iterator
								.next();
						if (rootJobbag.getPages() < 10000
								&& rootJobbag.getAccounts() > 0) {
							rootJobbagSplite.setLpPagesSeqBegin(1);
							rootJobbagSplite.setLpPagesSeqEnd(rootJobbag
									.getPages());
							rootJobbagSplite.setLpPagesSeqDiff(rootJobbag
									.getPages());
							rootJobbagSplite.setLpAccountSeqBegin(1);
							rootJobbagSplite.setLpAccountSeqEnd(rootJobbag
									.getAccounts());
							rootJobbagSplite.setLpAccountSeqDiff(rootJobbag
									.getAccounts());
						} else {
							rootJobbagSplite.setLpPagesSeqBegin(null);
							rootJobbagSplite.setLpPagesSeqEnd(null);
							rootJobbagSplite.setLpPagesSeqDiff(null);
							rootJobbagSplite.setLpAccountSeqBegin(null);
							rootJobbagSplite.setLpAccountSeqEnd(null);
							rootJobbagSplite.setLpAccountSeqDiff(null);
						}
						//更新後要重印，printing count重設為0
						rootJobbagSplite.setPrintingCount(0);
						rootJobbagSplite.setPages(rootJobbag.getPages());
						rootJobbagSplite.setAccounts(rootJobbag.getAccounts());						
						rootJobbagSplite.setSheets(rootJobbag.getSheets());

					}

					// 紀錄 更新工單批次
					// jobbagNo ==> null
					// jobbagSpliteNo = 原jobbagSpliteNo + "U" + ssSSS (秒/毫秒)
					JobBagSplite _jobBagSplite = new JobBagSplite();
					SimpleDateFormat formatter = new SimpleDateFormat("ssSSS");
					String updateFileName = formatter.format(new Date());
					_jobBagSplite.setAfpFilename(afpName);
					_jobBagSplite.setJobBagSpliteNo((jobBagSplite
							.getJobBagSpliteNo() + "U" + updateFileName).toUpperCase());
					_jobBagSplite.setLogFilename(logFilename);
					_jobBagSplite.setLineData(lineData);
					_jobBagSplite.setAccounts(accounts);
					_jobBagSplite.setPages(pages);
					_jobBagSplite.setSheets(sheets);
					
					_jobBagSplite.setFeeder2(feeder2);
					_jobBagSplite.setFeeder3(feeder3);
					_jobBagSplite.setFeeder4(feeder4);
					_jobBagSplite.setFeeder5(feeder5);
					_jobBagSplite.setTray1(tray1);
					_jobBagSplite.setTray2(tray2);
					_jobBagSplite.setTray3(tray3);
					_jobBagSplite.setTray4(tray4);
					_jobBagSplite.setTray5(tray5);
					_jobBagSplite.setTray6(tray6);
					_jobBagSplite.setTray7(tray7);
					_jobBagSplite.setTray8(tray8);
					_jobBagSplite.setP1accounts(p1accounts);
					_jobBagSplite.setP2accounts(p2accounts);
					_jobBagSplite.setP3accounts(p3accounts);
					_jobBagSplite.setP4accounts(p4accounts);
					_jobBagSplite.setP5accounts(p5accounts);
					_jobBagSplite.setP6accounts(p6accounts);
					_jobBagSplite.setPxaccounts(pxaccounts);
					
					_jobBagSplite.setReceiveDate(receiveDate);
					
					jobbagSpliteService.save(_jobBagSplite);
					return rootJobbag;
				}
			} // end 更新工單

			// 新增工單
			java.util.Date defaultValue = null;
			DateConverter converter = new DateConverter(defaultValue);
			ConvertUtils.register(converter, java.util.Date.class);
			BeanUtils.copyProperties(jobbag, jobcode);
			if("previewJobBag".equals(logFilename)){
				jobbag.setJobBagNo(this.getNewJobBagNo(jobcode.getJobCodeNo(), true));
			}else{
			    jobbag.setJobBagNo(this.getNewJobBagNo(jobcode.getJobCodeNo()));
			}
			jobbag.setJobCode(jobcode);
			
			Calendar calendar = Calendar.getInstance();
			Date now = new Date();
			
			boolean lp1 = false, lp2 = false, lp3 = false, mp1 = false, mp2 = false, mp3 = false;
			
			//lp1判斷
			if (null != jobcode.getLpinfoByIdfLpNo1()){
				String judgeBy = (jobcode.getLp1JudgeBy() == null)? "cycleDate" : jobcode.getLp1JudgeBy();
				if("cycleDate".equals(judgeBy) && cycleDate != null){					
					calendar.setTime(cycleDate);
				}else if("receivDate".equals(judgeBy) && receiveDate != null){
					calendar.setTime(receiveDate);
				}else if("systemDate".equals(judgeBy)){
					calendar.setTime(now);
				}
				lp1 = judgePeriod(calendar,  jobcode.getLpBeginMonth1(),
						jobcode.getLpEndMonth1(), jobcode.getLpBegindate1(), jobcode.getLpEnddate1());
			}
				
			//lp2判斷
			if (null != jobcode.getLpinfoByIdfLpNo2()){
				String judgeBy = (jobcode.getLp2JudgeBy() == null)? "cycleDate" : jobcode.getLp2JudgeBy();
				if("cycleDate".equals(judgeBy) && cycleDate != null){					
					calendar.setTime(cycleDate);
				}else if("receivDate".equals(judgeBy) && receiveDate != null){
					calendar.setTime(receiveDate);
				}else if("systemDate".equals(judgeBy)){
					calendar.setTime(now);
				}	
				lp2 = judgePeriod(calendar,  jobcode.getLpBeginMonth2(),
						jobcode.getLpEndMonth2(), jobcode.getLpBegindate2(), jobcode.getLpEnddate2());
			}
			
			//lp3判斷
			if (null != jobcode.getLpinfoByIdfLpNo3()){
				String judgeBy = (jobcode.getLp3JudgeBy() == null)? "cycleDate" : jobcode.getLp3JudgeBy();
				if("cycleDate".equals(judgeBy) && cycleDate != null){					
					calendar.setTime(cycleDate);
				}else if("receivDate".equals(judgeBy) && receiveDate != null){
					calendar.setTime(receiveDate);
				}else if("systemDate".equals(judgeBy)){
					calendar.setTime(now);
				}	
				lp3 = judgePeriod(calendar,  jobcode.getLpBeginMonth3(),
						jobcode.getLpEndMonth3(), jobcode.getLpBegindate3(), jobcode.getLpEnddate3());
			}
			
			//mp1判斷
			if (null != jobcode.getMpinfoByIdfMpNo1()){
				String judgeBy = (jobcode.getMp1JudgeBy() == null)? "cycleDate" : jobcode.getMp1JudgeBy();
				if("cycleDate".equals(judgeBy) && cycleDate != null){					
					calendar.setTime(cycleDate);
				}else if("receivDate".equals(judgeBy) && receiveDate != null){
					calendar.setTime(receiveDate);
				}else if("systemDate".equals(judgeBy)){
					calendar.setTime(now);
				}	
				mp1 = judgePeriod(calendar,  jobcode.getMpBeginMonth1(),
						jobcode.getMpEndMonth1(), jobcode.getMpBegindate1(), jobcode.getMpEnddate1());
			}
			//mp2判斷
			if (null != jobcode.getMpinfoByIdfMpNo2()){
				String judgeBy = (jobcode.getMp2JudgeBy() == null)? "cycleDate" : jobcode.getMp2JudgeBy();
				if("cycleDate".equals(judgeBy) && cycleDate != null){					
					calendar.setTime(cycleDate);
				}else if("receivDate".equals(judgeBy) && receiveDate != null){
					calendar.setTime(receiveDate);
				}else if("systemDate".equals(judgeBy)){
					calendar.setTime(now);
				}	
				mp2 = judgePeriod(calendar,  jobcode.getMpBeginMonth2(),
						jobcode.getMpEndMonth2(), jobcode.getMpBegindate2(), jobcode.getMpEnddate2());
			}
			//mp3判斷
			if (null != jobcode.getMpinfoByIdfMpNo3()){
				String judgeBy = (jobcode.getMp3JudgeBy() == null)? "cycleDate" : jobcode.getMp3JudgeBy();
				if("cycleDate".equals(judgeBy) && cycleDate != null){					
					calendar.setTime(cycleDate);
				}else if("receivDate".equals(judgeBy) && receiveDate != null){
					calendar.setTime(receiveDate);
				}else if("systemDate".equals(judgeBy)){
					calendar.setTime(now);
				}
				mp3 = judgePeriod(calendar,  jobcode.getMpBeginMonth3(),
						jobcode.getMpEndMonth3(), jobcode.getMpBegindate3(), jobcode.getMpEnddate3());
			}
			//回復成目前時間
			calendar.setTime(now);

			// 挑選區間內的 Laser Printer  Thor modified
			Lpinfo selectedLpinfo = new Lpinfo();
			jobbag.setLpinfoByIdfLpNo1(null); // RESET job_bag lpinfo
			Mpinfo selectedMpinfo = new Mpinfo();
			jobbag.setMpinfoByIdfMpNo1(null); // RESET job_bag mpinfo
			
			if(lp1){
				jobbag.setLpinfoByIdfLpNo1(jobcode.getLpinfoByIdfLpNo1());
				BeanUtils.copyProperties(selectedLpinfo,
						jobcode.getLpinfoByIdfLpNo1());
			}
			if(lp2){
				jobbag.setLpinfoByIdfLpNo1(jobcode.getLpinfoByIdfLpNo2());
				BeanUtils.copyProperties(selectedLpinfo,
						jobcode.getLpinfoByIdfLpNo2());
			}
			if(lp3){
				jobbag.setLpinfoByIdfLpNo1(jobcode.getLpinfoByIdfLpNo3());
				BeanUtils.copyProperties(selectedLpinfo,
						jobcode.getLpinfoByIdfLpNo3());
			}
			if(mp1){
				jobbag.setMpinfoByIdfMpNo1(jobcode.getMpinfoByIdfMpNo1());
				BeanUtils.copyProperties(selectedMpinfo,
						jobcode.getMpinfoByIdfMpNo1());
			}
			if(mp2){
				jobbag.setMpinfoByIdfMpNo1(jobcode.getMpinfoByIdfMpNo2());
				BeanUtils.copyProperties(selectedMpinfo,
						jobcode.getMpinfoByIdfMpNo2());
			}
			if(mp3){
				jobbag.setMpinfoByIdfMpNo1(jobcode.getMpinfoByIdfMpNo3());
				BeanUtils.copyProperties(selectedMpinfo,
						jobcode.getMpinfoByIdfMpNo3());
			}

			// Clone LP into to job_bag, 切斷 job_bag 跟 lpinfo reference 關係,
			if (null != selectedLpinfo.getLpNo() && jobbag.getIsLp()) {
				jobbag.setLpProdmemo(selectedLpinfo.getProdmemo());
				jobbag.setCodeByLpCodePrinter(selectedLpinfo
						.getCodeByCodePrinter());
				jobbag.setCodeByLpCodePrintType(selectedLpinfo
						.getCodeByCodePrintType());
				jobbag.setLpPcode1(selectedLpinfo.getPcode1());
				jobbag.setLpPcode2(selectedLpinfo.getPcode2());
				jobbag.setLpPcode3(selectedLpinfo.getPcode3());
				jobbag.setLpPcode4(selectedLpinfo.getPcode4());
				jobbag.setLpPcode5(selectedLpinfo.getPcode5());
				jobbag.setLpPcode6(selectedLpinfo.getPcode6());
				jobbag.setLpPcode7(selectedLpinfo.getPcode7());
				jobbag.setLpPcode8(selectedLpinfo.getPcode8());
				jobbag.setLpRemark(selectedLpinfo.getRemark());
			}

			// Clone MP into to job_bag, 切斷 job_bag 跟 mpinfo reference 關係,
			if (null != selectedMpinfo.getMpNo()
					&& jobbag.getMpDmPs().equalsIgnoreCase("MP")) {
				jobbag.setMpProdmemo(selectedMpinfo.getProdmemo());
				jobbag.setMpPrintCode(selectedMpinfo.getPrintCode());
				jobbag.setMpStampSetFg(selectedMpinfo.getStampSetFg());
				jobbag.setMpZipFg(selectedMpinfo.getZipFg());
				jobbag.setMpMp1Iflag(selectedMpinfo.getMp1Iflag());
				jobbag.setMpMp2Iflag(selectedMpinfo.getMp2Iflag());
				jobbag.setMpMp3Iflag(selectedMpinfo.getMp3Iflag());
				jobbag.setMpMp4Iflag(selectedMpinfo.getMp4Iflag());
				jobbag.setMpMp1Word(selectedMpinfo.getMp1Word());
				jobbag.setMpMp2Word(selectedMpinfo.getMp2Word());
				jobbag.setMpMp3Word(selectedMpinfo.getMp3Word());
				jobbag.setMpMp4Word(selectedMpinfo.getMp4Word());
				jobbag.setMpRemark(selectedMpinfo.getRemark());
			}
			
			
			
			

			// Clone PS into to job_bag, 切斷 job_bag 跟 psinfo reference 關係,
			if (null != jobbag.getPsinfo()
					&& jobbag.getMpDmPs().equalsIgnoreCase("PS")) {
				jobbag.setPsProdmemo(jobbag.getPsinfo().getProdmemo());
				jobbag.setCodeByPsCodePsType(jobbag.getPsinfo()
						.getCodeByCodePsType());
				jobbag.setPsZipFg(jobbag.getPsinfo().getZipFg());
				jobbag.setPsRemark(jobbag.getPsinfo().getRemark());
			}

			// Clone LG into to job_bag, 切斷 job_bag 跟lginfo reference 關係,
			if (null != jobbag.getLginfo() && jobbag.getIsLg()
					&& null != jobbag.getDispatchType()
					&& jobbag.getDispatchType().equalsIgnoreCase("MAIL")) {
				jobbag.setLgProgNm(jobbag.getLginfo().getProgNm());
				jobbag.setCodeByLgCodeMailToArea1(jobbag.getLginfo()
						.getCodeByCodeMailToArea1());
				jobbag.setCodeByLgCodeMailToArea2(jobbag.getLginfo()
						.getCodeByCodeMailToArea2());
				jobbag.setCodeByLgCodeMailToArea3(jobbag.getLginfo()
						.getCodeByCodeMailToArea3());
				jobbag.setCodeByLgCodeMailToArea4(jobbag.getLginfo()
						.getCodeByCodeMailToArea4());
				jobbag.setCodeByLgCodeMailToArea5(jobbag.getLginfo()
						.getCodeByCodeMailToArea5());
				jobbag.setCodeByLgCodeMailToArea6(jobbag.getLginfo()
						.getCodeByCodeMailToArea6());
				jobbag.setLgPList(jobbag.getLginfo().getPList());
				jobbag.setLgMailToAreaDisplay(jobbag.getLginfo()
						.getMailToAreaDisplay());
				jobbag.setCodeByCodeLgType(jobbag.getLginfo()
						.getCodeByCodeLgType());
				jobbag.setCodeByCodeMailCategory(jobbag.getLginfo()
						.getCodeByCodeMailCategory());
				jobbag.setCodeByCodeMailToPostoffice(jobbag.getLginfo()
						.getCodeByCodeMailToPostoffice());
				jobbag.setLgDisplayQty(jobbag.getLginfo().getLgDisplayQty());
			}

			// Clone LC into to job_bag, 切斷 job_bag 跟lcinfo reference 關係,
			if (null != jobbag.getLcinfo() && jobbag.getIsLg()) {
				jobbag.setLcProgNm(jobbag.getLcinfo().getProgNm());
				jobbag.setLcPList(jobbag.getLcinfo().getPList());
				jobbag.setLcTemplate(jobbag.getLcinfo().getTemplate());
			}
			// Clone return into to job_bag, 切斷 job_bag 跟lcinfo reference 關係,
			if (null != jobbag.getReturninfo()
					&& jobbag.getIsLg()
					&& jobbag.getDispatchType().equalsIgnoreCase(
							"RETURN_CUSTOMER")) {
				jobbag.setRetReturnAddress(jobbag.getReturninfo()
						.getReturnAddress());
				jobbag.setRetUserCompany(jobbag.getReturninfo()
						.getUserCompany());
				jobbag.setRetUserName(jobbag.getReturninfo().getUserName());
				jobbag.setRetUserTel(jobbag.getReturninfo().getUserTel());
			}
		
			//有pages無sheets，則用算的
			
			//從Code去取得單面或雙面列印
			String printType = null;
			
			Code code = jobbag.getCodeByLpCodePrintType();
			if(code != null){
				code.getId();
			    printType = code.getCodeValueTw();
			}
			
			//如果從Code取不到列印方式，直接從LpInfo取得printType
			if(printType == null && selectedLpinfo != null)
				printType = selectedLpinfo.getPrintType();
			
			int doubleOrSingle = 1;
			if(printType != null && printType.indexOf("雙面") >= 0)
				doubleOrSingle = 2;
			//只在有pages且 pages > 0 且 無sheets時，才把sheets算出
			if((pages != null && pages > 0) && (sheets == null || sheets == 0) ){
				sheets = pages / doubleOrSingle;
				if(pages % doubleOrSingle == 1)
					sheets++;
			}
			
			jobbag.setAccounts(accounts);
			jobbag.setPages(pages);
			jobbag.setSheets(sheets);
			
			jobbag.setFeeder2(feeder2);
			jobbag.setFeeder3(feeder3);
			jobbag.setFeeder4(feeder4);
			jobbag.setFeeder5(feeder5);
			
			//如果原始帶入的feeder2 ~ 5為零且
			//有設定 插件二 - 插件五, 非智慧型且內容不為空白  
			//請將對應的 feeder2-feeder5數量填上 Accounts
            if(jobbag.getMpMp1Iflag() != null && !jobbag.getMpMp1Iflag() && jobbag.getMpMp1Word() != null && !jobbag.getMpMp1Word().trim().equals("") && (feeder2 == null || feeder2 == 0)){            	
            	jobbag.setFeeder2(accounts);
            }
            if(jobbag.getMpMp2Iflag() != null && !jobbag.getMpMp2Iflag() && jobbag.getMpMp2Word() != null && !jobbag.getMpMp2Word().trim().equals("") && (feeder3 == null || feeder3 == 0)){
            	jobbag.setFeeder3(accounts);
            }
            if(jobbag.getMpMp3Iflag() != null && !jobbag.getMpMp3Iflag() && jobbag.getMpMp3Word() != null && !jobbag.getMpMp3Word().trim().equals("") && (feeder4 == null || feeder4 == 0)){
            	jobbag.setFeeder4(accounts);
            }
            if(jobbag.getMpMp4Iflag() != null && !jobbag.getMpMp4Iflag() && jobbag.getMpMp4Word() != null && !jobbag.getMpMp4Word().trim().equals("") && (feeder5 == null || feeder5 == 0)){
            	jobbag.setFeeder5(accounts);
            }
			
			
			jobbag.setTray1(tray1);
			jobbag.setTray2(tray2);
			jobbag.setTray3(tray3);
			jobbag.setTray4(tray4);
			jobbag.setTray5(tray5);
			jobbag.setTray6(tray6);
			jobbag.setTray7(tray7);
			jobbag.setTray8(tray8);
			jobbag.setP1accounts(p1accounts);
			jobbag.setP2accounts(p2accounts);
			jobbag.setP3accounts(p3accounts);
			jobbag.setP4accounts(p4accounts);
			jobbag.setP5accounts(p5accounts);
			jobbag.setP6accounts(p6accounts);
			jobbag.setPxaccounts(pxaccounts);
		
			jobbag.setAfpName(afpName);// 實際AFP 檔名
			
			jobbag.setCycleDate(cycleDate);
			jobbag.setReceiveDate(receiveDate);
			jobbag.setLogFilename(logFilename);// log file 檔名
			jobbag.setLogFileSeq(jobcode.getLogFileSeq());
			jobbag.setSpliteCount(1);// 預設批次 =1
			jobbag.setCreateDate(calendar.getTime());// 建立時間 = sysDate

			if (jobbag.getIsEdd() != null && jobbag.getIsEdd())
				jobbag.setJobBagStatus("EDD"); // EDD
			else if (jobbag.getIsLp() != null && !jobbag.getIsLp() && jobbag.getMpDmPs() != null && jobbag.getMpDmPs().length() == 0
					&& jobbag.getIsLg() != null && jobbag.getIsLg())
				jobbag.setJobBagStatus("NON_MP"); // 不需 列印 也不需 裝封
			else if (jobbag.getIsLp() != null && !jobbag.getIsLp() && jobbag.getMpDmPs() != null && jobbag.getMpDmPs().length() > 0
					&& jobbag.getIsLg() != null && jobbag.getIsLg())
				jobbag.setJobBagStatus("NON_LP"); // 不需 列印 但需 裝封
			else
				jobbag.setJobBagStatus("INIT");

			// 計算截止日期 截止日期 = receivdeDate + dead_time
			Calendar deadLine = Calendar.getInstance();			
			deadLine.set(deadLine.get(Calendar.YEAR), deadLine.get(Calendar.MONTH), deadLine.get(Calendar.DATE), 0, 0, 0);
			deadLine.set(Calendar.MILLISECOND, 0);
			if (null != jobbag.getDeadTime()
					&& !jobbag.getDeadTime().equalsIgnoreCase("0")) {
				//把dead_line的小時轉成天
				int days = Integer.parseInt(jobbag.getDeadTime()) / 24;
				//找出未來30天的Holiday的list
				List<Holiday> holidays = HolidayService.queryByNow(deadLine.getTime());

				for(int i = 1 ; i <= days ; i++){
					//加一天
					deadLine.add(Calendar.DATE, 1);
					for(Holiday holiday : holidays){
						//如果是假期時，就往下再算一天 
						if(holiday.getDate().getTime() == deadLine.getTimeInMillis() && holiday.getIsHoliday() != null && holiday.getIsHoliday()){
							i--;
						}
					}
				}
				jobbag.setDeadLine(deadLine.getTime());
			}
            
			
			
			// jobbag.setAcctSum1s(null);
			this.save(jobbag);

			// 產生 預設的 JobBagSplite
			JobBagSplite jobBagSplite = new JobBagSplite();
			jobBagSplite.setJobBag(jobbag);
			String seq = jobcode.getLogFileSeq();
			if(seq == null)
				seq = "001";
			
			try{
			   seq = nf.format(Integer.parseInt(seq));
			}catch(Exception e){
				log.error("", e);
				seq = "001";
			}
			jobBagSplite.setJobBagSpliteNo(jobbag.getJobBagNo().toUpperCase()
					+ "001");
			jobBagSplite.setLogFileSeq(seq);
			jobBagSplite.setAfpFilename(afpName);
			// 若只有1批: Pages Seq. = 1 ~ 工單.頁數 , Account Seq. = 1 ~ 工單.accounts
			// 若有多批: 全部留空白
			if (jobbag.getLogFileSeq() != null && jobbag.getLogFileSeq().equalsIgnoreCase("001")) {
				if (jobbag.getPages() < 10000 && jobbag.getPages() > 0) {
					jobBagSplite.setLpPagesSeqBegin(1);
					jobBagSplite.setLpPagesSeqEnd(jobbag.getPages());
					jobBagSplite.setLpPagesSeqDiff(jobbag.getPages());
					jobBagSplite.setLpAccountSeqBegin(1);
					jobBagSplite.setLpAccountSeqEnd(jobbag.getAccounts());
					jobBagSplite.setLpAccountSeqDiff(jobbag.getAccounts());
				} else {
					jobBagSplite.setLpPagesSeqBegin(null);
					jobBagSplite.setLpPagesSeqEnd(null);
					jobBagSplite.setLpPagesSeqDiff(null);
					jobBagSplite.setLpAccountSeqBegin(null);
					jobBagSplite.setLpAccountSeqEnd(null);
					jobBagSplite.setLpAccountSeqDiff(null);
				}
			}

			jobBagSplite.setLogFilename(logFilename);
			jobBagSplite.setLineData(lineData);
			jobBagSplite.setAccounts(accounts);
			jobBagSplite.setPages(pages);
			jobBagSplite.setSheets(sheets);
			
			jobBagSplite.setFeeder2(feeder2);
			jobBagSplite.setFeeder3(feeder3);
			jobBagSplite.setFeeder4(feeder4);
			jobBagSplite.setFeeder5(feeder5);
			jobBagSplite.setTray1(tray1);
			jobBagSplite.setTray2(tray2);
			jobBagSplite.setTray3(tray3);
			jobBagSplite.setTray4(tray4);
			jobBagSplite.setTray5(tray5);
			jobBagSplite.setTray6(tray6);
			jobBagSplite.setTray7(tray7);
			jobBagSplite.setTray8(tray8);
			jobBagSplite.setP1accounts(p1accounts);
			jobBagSplite.setP2accounts(p2accounts);
			jobBagSplite.setP3accounts(p3accounts);
			jobBagSplite.setP4accounts(p4accounts);
			jobBagSplite.setP5accounts(p5accounts);
			jobBagSplite.setP6accounts(p6accounts);
			jobBagSplite.setPxaccounts(pxaccounts);
			jobBagSplite.setReceiveDate(receiveDate);

			jobbagSpliteService.save(jobBagSplite);

		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("", e);
			return null;
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("", e);
			return null;
		}

		return jobbag;
	}

	private boolean judgePeriod( Calendar judgeCal,
			Integer beginMonth, Integer endMonth,
			Integer beginDate, Integer endDate) {
		
		
			int endYear = judgeCal.get(Calendar.YEAR);
			int year = judgeCal.get(Calendar.YEAR);
			boolean endMonthInit = false;

			//如果沒有設定開始及結束月，且結束日小於開始日，代表是跨月
			if((endDate != null && endDate != 0) && (beginDate != null && beginDate != 0) 
					&& endDate < beginDate && 
					(null == beginMonth || 0 == beginMonth) &&
					(null == endMonth || 0 == endMonth)){
				endMonth = judgeCal.get(Calendar.MONTH);
				endMonth += 1;
				endMonthInit = true;
			}
		
			//資料庫是1~12月，Calendar是0~11，所以要減1
		    //如果有值時BeginMonth和endMonth改成設定值
			if(null != beginMonth && 0 != beginMonth){
				beginMonth = beginMonth - 1;
			}else{
				beginMonth = judgeCal.get(Calendar.MONTH);
			}
			
			if(!endMonthInit && null != endMonth && 0 != endMonth){
				endMonth = endMonth - 1;
				//如果結束月小於開始月，代表是跨年
				if(endMonth < beginMonth)
					endYear += 1;
			}else if(!endMonthInit){
				endMonth = judgeCal.get(Calendar.MONTH);
			}
			
			//代表沒設定開始日
			if(null == beginDate || 0 == beginDate){
				beginDate = judgeCal.get(Calendar.DATE);
			}
			
			//代表沒設定結束日
			if(null == endDate || 0 == endDate){
				endDate = judgeCal.get(Calendar.DATE);
			}
			
			
			Calendar endCal = Calendar.getInstance();
			Calendar beginCal = Calendar.getInstance();

			//先設為開始月一日
			beginCal.set(year, beginMonth, 1);
			//取得開始月份最大天數
			int beginMonthMaxDate = beginCal.getActualMaximum(Calendar.DAY_OF_MONTH);
			//如果begin Date大於最大天數，設為最大日的23:59:59
			//如果沒有，begin date設為前一日的23:59:59
			if(beginDate > beginMonthMaxDate){
				beginCal.set(year, beginMonth, beginMonthMaxDate, 23, 59, 59);
			}else{
				beginCal.set(year, beginMonth, beginDate - 1, 23, 59, 59);
			}

			
			
            //先設為結束月一日			
			endCal.set(endYear, endMonth, 1);
			//取得結束月份最大天數
			int endMonthMaxDate = endCal.getActualMaximum(Calendar.DAY_OF_MONTH);
			//如果End Date大於最大天數，設為最大天數
			if(endDate > endMonthMaxDate)
				endDate = endMonthMaxDate;
			endCal.set(year, endMonth, endDate, 23, 59, 59);
			
			if(beginCal.getTimeInMillis() < judgeCal.getTimeInMillis() &&  judgeCal.getTimeInMillis() <= endCal.getTimeInMillis()){
				return true;
			}else{   
		        return false;
			}
	}

	/**
	 * 檢查 logFilename + line number 是否已經存在 若存在, 表示重複轉入
	 * 只查詢十個月內的，避免超過一年後，linedata相同，檔名相同時，無法增加工單
	 */
	public Boolean checkExistLogFilenameLineData(String logFilename,
			String lineData) {
		try { 
			Calendar cal = Calendar.getInstance();
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) - 10, cal.get(Calendar.DATE));
			String beforeDate = sdf.format(cal.getTime());
			String queryString = " select {j.*} "
					+ " from dbo.job_bag_splite j  "
					+ " where j.LOG_FILENAME = ? and j.LINE_DATA = ? and j.RECEIVE_DATE > '" + beforeDate + "'";

			SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession()
					.createSQLQuery(queryString);
			sqlQuery.addEntity("j", JobBagSplite.class);
			sqlQuery.setParameter(0, logFilename);
			sqlQuery.setParameter(1, lineData);

			List retList = sqlQuery.list();
			if (null != retList && retList.size() >= 1)
				return true;
			else
				return false;
		} catch (RuntimeException re) {
			log.error("", re);
			return false;
		}
	}

	/**
	 * 檢查JobBagSplite是否已經存在 若存在, 更新accounts/pages/...
	 * 只查詢十個月內的，避免超過一年後，檔名相同時，無法增加工單，只能更新
	 */
	private JobBagSplite checkExistJobBagSplite(JobCode jobcode,
			String logFilename, String afpName, Date cycleDate) {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) - 10, cal.get(Calendar.DATE));
		String beforeDate = sdf.format(cal.getTime());		
		try {
			String logfileseq = "";
			try{
			    logfileseq = StringUtils.leftPad(jobcode.getLogFileSeq(), 3, '0');
			}catch(Exception e){
				log.error("", e);
			}
			/*String queryString = " select {j.*} "
					+ " from dbo.job_bag_splite j  "
					+ " where SUBSTRING(j.JOB_BAG_SPLITE_NO,1,len(j.JOB_BAG_SPLITE_NO)-12  ) = '"
					+ jobcode.getJobCodeNo() + "'"
					+ " and j.AFP_FILENAME = ? and j.LOG_FILENAME = ? and j.RECEIVE_DATE > '" + beforeDate + "'";
			*/
			// 等到jobbagsplit的logfileseq都有了之後再改用這個
			String queryString = " select {j.*} "
					+ " from dbo.job_bag_splite j, dbo.job_bag j1 "
					+ " where j1.JOB_BAG_NO = j.IDF_JOB_BAG_NO and j1.idf_JOB_CODE_NO = '"
					+ jobcode.getJobCodeNo() + "' "
					+ " and j.AFP_FILENAME = ? and j.LOG_FILENAME = ? and j.RECEIVE_DATE > '" + beforeDate + "' and j.LOG_FILE_SEQ = ? and j1.CYCLE_DATE = ? ";
			
			 

			SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession()
					.createSQLQuery(queryString);
			sqlQuery.addEntity("j", JobBagSplite.class);
			sqlQuery.setParameter(0, afpName);
			sqlQuery.setParameter(1, logFilename);
			if(jobcode.getLogFileSeq() != null)
			   sqlQuery.setParameter(2, jobcode.getLogFileSeq());
			else
			   sqlQuery.setParameter(2, "001");
			sqlQuery.setParameter(3, cycleDate);

			List retList = sqlQuery.list();
			if (null != retList && retList.size() >= 1)
				return (JobBagSplite) retList.get(0);
			else
				return null;
		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}
	}

	/**
	 * 產生jobBag 流水號 input: jobCodeNo jobBagNo = jobCodeNo + yymmdd+ 001(流水號)
	 */
	
	public String getNewJobBagNo(String jobCodeNo) {
		return getNewJobBagNo(jobCodeNo, false);
	}
	
	public String getNewJobBagNo(String jobCodeNo, Date cycleDate) {
		return getNewJobBagNo(jobCodeNo, cycleDate, false);
	}
	
	public String getNewJobBagNo(String jobCodeNo, boolean preview) {
		return getNewJobBagNo(jobCodeNo, null, preview);
	}
	
	/**
	 * 產生jobBag 流水號 input: jobCodeNo jobBagNo = jobCodeNo + yymmdd+ 001(流水號)
	 */
	public String getNewJobBagNo(String jobCodeNo, Date cycleDate, boolean preview) {
		
		String dateStr = null;
		if(preview){
			// 預覽時設為公元2000年
			Calendar previewDate = Calendar.getInstance();		
			previewDate.set(2000, previewDate.get(Calendar.MONTH), previewDate.get(Calendar.DATE));
			dateStr = new SimpleDateFormat("yyMMdd").format(previewDate.getTime());
			return jobCodeNo + dateStr + "999"; // 設為最後一碼
		}else if(cycleDate == null){
			//沒傳入cycleDate時以系統時間
		    dateStr = new SimpleDateFormat("yyMMdd").format(new Date());
		}else{			
			dateStr = new SimpleDateFormat("yyMMdd").format(cycleDate);
		}
		try {
			String queryString = " select max( JOB_BAG_NO) maxId "
					+ " from job_bag  " + " where JOB_BAG_NO like '"
					+ jobCodeNo + dateStr + "%' and ( IS_DAMAGE is null or IS_DAMAGE = 0 )";

			SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession()
					.createSQLQuery(queryString);
			// sqlQuery.addEntity("j", JobBag.class);
			// sqlQuery.addScalar("maxId");
			// sqlQuery.setParameter(0, jobCodeNo+dateStr);

			List retList = sqlQuery.list();
			String cruuentJabBagID = null;
			if (null != retList && retList.size() > 0)
				cruuentJabBagID = (String) retList.get(0);
			
			if (null == cruuentJabBagID)
				return jobCodeNo + dateStr + "001"; // 當天第一碼
			
			else {
				Integer currentSEQ = Integer.parseInt(cruuentJabBagID
						.substring(cruuentJabBagID.length() - 3,
								cruuentJabBagID.length()));
				currentSEQ = currentSEQ + 1;
				NumberFormat nf = NumberFormat.getInstance();
				nf.setMaximumIntegerDigits(3);
				nf.setMinimumIntegerDigits(3);
				return jobCodeNo + dateStr + nf.format(currentSEQ);
			}
		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}

	}

	/**
	 * 取得getJobBagSplites input: JobBag return jobBagSplites List
	 */
	public List getJobBagSplites(JobBag jobbag) {
		// TODO Auto-generated method stub
		String NowTime = new SimpleDateFormat("yyMMdd").format(new Date());
        if(jobbag != null)
		   try {
			   String queryString = " select {j.*}  "
					   + " from job_bag_splite j "
					   + " where j.IDF_JOB_BAG_NO = ? order by j.JOB_BAG_SPLITE_NO ";

			   SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession()
					   .createSQLQuery(queryString);
			   sqlQuery.addEntity("j", JobBagSplite.class);
			   sqlQuery.setParameter(0, jobbag.getJobBagNo());

			   List retList = sqlQuery.list();
			   return retList;

		   } catch (RuntimeException re) {
			   log.error("", re);
			   return null;
		   }
		else
		   return null;

	}

	/**
	 * 若是 ddn,ddnn..合併處理, LogFileSeq != 001 input: JobBag return jobBagSplites
	 * List
	 */
	public Boolean mergeJabbag(JobBag jobbag) {
		// TODO Auto-generated method stub
		try {
			Integer jobbagLast3Code = Integer.parseInt(jobbag.getJobBagNo()
					.substring(jobbag.getJobBagNo().length() - 3,
							jobbag.getJobBagNo().length()));
			if(jobbagLast3Code - 1 != 0)
			   jobbagLast3Code = jobbagLast3Code - 1; // 上一筆

			String rootJobbagNo = "";
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumIntegerDigits(3);
			nf.setMinimumIntegerDigits(3);
			rootJobbagNo = jobbag.getJobBagNo().substring(0,
					jobbag.getJobBagNo().length() - 3)
					+ nf.format(jobbagLast3Code);
			/*
			if (jobbagLast3Code > 100)
				rootJobbagNo = jobbag.getJobBagNo().substring(0,
						jobbag.getJobBagNo().length() - 3)
						+ jobbagLast3Code.toString();
			else if (jobbagLast3Code > 10)
				rootJobbagNo = jobbag.getJobBagNo().substring(0,
						jobbag.getJobBagNo().length() - 3)
						+ "0" + jobbagLast3Code.toString();
			else
				rootJobbagNo = jobbag.getJobBagNo().substring(0,
						jobbag.getJobBagNo().length() - 3)
						+ "00" + jobbagLast3Code.toString();
			*/
			
			JobBag rootJobbag = this.findById(rootJobbagNo);

			// . 全部只分成1 批
			// . 若 accounts <10000 ==>Pages Seq. = 1 ~ 工單.頁數 , Account Seq. = 1
			// ~ 工單.accounts
			// else 全部留空白

			// JobBagSplite newJobBagSplite = new JobBagSplite(); //新產生的
			// JobBagSplite
			// = new JobBagSplite(); //目前的 JobBagSplite

			// Step1:
			// 根據 currentJobBagSplite 複製出 新的 newJobBagSplite
			// newJobBagSplite jobbagNo 指回root jobbag
			Set<JobBagSplite> jobBagSplites = jobbag.getJobBagSplites();
			Iterator iterator = jobBagSplites.iterator();
			while (iterator.hasNext()) {
				JobBagSplite currentJobBagSplite = (JobBagSplite) iterator
						.next();
				currentJobBagSplite.setJobBag(null); // 切斷 currentJobBagSplite 跟
														// JobBag 關係
				jobbagSpliteService.save(currentJobBagSplite);
			}

			// java.util.Date defaultValue = null;
			// DateConverter converter = new DateConverter(defaultValue);
			// ConvertUtils.register(converter, java.util.Date.class);
			// BeanUtils.copyProperties(newJobBagSplite,currentJobBagSplite);

			// newJobBagSplite.setJobBag(rootJobbag); //將新JobBagSplite 的 JobBag
			// 指到 rootJobbag
			// newJobBagSplite.setJobBagSpliteNo(rootJobbag.getJobBagNo() +
			// jobbag.getLogFileSeq());//RESET newJobBagSplite setJobBagSpliteNo
			// jobbagSpliteService.save(newJobBagSplite);

			// Step2: 修正 root 相關數據
			// 合計計算 rootJobbag account/pages/sheets.... 等資訊
			Integer accounts = jobbag.getAccounts() + rootJobbag.getAccounts();
			Integer pages = jobbag.getPages() + rootJobbag.getPages();
			Integer sheets = jobbag.getSheets() + rootJobbag.getSheets();
			// Integer spliteCount = jobbag.getSpliteCount() +
			// rootJobbag.getSpliteCount();
			Integer feeder2 = jobbag.getFeeder2() + rootJobbag.getFeeder2();
			Integer feeder3 = jobbag.getFeeder3() + rootJobbag.getFeeder3();
			Integer feeder4 = jobbag.getFeeder4() + rootJobbag.getFeeder4();
			Integer feeder5 = jobbag.getFeeder5() + rootJobbag.getFeeder5();
			Integer tray1 = jobbag.getTray1() + rootJobbag.getTray1();
			Integer tray2 = jobbag.getTray2() + rootJobbag.getTray2();
			Integer tray3 = jobbag.getTray3() + rootJobbag.getTray3();
			Integer tray4 = jobbag.getTray4() + rootJobbag.getTray4();
			Integer tray5 = jobbag.getTray5() + rootJobbag.getTray5();
			Integer tray6 = jobbag.getTray6() + rootJobbag.getTray6();
			Integer tray7 = jobbag.getTray7() + rootJobbag.getTray7();
			Integer tray8 = jobbag.getTray8() + rootJobbag.getTray8();
			Integer p1accounts = jobbag.getP1accounts()
					+ rootJobbag.getP1accounts();
			Integer p2accounts = jobbag.getP2accounts()
					+ rootJobbag.getP2accounts();
			Integer p3accounts = jobbag.getP3accounts()
					+ rootJobbag.getP3accounts();
			Integer p4accounts = jobbag.getP4accounts()
					+ rootJobbag.getP4accounts();
			Integer p5accounts = jobbag.getP5accounts()
					+ rootJobbag.getP5accounts();
			Integer p6accounts = jobbag.getP6accounts()
					+ rootJobbag.getP6accounts();
			Integer pxaccounts = jobbag.getPxaccounts()
					+ rootJobbag.getPxaccounts();

			rootJobbag.setAccounts(accounts);
			rootJobbag.setPages(pages);
			rootJobbag.setSheets(sheets);
			rootJobbag.setSpliteCount(1);
			
			rootJobbag.setFeeder2(feeder2);
			rootJobbag.setFeeder3(feeder3);
			rootJobbag.setFeeder4(feeder4);
			rootJobbag.setFeeder5(feeder5);
			rootJobbag.setTray1(tray1);
			rootJobbag.setTray2(tray2);
			rootJobbag.setTray3(tray3);
			rootJobbag.setTray4(tray4);
			rootJobbag.setTray5(tray5);
			rootJobbag.setTray6(tray6);
			rootJobbag.setTray7(tray7);
			rootJobbag.setTray8(tray8);
			rootJobbag.setP1accounts(p1accounts);
			rootJobbag.setP2accounts(p2accounts);
			rootJobbag.setP3accounts(p3accounts);
			rootJobbag.setP4accounts(p4accounts);
			rootJobbag.setP5accounts(p5accounts);
			rootJobbag.setP6accounts(p6accounts);
			rootJobbag.setPxaccounts(pxaccounts);

			

			// Step3: 刪除 current jobbag,
			this.delete(jobbag);
			
			this.save(rootJobbag);

			// Step4: 調整 JobBagSplite init 數據, 若 accounts <10000 ==>Pages Seq. =
			// 1 ~ 工單.頁數 , Account Seq. = 1 ~ 工單.accounts
			Set<JobBagSplite> rootJobbagSplites = rootJobbag.getJobBagSplites();
			iterator = rootJobbagSplites.iterator();
			while (iterator.hasNext()) {
				JobBagSplite rootJobbagSplite = (JobBagSplite) iterator.next();
				if (rootJobbag.getPages() < 10000
						&& rootJobbag.getAccounts() > 0) {
					rootJobbagSplite.setLpPagesSeqBegin(1);
					rootJobbagSplite.setLpPagesSeqEnd(rootJobbag.getPages());
					rootJobbagSplite.setLpPagesSeqDiff(rootJobbag.getPages());
					rootJobbagSplite.setLpAccountSeqBegin(1);
					rootJobbagSplite.setLpAccountSeqEnd(rootJobbag
							.getAccounts());
					rootJobbagSplite.setLpAccountSeqDiff(rootJobbag
							.getAccounts());
				} else {
					rootJobbagSplite.setLpPagesSeqBegin(null);
					rootJobbagSplite.setLpPagesSeqEnd(null);
					rootJobbagSplite.setLpPagesSeqDiff(null);
					rootJobbagSplite.setLpAccountSeqBegin(null);
					rootJobbagSplite.setLpAccountSeqEnd(null);
					rootJobbagSplite.setLpAccountSeqDiff(null);
				}

			}

		} catch (Exception e) {
			log.error("", e);
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;

		}

		return true;
	}

	/**
	 * 產生DM 類的jobbag
	 */
	public Boolean createDMJob() {
		// TODO Auto-generated method stub

		// 取得系統日期
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumIntegerDigits(2);
		nf.setMinimumIntegerDigits(2);
		
		Date today = calendar.getTime();		
		Criteria criteria = HibernateSessionFactory.getSession().createCriteria(Code.class);
		//取得Transfer type的對映map
		List<Code> list = criteria.add(Restrictions.eq("codeTypeName", "TRANSFER_TYPE")).list();
		HashMap<Integer, String> codeMap = new HashMap<Integer, String>();
		for(Code code : list){
			codeMap.put(code.getId(), code.getCodeKey());
		}
		
		//取得上個非假日
		criteria = HibernateSessionFactory.getSession().createCriteria(Holiday.class);
	    List<Holiday> holidayList = criteria.add(Restrictions.lt("date", today)).add(Restrictions.eq("isHoliday", false)).addOrder(Order.desc("date")).setMaxResults(1).list();
	    Date lastNotHoliday = holidayList.get(0).getDate();
	    
	    //判斷今天是不是假日
	    criteria = HibernateSessionFactory.getSession().createCriteria(Holiday.class);
	    holidayList = criteria.add(Restrictions.eq("date", today)).list();
	    boolean todayIsHoliday = holidayList.get(0).getIsHoliday();
	    
	    	    
	    Date yesterday = new Date(today.getTime() - 24 * 60 * 60 * 1000);
	    Date twoDaysBefore = new Date(today.getTime() - 2 * 24 * 60 * 60 * 1000);         		          
	    
		
		boolean isMonthEnd = false;

		//今天日期，判斷是不是月底
		int todayDate = calendar.get(Calendar.DAY_OF_MONTH);
		int monthEnd = calendar.getActualMaximum(Calendar.DATE);
		if(todayDate == monthEnd)
			isMonthEnd = true;
		//今天星期幾
		Integer weekDay = calendar.get(Calendar.DAY_OF_WEEK);
		String todayString = nf.format(todayDate);

		try {

			// 建立工單
			HibernateSessionFactory.getSession().clear();
			HibernateSessionFactory.getSession().getTransaction().begin();

			String queryString = " select {j.*}  " + " from job_code j, code c where j.transfer_type = c.id and c.CODE_TYPE_NAME = 'TRANSFER_TYPE' and ";
			
		    String whereString = null;
		    
		    if(!isMonthEnd){
		        whereString = "  j.CREATE_BY_CYCLE = 1 and ((c.code_key in ('NONE', 'BY_EMAIL', 'ONE_TIME', 'CYCLE_DATE') and (j.CYCLE" + todayString
					   + "=1 or ( j.selectByWeek = 1 and j.weekDay" + weekDay + " = 1)))";
		    }else{
		    	whereString = "  j.CREATE_BY_CYCLE = 1 and ((c.code_key in ('NONE', 'BY_EMAIL', 'ONE_TIME', 'CYCLE_DATE') and (j.CYCLE" + todayString
				   + "=1 or j.cycleEnd = 1 or (j.selectByWeek = 1 and j.weekDay" + weekDay + " = 1)))";
		    }
		    
		    //working_day，非假日時才產生
		    if(!todayIsHoliday){
		    	if(!isMonthEnd){
			        whereString += "  or (c.code_key = 'WORKING_DAY' and (j.CYCLE" + todayString
						   + "=1 or ( j.selectByWeek = 1 and j.weekDay" + weekDay + " = 1)))";
			    }else{
			    	whereString += "  or (c.code_key = 'WORKING_DAY' and (j.CYCLE" + todayString
					   + "=1 or j.cycleEnd = 1 or (j.selectByWeek = 1 and j.weekDay" + weekDay + " = 1)))";
			    }
		    }
		    
		    //next_date
		    Calendar yesterdayCal = Calendar.getInstance();
		    yesterdayCal.setTime(yesterday);
		    monthEnd = calendar.getActualMaximum(Calendar.DATE);
			if(yesterdayCal.get(Calendar.DATE) == monthEnd)
				isMonthEnd = true;
			else
				isMonthEnd = false;
			if(!isMonthEnd){
				int date = yesterdayCal.get(Calendar.DATE);
		    	int dayOfWeek = yesterdayCal.get(Calendar.DAY_OF_WEEK);
		        whereString += " or (c.code_key = 'NEXT_DATE' and (j.CYCLE" + nf.format(date)
					   + "=1 or ( j.selectByWeek = 1 and j.weekDay" + dayOfWeek + " = 1)))";		        
		        	
		    }else{
		    	int date = yesterdayCal.get(Calendar.DATE);
		    	int dayOfWeek = yesterdayCal.get(Calendar.DAY_OF_WEEK);
		    	whereString += " or (c.code_key = 'NEXT_DATE' and (j.CYCLE" + nf.format(date)
				   + "=1 or j.cycleEnd = 1 or (j.selectByWeek = 1 and j.weekDay" + dayOfWeek + " = 1)))";
		    }
		    
		    
		    //combine_sunday，星期天不產生，如果星期一時是前天
		    if(weekDay == 2){
		    	Calendar cal = Calendar.getInstance();
		    	cal.setTime(twoDaysBefore);
		    	//前天的日期和星期幾
		    	int date = cal.get(Calendar.DATE);
		    	int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		    	monthEnd = cal.getActualMaximum(Calendar.DATE);
				if(date == monthEnd)
					isMonthEnd = true;
				else
					isMonthEnd = false;
		    	if(!isMonthEnd)
		    	    whereString += " or (c.code_key = 'COMBINE_SUNDAY' and (j.CYCLE" + nf.format(date)
				        + "=1 or ( j.selectByWeek = 1 and j.weekDay" + dayOfWeek + " = 1)))";
		    	else
		    		whereString += " or (c.code_key = 'COMBINE_SUNDAY' and (j.CYCLE" + nf.format(date)
			            + "=1 or j.cycleEnd = 1 or ( j.selectByWeek = 1 and j.weekDay" + dayOfWeek + " = 1)))";
		    }else if(weekDay != 1){
		    	Calendar cal = Calendar.getInstance();
		    	cal.setTime(yesterday);
		    	//昨天的日期和星期幾
		    	int date = cal.get(Calendar.DATE);
		    	int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		    	monthEnd = cal.getActualMaximum(Calendar.DATE);
				if(date == monthEnd)
					isMonthEnd = true;
				else
					isMonthEnd = false;
		    	if(!isMonthEnd)
		    	    whereString += " or (c.code_key = 'COMBINE_SUNDAY' and (j.CYCLE" + nf.format(date)
				        + "=1 or ( j.selectByWeek = 1 and j.weekDay" + dayOfWeek + " = 1)))";
		    	else
		    		whereString += " or (c.code_key = 'COMBINE_SUNDAY' and (j.CYCLE" + nf.format(date)
			            + "=1 or j.cycleEnd = 1 or ( j.selectByWeek = 1 and j.weekDay" + dayOfWeek + " = 1)))";
		    }
		    
		    //combine_holiday，非假日時才產生
		    if(!todayIsHoliday){
		    	Calendar cal = Calendar.getInstance();
		    	cal.setTime(lastNotHoliday);	
		    	//上一個非假日到昨天的日期和星期幾
		    	while(today.getTime() > cal.getTimeInMillis()){		    	   
		    	   int date = cal.get(Calendar.DATE);		    	
		    	   int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		    	   monthEnd = cal.getActualMaximum(Calendar.DATE);
				   if(date == monthEnd)
					   isMonthEnd = true;
				   else
					   isMonthEnd = false;
		    	   if(!isMonthEnd)
		    	       whereString += " or (c.code_key = 'COMBINE_HOLIDAY' and (j.CYCLE" + nf.format(date)
				           + "=1 or ( j.selectByWeek = 1 and j.weekDay" + dayOfWeek + " = 1)))";
		    	   else
		    		   whereString += " or (c.code_key = 'COMBINE_HOLIDAY' and (j.CYCLE" + nf.format(date)
			               + "=1 or j.cycleEnd = 1 or ( j.selectByWeek = 1 and j.weekDay" + dayOfWeek + " = 1)))";
		    	   cal.add(Calendar.DATE, 1);
		    	}
		    }

		    
		    
		    queryString += (whereString + ")");

			SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession()
					.createSQLQuery(queryString);

			sqlQuery.addEntity("j", JobCode.class);

			List jobcodeList = sqlQuery.list();

			for (int i = 0; i < jobcodeList.size(); i++) {
				JobCode jobcode = (JobCode) jobcodeList.get(i);								
				Date cycleDate = today;
				String 	transferType = codeMap.get(jobcode.getTransferType());
				//判斷各種不同傳檔類型的cycle date
				if("COMBINE_HOLIDAY".equals(transferType)){
					cycleDate = lastNotHoliday;
				}else if("NEXT_DATE".equals(transferType)){
					cycleDate = yesterday;
				}else if("COMBINE_SUNDAY".equals(transferType) && weekDay != 2){
					cycleDate = yesterday;
				}else if("COMBINE_SUNDAY".equals(transferType) && weekDay == 2){
					cycleDate = twoDaysBefore;
				}
				
				jobcode.setLogFileSeq("001"); // 固定壓 001
				JobBag jobbag = this.createNewJobBag(jobcode, 0, 0, 0, "",
						cycleDate, calendar.getTime(), "", 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, "", 0, 0, 0, 0, 0, 0, 0);
				if(jobbag != null){
				   jobbag.setCreateByCycle(true); // 週期性工單
				   this.save(jobbag);

				   SysLog syslog = new SysLog();
				   syslog.setLogType("CREATE_JOBBAG_DM");
				   syslog.setSubject("產生周期性的jobbag ");
				   syslog.setMessageBody(jobbag.getJobBagNo() + ":"
						   + jobcode.getJobCodeNo() + ":" + sdf.format(cycleDate) + ":" + transferType);

				   syslog.setCreateDate(new Date());
				   syslogService.save(syslog);
				}
			}

			HibernateSessionFactory.getSession().getTransaction().commit();
		} catch (Exception re) {
			log.error("", re);
			HibernateSessionFactory.getSession().getTransaction().rollback();
			return null;
		}finally{
			HibernateSessionFactory.closeSession();
		}

		return true;
	}

	// 批次更新工單狀態，已進入account計算的不更新
	public Boolean updateJobStatus(String jobBagList, String jobbagStatus) {
		try {
			//先把之前的狀態放到prev_status中
			String updateStringZero = " update job_bag set PREVSTATUS = JOB_BAG_STATUS where JOB_BAG_NO in (" 
					+ jobBagList + ")  and JOB_BAG_STATUS in ('PRINTED_LP', 'PRINTED_MP', 'PRINTED_LG', 'NON_MP',  'INIT', 'NON_LP', 'COMPLETED_LP', 'COMPLETED_MP')" +
							" and (JOB_BAG_STATUS <> PREVSTATUS or PREVSTATUS is null)";
			SQLQuery updateQuery = (SQLQuery) HibernateSessionFactory
			       .getSession().createSQLQuery(updateStringZero);
			
			//再批次更新狀態
			String updateString = " update job_bag set JOB_BAG_STATUS = ? where JOB_BAG_NO in ("
					+ jobBagList + ") and JOB_BAG_STATUS in ('PRINTED_LP', 'PRINTED_MP', 'PRINTED_LG', 'NON_MP',  'INIT', 'NON_LP', 'COMPLETED_LP', 'COMPLETED_MP')";
			updateQuery = (SQLQuery) HibernateSessionFactory
					.getSession().createSQLQuery(updateString);
			updateQuery.setParameter(0, jobbagStatus);
			// updateQuery.setParameter(1, jobBagList);
			int ret2 = updateQuery.executeUpdate();
		} catch (Exception re) {
			log.error("", re);
			return false;
		}
		return true;
	}

	/*
	 * Daily Logement Report
	 */
	public List findJobbagListByDailyLogementReport(String custNo,
			String queryDateBegin, String queryDateEnd) {
		String queryString = " select {j.*}"
		                    + " from job_bag j" 
		                    + " where ("
		                    + " (convert(date, j.CYCLE_DATE, 120)  between  convert(date, ?, 120)  and  convert(date, ?, 120) )"
		                    + " and (j.COMPLETED_DATE is not null"
		                    + " or (j.COMPLETED_DATE is null and j.IS_EDD = 1 and (j.mailType is null or j.mailType = ''))" 
		                    + " )) and (j.IS_DAMAGE is null or j.IS_DAMAGE = 0) and ( j.IS_DELETED is null or j.IS_DELETED = 0)";
		

		if (null != custNo && !custNo.equals("ALL"))
			queryString = queryString + " and j.idf_CUST_NO ='" + custNo + "'";

		queryString = queryString + " order by j.job_bag_no ";
		SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession()
				.createSQLQuery(queryString);
		sqlQuery.addEntity("j", JobBag.class);
		sqlQuery.setParameter(0, queryDateBegin);
		sqlQuery.setParameter(1, queryDateEnd);

		List jobbagList = sqlQuery.list();
		return jobbagList;
	}
	
	/*
	 * Daily Logement Report
	 */
	public List findJobbagListByDailyLogementReportNotCompleted(String custNo,
			String queryDateBegin, String queryDateEnd) {
		String queryString = "select {j.*} from job_bag j "
                                 + " where convert(date, j.CYCLE_DATE, 120)  between  convert(date, ?, 120)  and  convert(date, ?, 120) and (j.IS_DAMAGE is null or j.IS_DAMAGE = 0) " 
                                 + " and ( j.IS_DELETED is null or j.IS_DELETED = 0) and CODE_JOB_CODE_TYPE <> 190 "  
                                 + " and (j.COMPLETED_DATE is not null or (j.COMPLETED_DATE is null and j.ACCOUNTS is not null and j.ACCOUNTS > 0 )) ";
		if (null != custNo && !custNo.equals("ALL"))
			queryString = queryString + " and j.idf_CUST_NO ='" + custNo + "'";

		queryString = queryString + " order by j.job_bag_no ";
		SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession()
				.createSQLQuery(queryString);
		sqlQuery.addEntity("j", JobBag.class);
		sqlQuery.setParameter(0, queryDateBegin);
		sqlQuery.setParameter(1, queryDateEnd);

		List jobbagList = sqlQuery.list();
		return jobbagList;
	}

	/*
	 * DM Summary Report
	 */
	public List findJobbagListByDmSummaryReport(String custNo,
			String queryDateBegin, String queryDateEnd) {
		try {		
			String queryString = " select {j.*}  "
					+ " from job_bag j "
					+ " where convert(date, j.CYCLE_DATE, 120)  between  convert(date, ?, 120)  and  convert(date, ?, 120) and (j.IS_DAMAGE = 0 or j.IS_DAMAGE is null) "
					+ " and ( j.IS_DELETED is null or j.IS_DELETED = 0) ";
	
			if (null != custNo && !custNo.equals("ALL"))
				queryString = queryString + " and j.idf_CUST_NO ='" + custNo + "'";
	
			queryString = queryString + " order by j.job_bag_no ";		
			SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession()
					.createSQLQuery(queryString);
			sqlQuery.addEntity("j", JobBag.class);
			sqlQuery.setParameter(0, queryDateBegin);
			sqlQuery.setParameter(1, queryDateEnd);
	
			List jobbagList = sqlQuery.list();
			return jobbagList;
		} catch (Exception re) {
			log.error("", re);
			return null;
		}

	}
	
	/*
	 * get amex csv file name
	 * 取得AMEX 最近30筆 .csv 檔名
	 */
	public List findAmexCSVFileName() {
		try {			
			String queryString = " select distinct top 30 j.LOG_FILENAME logFileName, convert(varchar(10),CYCLE_DATE,120) cycleDate  "
					+ " from job_bag j  "
					+ " where j.idf_CUST_NO='AE' and j.LOG_FILENAME <> ''  and j.LOG_FILENAME not like 'logis%' and j.LOG_FILENAME is not null "
					+ " order by convert(varchar(10),CYCLE_DATE,120) desc  ";		
			SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
			sqlQuery.setResultTransformer(Transformers.aliasToBean(AmexCSVNameBean.class));
			List amexCSVNameList = sqlQuery.list();
			return amexCSVNameList;
		} catch (Exception re) {
			log.error("", re);
			return null;
		}		
	}
	
	public List<Map<String, String>> findJobBagInList(String jobBagList) {
		try {			
			String queryString = "select j.jobBagNo, j.spliteCount, c.codeValueTw from JobBag j, Code c where j.jobBagStatus = c.codeKey and j.jobBagNo in (" + jobBagList + ")";		
			Query query = HibernateSessionFactory.getSession().createQuery(queryString);
			List<Object []> list = query.list();			
			List<Map<String, String>> retList = new ArrayList<Map<String, String>>();
			if(list != null){
				for(Object [] obj : list){
					Map<String, String> retMap = new HashMap<String, String>();
					String spliteCount = obj[1] == null ? null : obj[1].toString();
					retMap.put("jobBagNo", (String)obj[0]);
					retMap.put("spliteCount", spliteCount);
					retMap.put("jobBagStatus", (String)obj[2]);
					retList.add(retMap);
				}
			}
			return retList;
		} catch (Exception re) {
			log.error("", re);
			return null;
		}finally{
			HibernateSessionFactory.closeSession();
		}		
	}
    public List<JobBag> findJobagNotDisplayQty(String jobBagList, boolean display){
    	Criteria criteria = getSession().createCriteria(JobBag.class);
    	criteria.add(Restrictions.eq("lgPList", false));    	
    	String[] jobBags = jobBagList.split(",");
		for(int i = 0 ; i < jobBags.length ; i++){
			jobBags[i] = jobBags[i].replaceAll("'", "").trim();
		}
		List<JobBag> retList = null;
		if(!display)
		   retList = criteria.add(Restrictions.in("jobBagNo", jobBags)).add(Restrictions.eq("lgDisplayQty", false)).list();
		else
		   retList = criteria.add(Restrictions.in("jobBagNo", jobBags)).add(Restrictions.or(Restrictions.eq("lgDisplayQty", true), Restrictions.isNull("lgDisplayQty"))).list();
		return retList;
		
	}	
}
