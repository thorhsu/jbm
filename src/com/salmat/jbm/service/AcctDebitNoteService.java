package com.salmat.jbm.service;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.log4j.Logger;
import org.apache.xerces.impl.dv.util.Base64;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.salmat.jbm.bean.AcctInvoiceInfo;
import com.salmat.jbm.bean.AcctSum3IncludeQty;
import com.salmat.jbm.bean.AcctSum3Info;
import com.salmat.jbm.bean.DebitNoteFullInfo;
import com.salmat.jbm.bean.MinimalChargePrice;
import com.salmat.jbm.hibernate.*;
import com.salmat.jbm.service.*;
import com.painter.filter.SetParamValFilter;
import com.painter.util.EnCryptUtils;
import com.painter.util.Global;
import com.painter.util.MailUtil;
import com.painter.util.Util;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.Integer;
import java.lang.String;
import java.net.URLEncoder;

public class AcctDebitNoteService extends AcctDnoDAO {

	/**
	 * Log4j instance.
	 */
	private final static Logger log = Logger
			.getLogger(AcctDebitNoteService.class);
	private final static AcctSectionReceiverMappingService sectionReceiverMappingService = AcctSectionReceiverMappingService
			.getInstance();
	private static final JobCodeService jobCodeService = JobCodeService
			.getInstance();
	private static final AcctSum1Service acctSum1Service = AcctSum1Service
			.getInstance();
	private static final AcctSum2Service acctSum2Service = AcctSum2Service
			.getInstance();
	private static final AcctInvoiceService acctInvoiceService = AcctInvoiceService
			.getInstance();

	/* Constants */
	private static final AcctDebitNoteService instance = new AcctDebitNoteService();

	// private static CustomerDAO CustomerDao;

	private AcctDebitNoteService() {
		// CustomerDao = new CustomerDAO();

	}

	public static AcctDebitNoteService getInstance() {
		return instance;
	}

	public List findJobBagListByDebitNote(AcctDno acctDno) {
		String queryString = " select {j.*} from job_bag j where j.job_bag_no in (select distinct jb.job_bag_no "
				+ " from acct_sum1 as1, job_bag jb where as1.idf_JOB_BAG_NO = jb.JOB_BAG_NO and as1.idf_debit_no = ?)";
		SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession()
				.createSQLQuery(queryString);
		sqlQuery.addEntity("j", JobBag.class);
		sqlQuery.setParameter(0, acctDno.getDebitNo());
		List retList = sqlQuery.list();
		HibernateSessionFactory.closeSession();

		return retList;
	}

	public List findJobBagListByDebitNote(AcctDno acctDno, String extractMode) {

		String jobbagStatus = "'COMPLETED_LG'"; // 預設只抓 jobbagStatus =
												// COMPLETED_LG 工單
		String otherCondition = " or (j.JOB_BAG_STATUS = 'COMPLETED_MP' and j.IS_LG = 0  ) "
				+ "or (j.JOB_BAG_STATUS = 'COMPLETED_LP' and j.IS_LG = 0 and (j.MP_DM_PS is null or j.MP_DM_PS = '' or j.MP_DM_PS = 'Ni')) "
				+ "or j.JOB_BAG_STATUS='EDD' ";

		if (extractMode.equalsIgnoreCase("ALL")) // 若extractMode ==ALL, 表示抓
													// jobbagStatus =
													// 'COMPLETED_LG'
													// ,'ACCOUNTING_LOCKED' 工單
			jobbagStatus = jobbagStatus + ", 'ACCOUNTING_LOCKED'";
		else if (extractMode.equalsIgnoreCase("ACCOUNTING_LOCKED")) { // 若extractMode
																		// =='ACCOUNTING_LOCKED',
																		// 表示抓
																		// jobbagStatus
																		// =
																		// 'ACCOUNTING_LOCKED'
																		// 工單
			jobbagStatus = " 'ACCOUNTING_LOCKED' ";
			otherCondition = "";
		} else if (extractMode.equalsIgnoreCase("NON_DELETE")) { // 若extractMode
																	// =='NON_DELETE',表示抓jobbagStatus
																	// =
																	// 'ACCOUNTING_LOCKED',
																	// 'ACCT_DN_GENERATED'
																	// 工單
			jobbagStatus = "'ACCOUNTING_LOCKED', 'ACCT_DN_GENERATED'";
			otherCondition = "";
		}

		try {
			// 執行Step1 表是要重抓工單, 原 sum1/sum2/sum3/acctInvoice 中, 該 debit note 資料,
			// 要先刪除
			// 如果 extractMode == NON_DELETE, 不刪除原 sum1 資料
			if (!extractMode.equalsIgnoreCase("NON_DELETE")) {
				// 只有 extractMode ==ALL, 才要刪除原 sum1 資料
				if (extractMode.equalsIgnoreCase("ALL")) {
					String deleteString1 = " delete from dbo.acct_sum1   WHERE idf_debit_no=?  ";
					SQLQuery deleteQuery1 = (SQLQuery) HibernateSessionFactory
							.getSession().createSQLQuery(deleteString1);
					deleteQuery1.setParameter(0, acctDno.getDebitNo());
					int ret1 = deleteQuery1.executeUpdate();
				}

				String deleteString2 = " delete from dbo.acct_sum2   WHERE idf_debit_no=? and ITEM_TYPE='JOB_BAG' ";
				SQLQuery deleteQuery2 = (SQLQuery) HibernateSessionFactory
						.getSession().createSQLQuery(deleteString2);
				deleteQuery2.setParameter(0, acctDno.getDebitNo());
				int ret2 = deleteQuery2.executeUpdate();

				String deleteString3 = " delete from dbo.acct_sum3   WHERE idf_debit_no=? ";
				SQLQuery deleteQuery3 = (SQLQuery) HibernateSessionFactory
						.getSession().createSQLQuery(deleteString3);
				deleteQuery3.setParameter(0, acctDno.getDebitNo());
				int ret3 = deleteQuery3.executeUpdate();

				String deleteString4 = " delete from dbo.acct_invoice   WHERE idf_debit_no=? ";
				SQLQuery deleteQuery4 = (SQLQuery) HibernateSessionFactory
						.getSession().createSQLQuery(deleteString4);
				deleteQuery4.setParameter(0, acctDno.getDebitNo());
				int ret4 = deleteQuery4.executeUpdate();
			}

			String queryString = " select {j.*} "
					+ " from job_bag j, dbo.acct_section_job_code_mapping m  "
					+ " where ( j.IS_DAMAGE = 0 or j.IS_DAMAGE is null ) and (j.JOB_BAG_STATUS in ("
					+ jobbagStatus
					+ ") "
					+ otherCondition
					+ ") "
					+ " and (IS_DELETED is null or IS_DELETED = 0) and"
					+ " j.idf_CUST_NO = m.idf_CUST_NO and j.idf_JOB_CODE_NO = m.idf_JOB_CODE_NO and "
					+ " m.idf_CUST_NO =? " + " and m.section =?";

			if (null != acctDno.getDesSdt() && null != acctDno.getDesEdt()
					&& acctDno.getDesEdt().length() > 0
					&& acctDno.getDesSdt().length() > 0)
				queryString += " and convert(date, j.COMPLETED_DATE , 120)  between  convert(date, ?, 120)  and  convert(date, ?, 120) ";

			if (null != acctDno.getCycleSdt() && null != acctDno.getCycleEdt()
					&& acctDno.getCycleSdt().length() > 0
					&& acctDno.getCycleEdt().length() > 0)
				queryString = queryString
						+ " and convert(date, j.CYCLE_DATE , 120)  between  convert(date, ?, 120)  and  convert(date, ?, 120) ";

			SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession()
					.createSQLQuery(queryString);

			sqlQuery.addEntity("j", JobBag.class);
			sqlQuery.setParameter(0, acctDno.getCustomer().getCustNo());
			sqlQuery.setParameter(1, acctDno.getSection());
			int counter = 2;
			if (null != acctDno.getDesSdt() && null != acctDno.getDesEdt()
					&& acctDno.getDesEdt().length() > 0
					&& acctDno.getDesSdt().length() > 0) {
				sqlQuery.setParameter(counter, acctDno.getDesSdt());
				counter++;
				sqlQuery.setParameter(counter, acctDno.getDesEdt());
				counter++;
			}
			if (null != acctDno.getCycleSdt() && null != acctDno.getCycleEdt()
					&& acctDno.getCycleSdt().length() > 0
					&& acctDno.getCycleEdt().length() > 0) {
				sqlQuery.setParameter(counter, acctDno.getCycleSdt());
				counter++;
				sqlQuery.setParameter(counter, acctDno.getCycleEdt());
			}

			List retList = sqlQuery.list();
			HibernateSessionFactory.closeSession();

			return retList;
		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}
	}

	public List findJobCodeChargeItemListByJobBag(JobBag jobbag) {
		try {
			String queryString = " select {ji.*} from dbo.acct_job_code_charge_item ji, acct_customer_contract acc "
					+ " where ji.idf_JOB_CODE_NO = ? and ji.idf_acct_customer_contract = acc.id and acc.contact_date_begin <= ? and ? <= acc.contact_date_end ";

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date cycleDate = jobbag.getCycleDate();
			// 因為acc.contact_date_end也是00:00:00.000，所以要全設為00:00:00不然結尾日會有誤差
			String cycleDateStr = null;
			if (cycleDate != null) {
				cycleDateStr = sdf.format(cycleDate) + " 00:00:00.000";
			} else {
				cycleDateStr = sdf.format(new Date()) + " 00:00:00.000";
			}

			SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession()
					.createSQLQuery(queryString);
			sqlQuery.addEntity("ji", AcctJobCodeChargeItem.class);
			sqlQuery.setParameter(0, jobbag.getJobCode().getJobCodeNo());
			sqlQuery.setParameter(1, cycleDateStr);
			sqlQuery.setParameter(2, cycleDateStr);

			List retList = sqlQuery.list();
			return retList;

		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}
	}

	public int jobBagConfirmToEP1(AcctDno acctDno) {
		// job_bag_status = ACCOUNTING_EP1
		// prevStatus = job_bag_status
		String updateStr = "update job_bag set prevstatus = job_bag_status, job_bag_status = 'ACCOUNTING_EP1' "
				+ "where job_bag_no in ("
				+ "select distinct j.job_bag_no from acct_sum1 as1, job_bag j where j.JOB_BAG_NO = as1.IDF_JOB_BAG_NO and as1.idf_debit_no = ?"
				+ ")";
		SQLQuery insertQuery = (SQLQuery) HibernateSessionFactory.getSession()
				.createSQLQuery(updateStr);
		insertQuery.setParameter(0, acctDno.getDebitNo());
		return insertQuery.executeUpdate();
	}

	public boolean generateSum2(AcctDno acctDno) {
		try {

			// 執行Step4, 產生 sum2 , 原來sum2/sum3/invoice 中, 該 debit note 資料, 要先刪除
			String deleteString2 = " delete from dbo.acct_sum2   WHERE idf_debit_no=? and ITEM_TYPE='JOB_BAG' ";
			SQLQuery deleteQuery2 = (SQLQuery) HibernateSessionFactory
					.getSession().createSQLQuery(deleteString2);
			deleteQuery2.setParameter(0, acctDno.getDebitNo());
			int ret2 = deleteQuery2.executeUpdate();

			String deleteString3 = " delete from dbo.acct_sum3   WHERE idf_debit_no=?  ";
			SQLQuery deleteQuery3 = (SQLQuery) HibernateSessionFactory
					.getSession().createSQLQuery(deleteString3);
			deleteQuery3.setParameter(0, acctDno.getDebitNo());
			int ret3 = deleteQuery3.executeUpdate();

			String deleteString4 = " delete from dbo.acct_invoice   WHERE idf_debit_no=? ";
			SQLQuery deleteQuery4 = (SQLQuery) HibernateSessionFactory
					.getSession().createSQLQuery(deleteString4);
			deleteQuery4.setParameter(0, acctDno.getDebitNo());
			int ret4 = deleteQuery4.executeUpdate();

			// 從sum1 以cost_center 為統計單位, 產生 sum2 -- s.is_minimal_charge is null
			// 無最低收費項目
			String insertString = " INSERT INTO dbo.acct_sum2 (   idf_cust_no  ,idf_debit_no  ,cost_center  ,autoCostCenter ,idf_JOB_CODE_NO   , PROG_NM ,ITEM_TYPE     ,item_name  ,item_name_tw  ,title  ,sub_title  ,EP1_account_code ,idf_acct_charge_item   ,idf_acct_customer_contract ,unit_price     ,sum_qty , total     ) "
					+ " select  s.idf_cust_no , s.idf_debit_no ,s.cost_center, s.autoCostCenter,  s.idf_JOB_CODE_NO , s.PROG_NM , 'JOB_BAG' ITEM_TYPE , s.item_name , s.item_name_tw, s.title, s.sub_title, s.EP1_account_code ,s.idf_acct_charge_item, s.idf_acct_customer_contract, s.unit_price, sum(cast(isnull(s.sum_qty,0)   as   numeric ))  sum_qty, s.unit_price * sum(cast(isnull(s.sum_qty,0)   as   numeric )) total     "
					+ " from dbo.acct_sum1 s "
					+ " where s.idf_debit_no =? and s.is_minimal_charge is null "
					+ " group by s.idf_cust_no, s.idf_debit_no ,s.cost_center, s.autoCostCenter,  s.idf_JOB_CODE_NO, s.PROG_NM,  s.item_name , s.item_name_tw, s.title, s.sub_title, s.EP1_account_code ,s.idf_acct_charge_item, s.idf_acct_customer_contract, s.unit_price ";

			SQLQuery insertQuery = (SQLQuery) HibernateSessionFactory
					.getSession().createSQLQuery(insertString);
			insertQuery.setParameter(0, acctDno.getDebitNo());
			int ret1 = insertQuery.executeUpdate();

			// 有最低收費項目 , 將 minimal_charge_price 拆至各項去
			// 從sum1 以cost_center 為統計單位, 產生 sum2 -- s.is_minimal_charge ==1
			String queryString = " select {s.*} "
					+ " from acct_sum1 s "
					+ " where s.idf_debit_no =? and s.is_minimal_charge = 1 and s.EP1_account_code != '0' ";

			SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession()
					.createSQLQuery(queryString);
			sqlQuery.addEntity("s", AcctSum1.class);
			sqlQuery.setParameter(0, acctDno.getDebitNo());

			List retList = sqlQuery.list();

			Double[][] subTotal = new Double[100][2];
			Double total = 0.0;
			List minimalChargePriceList = new ArrayList();
			String nextJobBagNo = "";
			Integer xCounter = -1;
			Integer chargeItemCounter = 0;
			// MinimalChargePrice minimalChargePrice = null;
			Double minimalChargePrice = 0.0;
			for (int i = 0; i < retList.size(); i++) {
				AcctSum1 acctSum1 = (AcctSum1) retList.get(i);
				JobCode _jobCode = jobCodeService.findById(acctSum1
						.getIdfJobCodeNo());
				minimalChargePrice = _jobCode.getMinimalChargePrice();

				subTotal[i][0] = acctSum1.getUnitPrice() * acctSum1.getSumQty();
				subTotal[i][1] = new Double(acctSum1.getId());

				total += acctSum1.getUnitPrice() * acctSum1.getSumQty();

				if (i != retList.size() - 1) {
					nextJobBagNo = ((AcctSum1) retList.get(i + 1))
							.getIdfJobBagNo();
				}
				// 當下一個 JobBagNo 不同時 or 最後一筆時 , 要根據 JobBagNo 來拆帳
				if (i == retList.size() - 1
						|| !acctSum1.getIdfJobBagNo().equalsIgnoreCase(
								nextJobBagNo)) {
					Double _total = 0.0;// 前N-1 項合計金額
					for (int j = i - chargeItemCounter; j < i; j++) {
						subTotal[j][0] = subTotal[j][0] / total
								* minimalChargePrice;
						Integer subTotalInt = (int) Math.round(subTotal[j][0]); // 取整數
						subTotal[j][0] = new Double(subTotalInt);
						_total += subTotal[j][0];
					}
					subTotal[i][0] = minimalChargePrice - _total; // 最後一項合計金額, 為
																	// minimalChargePrice
																	// - 前N-1
																	// 項合計金額
					chargeItemCounter = 0;
					total = 0.0;
				} else {
					chargeItemCounter++;
				}

			}
			for (int i = 0; i < retList.size(); i++) {

				AcctSum1 acctSum1 = (AcctSum1) retList.get(i);
				AcctSum2 acctSum2 = new AcctSum2();
				acctSum2.setCustomer(acctSum1.getCustomer());
				acctSum2.setAcctDno(acctSum1.getAcctDno());
				acctSum2.setCostCenter(acctSum1.getCostCenter());
				acctSum2.setAutoCostCenter(acctSum1.getAutoCostCenter());
				acctSum2.setIdfJobCodeNo(acctSum1.getIdfJobCodeNo());
				acctSum2.setItemType("JOB_BAG");
				acctSum2.setAcctChargeItem(acctSum1.getAcctChargeItem());
				acctSum2.setAcctCustomerContract(acctSum1
						.getAcctCustomerContract());
				acctSum2.setItemName(acctSum1.getItemName());
				acctSum2.setItemNameTw(acctSum1.getItemNameTw());
				acctSum2.setTitle(acctSum1.getTitle());
				acctSum2.setSubTitle(acctSum1.getSubTitle());
				acctSum2.setEp1AccountCode(acctSum1.getEp1AccountCode());
				acctSum2.setUnitPrice(acctSum1.getUnitPrice());
				Double _sumQty = new Double(acctSum1.getSumQty());
				acctSum2.setSumQty(_sumQty);
				for (int k = 0; k < retList.size(); k++) {
					if (subTotal[k][1].compareTo(new Double(acctSum1.getId())) == 0)
						acctSum2.setTotal(subTotal[k][0]);
				}
				acctSum2.setIsMinimalCharge(true);
				acctSum2Service.save(acctSum2);
			}
			return true;
		} catch (RuntimeException re) {
			log.error("", re);
			return false;
		}
	}

	public boolean generateSum3(AcctDno acctDno) {
		try {

			// 執行Step7, 產生 sum7 , 原來sum3 中, 該 debit note 資料, 要先刪除
			String deleteString3 = " delete from dbo.acct_sum3   WHERE idf_debit_no=?  ";
			SQLQuery deleteQuery3 = (SQLQuery) HibernateSessionFactory
					.getSession().createSQLQuery(deleteString3);
			deleteQuery3.setParameter(0, acctDno.getDebitNo());
			int ret3 = deleteQuery3.executeUpdate();

			// 從sum2 以cost_center title/sub title 為統計單位, 產生 sum3
			// 若有 最低收費, 則產生sum3 時, purpose 要區分出
			// DISPLAY_ONLY: , 報表可顯示, 不轉 EP1
			// TO_EP1: , 報表不顯示, 轉EP1

			String queryString = " select {s.*} " + " from acct_sum2 s "
					+ " where s.idf_debit_no =? and s.is_minimal_charge = 1  ";

			SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession()
					.createSQLQuery(queryString);
			sqlQuery.addEntity("s", AcctSum2.class);
			sqlQuery.setParameter(0, acctDno.getDebitNo());

			List retList = sqlQuery.list();
			Boolean isMinimalCharge = false;
			String purpose = "";
			if (null != retList && retList.size() > 0)
				isMinimalCharge = true;

			String insertString = "";
			if (!isMinimalCharge) {
				purpose = "";

				insertString = " INSERT INTO dbo.acct_sum3 (    idf_cust_no  ,idf_debit_no  ,title  ,subtitle  ,sum_qty   ,unit_price   ,total   ,ep1acccd , purpose, display_title, idf_acct_charge_item, print_code  )  "
						+ " select s2.idf_cust_no, idf_debit_no, s2.title, s2.sub_title, sum(s2.sum_qty) sum_qty, s2.unit_price, sum( s2.total ) total ,  s2.EP1_account_code, '"
						+ purpose
						+ "' purpose, acc.report_title, s2.idf_acct_charge_item, s2.print_code  "
						+ " from dbo.acct_sum2 s2 left join acct_customer_contract acc  on s2.idf_acct_customer_contract = acc.id "
						// +
						// " where s2.idf_debit_no =? and s2.is_minimal_charge is null and (s2.autoCostCenter is null or s2.autoCostCenter = 1) "
						+ " where s2.idf_debit_no =? and s2.is_minimal_charge is null "
						+ " group by s2.idf_cust_no, idf_debit_no, s2.title, s2.sub_title, s2.unit_price,  s2.EP1_account_code, acc.report_title, s2.idf_acct_charge_item, s2.print_code  ";

				SQLQuery insertQuery = (SQLQuery) HibernateSessionFactory
						.getSession().createSQLQuery(insertString);
				insertQuery.setParameter(0, acctDno.getDebitNo());
				int ret1 = insertQuery.executeUpdate();
				/*
				 * insertString =
				 * " INSERT INTO dbo.acct_sum3 (    idf_cust_no  ,idf_debit_no  ,title  ,subtitle  ,sum_qty   ,unit_price   ,total   ,ep1acccd , purpose, display_title, idf_acct_charge_item, print_code, cost_center  )  "
				 * +
				 * " select s2.idf_cust_no, idf_debit_no, s2.title, s2.sub_title, sum(s2.sum_qty) sum_qty, s2.unit_price, sum( s2.total ) total ,  s2.EP1_account_code, '"
				 * + purpose +
				 * "' purpose, acc.report_title, s2.idf_acct_charge_item, s2.print_code, s2.cost_center  "
				 * +
				 * " from dbo.acct_sum2 s2 left join acct_customer_contract acc  on s2.idf_acct_customer_contract = acc.id "
				 * +
				 * " where s2.idf_debit_no =? and s2.is_minimal_charge is null and  s2.autoCostCenter = 0 "
				 * +
				 * " group by s2.idf_cust_no, idf_debit_no, s2.title, s2.sub_title, s2.unit_price,  s2.EP1_account_code, acc.report_title, s2.idf_acct_charge_item, s2.print_code, , s2.cost_center  "
				 * ; insertQuery = (SQLQuery) HibernateSessionFactory
				 * .getSession().createSQLQuery(insertString);
				 * insertQuery.setParameter(0, acctDno.getDebitNo()); ret1 =
				 * insertQuery.executeUpdate();
				 */
			} else {
				purpose = "DISPLAY_ONLY";
				insertString = " INSERT INTO dbo.acct_sum3 (    idf_cust_no  ,idf_debit_no  ,title  ,subtitle  ,sum_qty   ,unit_price   ,total   ,ep1acccd , purpose, display_title, idf_acct_charge_item, print_code   )  "
						+ " select s2.idf_cust_no, idf_debit_no, s2.title, s2.sub_title, sum(s2.sum_qty) sum_qty, s2.unit_price, sum( s2.total ) total ,  s2.EP1_account_code, '"
						+ purpose
						+ "' purpose, acc.report_title, s2.idf_acct_charge_item, s2.print_code  "
						+ " from dbo.acct_sum2 s2 left join acct_customer_contract acc  on s2.idf_acct_customer_contract = acc.id "
						// +
						// " where s2.idf_debit_no =? and s2.is_minimal_charge is null and (s2.autoCostCenter is null or s2.autoCostCenter = 1) "
						+ " where s2.idf_debit_no =? and s2.is_minimal_charge is null "
						+ " group by s2.idf_cust_no, idf_debit_no, s2.title, s2.sub_title, s2.unit_price,  s2.EP1_account_code, acc.report_title, s2.idf_acct_charge_item, s2.print_code  ";

				SQLQuery insertQuery = (SQLQuery) HibernateSessionFactory
						.getSession().createSQLQuery(insertString);
				insertQuery.setParameter(0, acctDno.getDebitNo());
				int ret1 = insertQuery.executeUpdate();

				purpose = "TO_EP1";
				insertString = " INSERT INTO dbo.acct_sum3 (    idf_cust_no  ,idf_debit_no  ,title  ,subtitle  ,sum_qty   ,unit_price   ,total   ,ep1acccd , purpose, display_title, idf_acct_charge_item, print_code )  "
						+ " select s2.idf_cust_no, idf_debit_no, s2.title, s2.sub_title, sum(s2.sum_qty) sum_qty, s2.unit_price, sum( s2.total ) total ,  s2.EP1_account_code, '"
						+ purpose
						+ "' purpose, acc.report_title, s2.idf_acct_charge_item, s2.print_code   "
						+ " from dbo.acct_sum2 s2 left join acct_customer_contract acc  on s2.idf_acct_customer_contract = acc.id "
						// +
						// " where s2.idf_debit_no =? and s2.EP1_account_code !='0' and (s2.autoCostCenter is null or s2.autoCostCenter = 1) "
						+ " where s2.idf_debit_no =? and s2.EP1_account_code !='0' "
						+ " group by s2.idf_cust_no, idf_debit_no, s2.title, s2.sub_title, s2.unit_price,  s2.EP1_account_code, acc.report_title, s2.idf_acct_charge_item, s2.print_code  ";

				insertQuery = (SQLQuery) HibernateSessionFactory.getSession()
						.createSQLQuery(insertString);
				insertQuery.setParameter(0, acctDno.getDebitNo());
				ret1 = insertQuery.executeUpdate();
				/*
				 * purpose = "DISPLAY_ONLY"; insertString =
				 * " INSERT INTO dbo.acct_sum3 (    idf_cust_no  ,idf_debit_no  ,title  ,subtitle  ,sum_qty   ,unit_price   ,total   ,ep1acccd , purpose, display_title, idf_acct_charge_item, print_code, cost_center   )  "
				 * +
				 * " select s2.idf_cust_no, idf_debit_no, s2.title, s2.sub_title, sum(s2.sum_qty) sum_qty, s2.unit_price, sum( s2.total ) total ,  s2.EP1_account_code, '"
				 * + purpose +
				 * "' purpose, acc.report_title, s2.idf_acct_charge_item, s2.print_code, s2.cost_center  "
				 * +
				 * " from dbo.acct_sum2 s2 left join acct_customer_contract acc  on s2.idf_acct_customer_contract = acc.id "
				 * +
				 * " where s2.idf_debit_no =? and s2.is_minimal_charge is null and  s2.autoCostCenter = 0 "
				 * +
				 * " group by s2.idf_cust_no, idf_debit_no, s2.title, s2.sub_title, s2.unit_price,  s2.EP1_account_code, acc.report_title, s2.idf_acct_charge_item, s2.print_code, s2.cost_center   "
				 * ; insertQuery = (SQLQuery) HibernateSessionFactory
				 * .getSession().createSQLQuery(insertString);
				 * insertQuery.setParameter(0, acctDno.getDebitNo()); ret1 =
				 * insertQuery.executeUpdate();
				 * 
				 * 
				 * purpose = "TO_EP1"; insertString =
				 * " INSERT INTO dbo.acct_sum3 (    idf_cust_no  ,idf_debit_no  ,title  ,subtitle  ,sum_qty   ,unit_price   ,total   ,ep1acccd , purpose, display_title, idf_acct_charge_item, print_code, cost_center )  "
				 * +
				 * " select s2.idf_cust_no, idf_debit_no, s2.title, s2.sub_title, sum(s2.sum_qty) sum_qty, s2.unit_price, sum( s2.total ) total ,  s2.EP1_account_code, '"
				 * + purpose +
				 * "' purpose, acc.report_title, s2.idf_acct_charge_item, s2.print_code, s2.cost_center    "
				 * +
				 * " from dbo.acct_sum2 s2 left join acct_customer_contract acc  on s2.idf_acct_customer_contract = acc.id "
				 * +
				 * " where s2.idf_debit_no =? and s2.EP1_account_code !='0' and s2.autoCostCenter = 0 "
				 * +
				 * " group by s2.idf_cust_no, idf_debit_no, s2.title, s2.sub_title, s2.unit_price,  s2.EP1_account_code, acc.report_title, s2.idf_acct_charge_item, s2.print_code, s2.cost_center   "
				 * ; insertQuery = (SQLQuery)
				 * HibernateSessionFactory.getSession()
				 * .createSQLQuery(insertString); insertQuery.setParameter(0,
				 * acctDno.getDebitNo()); ret1 = insertQuery.executeUpdate();
				 */
			}

			return true;
		} catch (RuntimeException re) {
			log.error("", re);
			return false;
		}
	}

	// 合併debit Note
	// 從 sum1/sum2/sum3/invoce 中, mergedDebitNo 置換成 debitNo
	// 刪除 dbo.acct_dno 中 mergedDebitNo
	public Boolean MergedDebitNo(String debitNo, String mergedDebitNo) {
		try {
			// 執行Step1 表是要重抓工單, 原 sum1/sum2/sum3 中, 該 debit note 資料, 要先刪除
			String deleteString1 = " update dbo.acct_sum1  set idf_debit_no=?  where idf_debit_no=?  ";
			SQLQuery deleteQuery1 = (SQLQuery) HibernateSessionFactory
					.getSession().createSQLQuery(deleteString1);
			deleteQuery1.setParameter(0, debitNo);
			deleteQuery1.setParameter(1, mergedDebitNo);
			int ret1 = deleteQuery1.executeUpdate();

			// 刪除 被合併的mergeNote, db 設定 delete trigger, sum1/sum2/sum3/invoice
			// 全部刪
			String deleteString5 = " delete from acct_dno  WHERE debit_no=? ";
			SQLQuery deleteQuery5 = (SQLQuery) HibernateSessionFactory
					.getSession().createSQLQuery(deleteString5);
			deleteQuery5.setParameter(0, mergedDebitNo);
			int ret5 = deleteQuery5.executeUpdate();

			return true;
		} catch (RuntimeException re) {
			log.error("", re);
			return false;
		}
	}

	//
	// 判斷這筆Sum3Info 報價是否帶數量
	public Boolean isIncludeQyt(AcctSum3Info acctSum3Info) {
		// 查詢 sum3 list
		String queryString = " select  s3.title , s3.subtitle subTitle, convert(varchar,isnull( a.is_exclude_qty,'')) excludeQty ,a.id "
				+ "from dbo.acct_sum3 s3 , acct_charge_item a "
				+ "where  s3.id = ? and a.id = s3.idf_acct_charge_item ";

		SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession()
				.createSQLQuery(queryString);
		// sqlQuery.setParameter(0, acctSum3Info.getAcctDno().getDebitNo());
		sqlQuery.setParameter(0, acctSum3Info.getId());
		sqlQuery.setResultTransformer(Transformers
				.aliasToBean(AcctSum3IncludeQty.class));

		List list = sqlQuery.list();

		if (null != list && list.size() > 0)
			for (int i = 0; i < list.size(); i++) {
				AcctSum3IncludeQty acctSum3IncludeQty = (AcctSum3IncludeQty) list
						.get(i);
				if ("1".equalsIgnoreCase(acctSum3IncludeQty.getExcludeQty()))
					return false;
			}

		return true;

	}

	public DebitNoteFullInfo getDebitNoteFullInfo(AcctDno acctDno,
			String purpose) {
		return getDebitNoteFullInfo(acctDno, purpose, false);

	}

	// 取的完整的Debitnote info, 未稅/含稅/稅額/發票資訊
	//
	public DebitNoteFullInfo getDebitNoteFullInfo(AcctDno acctDno,
			String purpose, boolean removeTotalIsZero) {
		AcctSectionReceiverMapping sectionReceiverMapping = sectionReceiverMappingService
				.findByDebitNo(acctDno);
		if (sectionReceiverMapping == null)
			return null;
		Boolean includeTax = null; // 報價是否含稅
		try {
			if (null != sectionReceiverMapping.getVat())
				includeTax = !sectionReceiverMapping.getVat();
			else
				includeTax = sectionReceiverMapping.getVat();

			DebitNoteFullInfo debitNoteFullInfo = new DebitNoteFullInfo();

			// 從sum3 中, 計算這張debit 合計金額

			java.util.Date defaultValue = null;
			DateConverter converter = new DateConverter(defaultValue);
			ConvertUtils.register(converter, java.util.Date.class);
			BeanUtils.copyProperties(debitNoteFullInfo, acctDno);

			// 查詢 sum3 list
			String queryString = " select {s3.*} from dbo.acct_sum3 s3 where  s3.purpose in ("
					+ purpose + ") and s3.idf_debit_no=?  ";
			if (removeTotalIsZero)
				queryString = " select {s3.*} from dbo.acct_sum3 s3 where  s3.purpose in ("
						+ purpose
						+ ") and s3.idf_debit_no=?  and  s3.total <> 0 and s3.total is not null";
			SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession()
					.createSQLQuery(queryString);
			sqlQuery.addEntity("s3", AcctSum3.class);
			sqlQuery.setParameter(0, acctDno.getDebitNo());

			List retList = sqlQuery.list();
			Double totalAmount = 0.0; // 總金額
			Integer amountIncludeTax = 0; // 含稅金額
			Integer amountExcludeTax = 0; // 未稅金額
			Integer amountTax = 0; // 稅額
			Integer amountBySumToInteger = 0; // 合計後再取整數
			Integer amountByIntegerToSum = 0; // 取整數後再合計

			// 若有該Dabit Note 有調整過的 含稅金額, 未稅金額, 稅額 就認調整的金額
			// 否則系統計算出 預設值, 再讓accounting 來調整
			if (null != acctDno.getTax() && acctDno.getTax().compareTo(0.0) > 0
					&& purpose.indexOf("ADJUSTED_SUM3") < 0) {
				// 由 Dabit Note 帶出調整好的資料
				amountTax = acctDno.getTax().intValue();
				amountIncludeTax = acctDno.getAmountIncludeTax();
				amountExcludeTax = acctDno.getAmountExcludeTax();
			} else {
				// 由 sum3 計算出
				for (int i = 0; i < retList.size(); i++) {
					AcctSum3 acctSum3 = (AcctSum3) retList.get(i);
					totalAmount += acctSum3.getTotal();
				}

				if (includeTax) {
					// 報價含稅
					amountIncludeTax = (int) Math.round(totalAmount);
					Double _tmp = totalAmount / 1.05;
					amountExcludeTax = (int) Math.round(_tmp);
					amountTax = amountIncludeTax - amountExcludeTax;
				} else {
					// 報價不含稅
					amountExcludeTax = (int) Math.round(totalAmount);
					Double _tmp = totalAmount * 1.05;
					amountIncludeTax = (int) Math.round(_tmp);
					amountTax = amountIncludeTax - amountExcludeTax;
				}
				if (purpose.indexOf("ADJUSTED_SUM3") >= 0
						&& ((acctDno.getTax() == null || acctDno.getTax()
								.intValue() != amountTax)
								|| ((acctDno.getAmountExcludeTax() == null || acctDno
										.getAmountExcludeTax() != amountExcludeTax)) || ((acctDno
								.getAmountIncludeTax() == null || acctDno
								.getAmountIncludeTax() != amountIncludeTax)))) {
					acctDno.setTax((double) amountTax);
					acctDno.setAmountExcludeTax(amountExcludeTax);
					acctDno.setAmountIncludeTax(amountIncludeTax);
					this.save(acctDno);
				}

			}

			debitNoteFullInfo.setAmountExcludeTax(amountExcludeTax);
			debitNoteFullInfo.setAmountIncludeTax(amountIncludeTax);
			debitNoteFullInfo.setAmountTax(amountTax);
			// 由 sum3 計算出
			totalAmount = 0.0;
			for (int i = 0; i < retList.size(); i++) {
				AcctSum3 acctSum3 = (AcctSum3) retList.get(i);
				totalAmount += acctSum3.getTotal();
			}
			amountBySumToInteger = (int) Math.round(totalAmount);

			// 計算 acctSum3Info List
			Integer _totalAmountExcludeTax = 0;
			Integer _totalAmountIncludeTax = 0;
			List acctSum3InfoList = new ArrayList();
			List<Integer> totalIsZero = new ArrayList<Integer>();

			// 把total為0的去掉
			/*
			 * for (int i = 0; i < retList.size(); i++) { AcctSum3 acctSum3 =
			 * (AcctSum3) retList.get(i); if(acctSum3.getTotal() == 0){
			 * totalIsZero.add(i); } } for(int i = totalIsZero.size() - 1 ; i >=
			 * 0 ; i--){ retList.remove(totalIsZero.get(i).intValue()); }
			 */

			for (int i = 0; i < retList.size(); i++) {
				Integer _amountIncludeTax = 0;
				Integer _amountExcludeTax = 0;
				Integer _amountTax = 0;

				AcctSum3 acctSum3 = (AcctSum3) retList.get(i);
				AcctSum3Info acctSum3Info = new AcctSum3Info();
				BeanUtils.copyProperties(acctSum3Info, acctSum3);
				amountByIntegerToSum += (int) Math.round(acctSum3Info
						.getTotal());

				if (includeTax && i == retList.size() - 1) { // 報價含稅 且最後一筆
																// 的未稅金額,
																// 要用總未稅金額 -
																// 累計未稅金額
					_amountIncludeTax = (int) Math.round(acctSum3.getTotal());
					_amountExcludeTax = amountExcludeTax
							- _totalAmountExcludeTax;
					_amountTax = _amountIncludeTax - _amountExcludeTax;
				} else if (!includeTax && i == retList.size() - 1) { // 報價未含稅
																		// 且最後一筆
																		// 的含稅金額,
																		// 要用總含稅金額
																		// -
																		// 累計含稅金額
					_amountExcludeTax = (int) Math.round(acctSum3.getTotal());
					_amountIncludeTax = amountIncludeTax
							- _totalAmountIncludeTax;
					_amountTax = _amountIncludeTax - _amountExcludeTax;
				} else if (includeTax) {
					_amountIncludeTax = (int) Math.round(acctSum3.getTotal());
					Double _tmp = acctSum3.getTotal() / 1.05;
					_amountExcludeTax = (int) Math.round(_tmp);
					_amountTax = _amountIncludeTax - _amountExcludeTax;
				} else {
					_amountExcludeTax = (int) Math.round(acctSum3.getTotal());
					Double _tmp = acctSum3.getTotal() * 1.05;
					_amountIncludeTax = (int) Math.round(_tmp);
					_amountTax = _amountIncludeTax - _amountExcludeTax;
				}

				acctSum3Info.setAmountExcludeTax(_amountExcludeTax);
				acctSum3Info.setAmountIncludeTax(_amountIncludeTax);
				acctSum3Info.setAmountTax(_amountTax);
				acctSum3InfoList.add(acctSum3Info);

				_totalAmountExcludeTax += _amountExcludeTax; // 累計未稅價格
				_totalAmountIncludeTax += _amountIncludeTax; // 累計含稅價格
			}
			debitNoteFullInfo.setAcctSum3InfoList(acctSum3InfoList);
			debitNoteFullInfo.setAmountBySumToInteger(amountBySumToInteger);
			debitNoteFullInfo.setAmountByIntegerToSum(amountByIntegerToSum);

			debitNoteFullInfo.setVat(sectionReceiverMapping.getVat());
			debitNoteFullInfo.setPaymentTerm(sectionReceiverMapping
					.getPaymentTerm());

			// 發票 List
			// 查詢 sum3 list
			// String queryString2 =
			// " select {i.*} from dbo.acct_invoice i  where i.idf_debit_no=? and i.invoicetyp='' ";
			/*
			 * String queryString2 =
			 * " select {i.*} from dbo.acct_invoice i  where i.idf_debit_no=?  "
			 * ; SQLQuery sqlQuery2 = (SQLQuery) HibernateSessionFactory
			 * .getSession().createSQLQuery(queryString2);
			 * sqlQuery2.addEntity("i", AcctInvoice.class);
			 * sqlQuery2.setParameter(0, acctDno.getDebitNo());
			 * 
			 * List retList2 = sqlQuery2.list();
			 */
			List retList2 = acctInvoiceService.findByDebitNote(acctDno);

			// 計算 acctSum3Info List
			_totalAmountExcludeTax = 0;
			_totalAmountIncludeTax = 0;
			List acctInoviceInfoList = new ArrayList();
			for (int i = 0; i < retList2.size(); i++) {
				Integer _amountIncludeTax = 0;
				Integer _amountExcludeTax = 0;
				Integer _amountTax = 0;

				AcctInvoice acctInvoice = (AcctInvoice) retList2.get(i);
				AcctInvoiceInfo acctInvoiceInfo = new AcctInvoiceInfo();
				BeanUtils.copyProperties(acctInvoiceInfo, acctInvoice);

				if (includeTax && i == retList2.size()) { // 報價含稅 且最後一筆 的未稅金額,
															// 要用總未稅金額 - 累計未稅金額
					_amountIncludeTax = (int) Math.round(acctInvoice
							.getExpense());
					_amountExcludeTax = amountExcludeTax
							- _totalAmountExcludeTax;
					_amountTax = _amountIncludeTax - _amountExcludeTax;
				} else if (!includeTax && i == retList2.size()) { // 報價未含稅 且最後一筆
																	// 的含稅金額,
																	// 要用總含稅金額 -
																	// 累計含稅金額
					_amountExcludeTax = (int) Math.round(acctInvoice
							.getExpense());
					_amountIncludeTax = amountIncludeTax
							- _totalAmountIncludeTax;
					_amountTax = _amountIncludeTax - _amountExcludeTax;
				} else if (includeTax) {
					_amountIncludeTax = (int) Math.round(acctInvoice
							.getExpense());
					Double _tmp = acctInvoice.getExpense() / 1.05;
					_amountExcludeTax = (int) Math.round(_tmp);
					_amountTax = _amountIncludeTax - _amountExcludeTax;
				} else {
					_amountExcludeTax = (int) Math.round(acctInvoice
							.getExpense());
					Double _tmp = acctInvoice.getExpense() * 1.05;
					_amountIncludeTax = (int) Math.round(_tmp);
					_amountTax = _amountIncludeTax - _amountExcludeTax;
				}
				acctInvoiceInfo.setAmountExcludeTax(_amountExcludeTax);
				acctInvoiceInfo.setAmountIncludeTax(_amountIncludeTax);
				acctInvoiceInfo.setAmountTax(_amountTax);
				acctInoviceInfoList.add(acctInvoiceInfo);
				_totalAmountExcludeTax += _amountExcludeTax; // 累計未稅價格
				_totalAmountIncludeTax += _amountIncludeTax; // 累計含稅價格
			}
			debitNoteFullInfo.setAcctInvoiceInfoList(acctInoviceInfoList);

			return debitNoteFullInfo;
		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		} catch (Exception re) {
			log.error("", re);
			return null;
		}
	}

	/*
	 * Unlock Debit Note 中, job_bag 的狀態 = '交寄完成' 讓工單回到可以編輯的狀態
	 */
	public Boolean unlockJobBagStatus(String debitNo) throws Exception {
		try {
			/*
			 * String updateString =
			 * " update job_bag set JOB_BAG_STATUS='COMPLETED_LG'  " +
			 * " where JOB_BAG_NO in ( select distinct s.IDF_JOB_BAG_NO from acct_sum1 s  where s.idf_debit_no= ? )"
			 * ; SQLQuery updateQuery = (SQLQuery) HibernateSessionFactory
			 * .getSession().createSQLQuery(updateString);
			 * updateQuery.setParameter(0, debitNo); int ret5 =
			 * updateQuery.executeUpdate();
			 */
			HibernateSessionFactory.getSession().getTransaction().begin();
			String queryStr = "select  distinct {j.*} from acct_sum1 s, job_bag j  where j.job_bag_no = s.IDF_JOB_BAG_NO and s.idf_debit_no= ?";
			SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession()
					.createSQLQuery(queryStr);
			sqlQuery.addEntity("j", JobBag.class);
			sqlQuery.setParameter(0, debitNo);
			List<JobBag> jobBags = sqlQuery.list();
			String status = "COMPLETED_LG";
			String nowStatus = "ACCOUNTING_LOCKED";
			for (JobBag jobBag : jobBags) {
				boolean unlock = true;
				String notes = jobBag.getNotes();
				// 檢查是不是被別的debit notes鎖定了，如果被別的debit notes鎖定，就不解開
				if (notes != null
						&& (notes.startsWith("Locked By:") || notes
								.startsWith("Locked by:"))) {
					notes = notes.substring("Locked By:".length());
					if (notes.equals(debitNo)) {
						unlock = true;
						jobBag.setNotes("");
					} else {
						unlock = false;
					}
				}
				if (unlock) {
					if (jobBag.getPrevStatus() != null
							&& !"ACCOUNTING_LOCKED".equals(jobBag
									.getPrevStatus())
							&& !"ACCT_DN_GENERATED".equals(jobBag
									.getPrevStatus()))
						status = jobBag.getPrevStatus();
					// 只有在會計鎖定狀態時才回復前一狀態
					if ("ACCOUNTING_LOCKED".equalsIgnoreCase(jobBag
							.getJobBagStatus())
							|| "ACCT_DN_GENERATED".equals(jobBag
									.getJobBagStatus())) {
						jobBag.setJobBagStatus(status);
						jobBag.setPrevStatus(status);
						HibernateSessionFactory.getSession().update(jobBag);
					}
				}
			}
			HibernateSessionFactory.getSession().getTransaction().commit();
			return true;
		} catch (Exception re) {
			log.error("", re);
			HibernateSessionFactory.getSession().getTransaction().rollback();
			throw re;
			// return false;
		} finally {
			if (HibernateSessionFactory.getSession().isOpen())
				HibernateSessionFactory.closeSession();
		}

	}

	public String ctUnlockJobBagStatus(String debitNo) throws Exception {

		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSession();
			tx = session.getTransaction();
			tx.begin();
			String queryStr = "select  distinct {j.*} from acct_sum1 s, job_bag j  where j.job_bag_no = s.IDF_JOB_BAG_NO and s.idf_debit_no= ?";
			SQLQuery sqlQuery = (SQLQuery) session.createSQLQuery(queryStr);
			sqlQuery.addEntity("j", JobBag.class);
			sqlQuery.setParameter(0, debitNo);
			List<JobBag> jobBags = sqlQuery.list();
			String status = "COMPLETED_LG";
			boolean haveVirtuJobBag = false;
			for (JobBag jobBag : jobBags) {
				boolean unlock = true;
				String notes = jobBag.getNotes();
				// 檢查是不是被別的debit notes鎖定了，如果被別的debit notes鎖定，就不解開
				if (notes != null
						&& (notes.startsWith("Locked By:") || notes
								.startsWith("Locked by:"))) {
					notes = notes.substring("Locked By:".length());
					if (notes.equals(debitNo)) {
						unlock = true;
						jobBag.setNotes("");
					} else {
						unlock = false;
					}
				}
				if (unlock) {
					if (jobBag.getPrevStatus() != null
							&& !"ACCOUNTING_LOCKED".equals(jobBag
									.getPrevStatus())
							&& !"ACCT_DN_GENERATED".equals(jobBag
									.getPrevStatus()))
						status = jobBag.getPrevStatus();
					
					if (jobBag.getCtSpecialSplite() == null) {
						// system.out.println();
						if (jobBag.getJobBagNo().length() > 20) {
							session.delete(jobBag); // 字數長過20字為虛擬工單刪除掉
							haveVirtuJobBag = true;
						} else {
							if ("ACCOUNTING_LOCKED".equalsIgnoreCase(jobBag
									.getJobBagStatus())
									|| "ACCT_DN_GENERATED".equals(jobBag
											.getJobBagStatus())) {
								jobBag.setJobBagStatus(status);
								jobBag.setPrevStatus(status);
								session.update(jobBag);
							}
						}
					} else {
						if (jobBag.getCtSpecialSplite()) {
							// 特殊拆帳的還原為原來數字
							if (jobBag.getPagesOri() != null
									&& jobBag.getPagesOri() > 0)
								jobBag.setPages(jobBag.getPagesOri());
							if (jobBag.getPages2Ori() != null
									&& jobBag.getPages2Ori() > 0)
								jobBag.setPages2(jobBag.getPages2Ori());
							if (jobBag.getPages3Ori() != null
									&& jobBag.getPages3Ori() > 0)
								jobBag.setPages3(jobBag.getPages3Ori());
							jobBag.setCtSpecialSplite(false);
							if ("ACCOUNTING_LOCKED".equalsIgnoreCase(jobBag
									.getJobBagStatus())
									|| "ACCT_DN_GENERATED".equals(jobBag
											.getJobBagStatus())) {
								jobBag.setJobBagStatus(status);
								jobBag.setPrevStatus(status);
							}

						} else if (!jobBag.getCtSpecialSplite()) {
							// 非特殊拆帳的只需要更新狀態
							if ("ACCOUNTING_LOCKED".equalsIgnoreCase(jobBag
									.getJobBagStatus())
									|| "ACCT_DN_GENERATED".equals(jobBag
											.getJobBagStatus())) {
								jobBag.setJobBagStatus(status);
								jobBag.setPrevStatus(status);
							}
						}
						if ("ACCOUNTING_LOCKED".equalsIgnoreCase(jobBag
								.getJobBagStatus())
								|| "ACCT_DN_GENERATED".equals(jobBag
										.getJobBagStatus()))
							session.update(jobBag);
					}
				}
			}
			tx.commit();
			if (haveVirtuJobBag)
				return "還原為之前狀態，並且刪除虛擬工單";
			else
				return "還原為之前狀態";
		} catch (Exception e) {
			log.error("", e);
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}

	}
	
	public void deleteAllReport(String debitNo) throws Exception{
		Session session = getSession();
        Transaction tx = session.beginTransaction();     
        tx.begin();
		String deleteString1 = " delete from dbo.acct_sum1   WHERE idf_debit_no=?  ";
		SQLQuery deleteQuery1 = (SQLQuery)session.createSQLQuery(deleteString1);
		deleteQuery1.setParameter(0, debitNo);
		int ret1 = deleteQuery1.executeUpdate();
	

	    String deleteString2 = " delete from dbo.acct_sum2   WHERE idf_debit_no=? and ITEM_TYPE='JOB_BAG' ";
	    SQLQuery deleteQuery2 = (SQLQuery)session.createSQLQuery(deleteString2);
	    deleteQuery2.setParameter(0, debitNo);
	    int ret2 = deleteQuery2.executeUpdate();

	    String deleteString3 = " delete from dbo.acct_sum3   WHERE idf_debit_no=? ";
	    SQLQuery deleteQuery3 = (SQLQuery) session.createSQLQuery(deleteString3);
	    deleteQuery3.setParameter(0, debitNo);
	    int ret3 = deleteQuery3.executeUpdate();

	    String deleteString4 = " delete from dbo.acct_invoice   WHERE idf_debit_no=? ";
	    SQLQuery deleteQuery4 = (SQLQuery) session.createSQLQuery(deleteString4);
	    deleteQuery4.setParameter(0, debitNo);
	    int ret4 = deleteQuery4.executeUpdate();
	    tx.commit();
		session.close();
	}

	public String[] exportEp1(AcctDno acctDno) throws Exception {

		AcctSectionReceiverMappingService sectionReceiverMappingService = AcctSectionReceiverMappingService
				.getInstance();

		AcctSectionReceiverMapping acctSectionReceiverMapping = sectionReceiverMappingService
				.findByDebitNo(acctDno);

		String NowTime = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());

		String debitNoteNo = acctDno.getDebitNo();
		boolean includeTax = false;
		includeTax = acctSectionReceiverMapping.getVat(); // 報價含稅

		String purpose = "'','TO_EP1'";

		DebitNoteFullInfo debitNoteFullInfo = getDebitNoteFullInfo(acctDno,
				purpose, true);

		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumIntegerDigits(3);
		nf.setMinimumIntegerDigits(3);
		String paymentTerm = nf
				.format((debitNoteFullInfo.getPaymentTerm() == null) ? 60
						: debitNoteFullInfo.getPaymentTerm());

		String serverPath = SetParamValFilter.getServletContext().getRealPath(
				"");

		//String targetfileName = acctDno.getCustomer().getCustNo() + "_"
			//	+ debitNoteNo + "_" + NowTime + ".twn";
		String targetfileName = "DMSTWJBM_" + acctDno.getCustomer().getCustNo() + "_"
				+ debitNoteNo + "_" + NowTime + ".txt";
		String targetfileName1 = acctDno.getCustomer().getCustNo() + "_"
				+ debitNoteNo + "_" + NowTime + "_1.csv";
		String targetfileName2 = acctDno.getCustomer().getCustNo() + "_"
				+ debitNoteNo + "_" + NowTime + "_2.csv";

		String targetfile = serverPath + "\\pdf\\" + targetfileName;
		String targetfile1 = serverPath + "\\pdf\\" + targetfileName1;
		String targetfile2 = serverPath + "\\pdf\\" + targetfileName2;

		FileOutputStream fos = null;
		OutputStreamWriter ow = null;
		BufferedWriter saveFile = null;

		FileOutputStream fos1 = null;
		OutputStreamWriter ow1 = null;
		BufferedWriter saveFile_1 = null;

		FileOutputStream fos2 = null;
		OutputStreamWriter ow2 = null;
		BufferedWriter saveFile_2 = null;

		// FileWriter saveFile = null;
		// FileWriter saveFile_1 = null;
		// FileWriter saveFile_2 = null;
		try {
			fos = new FileOutputStream(targetfile);
			//ow = new OutputStreamWriter(fos, "ms950");
			ow = new OutputStreamWriter(fos, "UTF-8");
			saveFile = new BufferedWriter(ow);

			fos1 = new FileOutputStream(targetfile1);
			//ow1 = new OutputStreamWriter(fos1, "ms950");
			ow1 = new OutputStreamWriter(fos1, "UTF-8");
			saveFile_1 = new BufferedWriter(ow1);

			fos2 = new FileOutputStream(targetfile2);
			//ow2 = new OutputStreamWriter(fos2, "ms950");
			ow2 = new OutputStreamWriter(fos2, "UTF-8");
			saveFile_2 = new BufferedWriter(ow2);

			// saveFile_1 = new FileWriter(targetfile1);
			// saveFile_2 = new FileWriter(targetfile2);

			String F1 = "\"1\",";

			String invoiceDate = acctDno.getInDt();
			String invoiceDuteDate = acctDno.getInDuedt();

			// 轉換日期格式 DB 是存 YYYY-MM-DD, 改為 04/30/2011
			SimpleDateFormat ep1Date = new SimpleDateFormat("yyyy-MM-dd");
			if (null != invoiceDate && invoiceDate.length() > 0) {
				Date _date = ep1Date.parse(invoiceDate);
				invoiceDate = new SimpleDateFormat("MM/dd/yyyy").format(_date);
			}
			if (null != invoiceDuteDate && invoiceDuteDate.length() > 0) {
				Date _date = ep1Date.parse(invoiceDuteDate);
				invoiceDuteDate = new SimpleDateFormat("MM/dd/yyyy")
						.format(_date);
			}

			String ep1Account = acctDno.getCustomer().getEp1no();
			String invoiceNo = ""; // 發票號碼(數字部份)
			String invoiceWord = ""; // 發票號碼(文字部份)
			if (null != acctDno.getInNo() && acctDno.getInNo().length() >= 10) {
				invoiceNo = acctDno.getInNo().substring(2, 10);
				invoiceWord = acctDno.getInNo().substring(0, 2);
			}

			String remark = "";
			Integer amountIncludeTax = 0; // 含稅金額
			Integer amountExcludeTax = 0; // 未稅金額
			Integer amountTax = 0; // 稅額
			String[] file1Line = new String[1000];
			Integer lineCounter1 = 1;
			Integer arrayIndex1 = 0;

			/*
			 * Weekly 11 Process MKT-May CTW11ES11-MKT Weekly 11 Process
			 * MKT-karen CTW11ES11-MKT Weekly 11 Process MKT-yvonne
			 * CTW11ES11-MKT Weekly 11 Process MKT-Ivy ho CTW11ES11-MKT Weekly
			 * 11 Process MKT-Irrene CTW11ES11-MKT Weekly 11 Process MKT-Grace
			 * CTW11ES11-MKT Weekly 11 Process MKT-Jovi CTW11ES11-MKT Weekly 11
			 * Process MKT-karen CTW11ES11-MKT Weekly 11 Process MKT-Elaine
			 * CTW11ES11-MKT Weekly 11 Process MKT-stepha CTW11ES11-MKT Weekly
			 * 11 Process 1-1335 CTW11M11-1 Weekly 11 Process 1-1337 CTW11M11-1
			 * Weekly 11 Process 1-1350 CTW11M11-1 Weekly 11 Process 1-1737
			 * CTW11M11-1 Weekly 11 Process 1-3322 CTW11M11-1 Weekly 11 Process
			 * 1-8300 CTW11M11-1 Weekly 11 Process 1-8301 CTW11M11-1 Weekly 11
			 * Process 1-8505 CTW11M11-1 Weekly 11 Process 1-8800 CTW11M11-1
			 * Weekly 11 Process 1-8817 CTW11M11-1 Weekly 11 Process 2-8301
			 * CTW11M11-2 Weekly 11 Process 2-8508 CTW11M11-2 Weekly 11 Process
			 * 2-8512 CTW11M11-2 Weekly 11 Process 2-8800 CTW11M11-2 Weekly 11
			 * Process 2-8829 CTW11M11-2 Weekly 11 Process E CTW11M11-E Monthly
			 * 11 Process 2A SL11M11-2A Monthly 11 Process 3A SL11M11-3A Monthly
			 * 11 Process 4 SL11M11-4 Monthly 11 Process 5 SL11M11-5
			 */

			// Ex: CTW11Mnn-S HS11M12-7S CTW11W46-2 ND11M09-B
			// 第三碼(W: Weekly,M: Monthly)
			// 第六碼(nn)-後(Process S)
			// Weekly 04 Process 2
			String sectionStr = "";
			if (acctDno.getDebitNo().matches("..(W|M).*")) {
				// CTW11Mnn-S CTW11ESnn-mkt
				if (acctDno.getDebitNo().substring(2, 3).equalsIgnoreCase("W"))
					remark = "Weekly ";
				else if (acctDno.getDebitNo().substring(2, 3)
						.equalsIgnoreCase("M"))
					remark = "Monthly ";
				if (acctDno
						.getDebitNo()
						.matches(
								"..(W|M)([0-9][0-9])([A-Z][A-Z]|[a-z][a-z])(0[1-9]|1[0-2])-.*")) {
					// CTW11ESnn-mkt
					sectionStr = acctDno.getDebitNo().substring(10);
					remark += (" " + acctDno.getDebitNo().substring(7, 9));
				} else if (acctDno.getDebitNo().matches(
						"..(W|M)([0-9][0-9])([A-Z]|[a-z])(0[1-9]|1[0-2])-.*")) {
					sectionStr = acctDno.getDebitNo().substring(9);
					remark += (" " + acctDno.getDebitNo().substring(6, 8));
				} else if (acctDno.getDebitNo().matches(
						"..(W|M)([0-9][0-9])([A-Z][A-Z]|[a-z][a-z])([1-9])-.*")) {
					sectionStr = acctDno.getDebitNo().substring(9);
					remark += (" 0" + acctDno.getDebitNo().substring(7, 8));
				} else if (acctDno.getDebitNo().matches(
						"..(W|M)([0-9][0-9])([A-Z]|[a-z])([1-9])-.*")) {
					sectionStr = acctDno.getDebitNo().substring(8);
					remark += (" 0" + acctDno.getDebitNo().substring(6, 7));
				}
			} else if (acctDno.getDebitNo().matches(
					"([a-z]|[A-Z])([a-z]|[A-Z])([0-9][0-9])[A-Z].*")) {
				// HS11M12-7S
				if (acctDno.getDebitNo().substring(4, 5).equalsIgnoreCase("W"))
					remark = "Weekly ";
				else if (acctDno.getDebitNo().substring(4, 5)
						.equalsIgnoreCase("M"))
					remark = "Monthly ";
				else
					remark = "Monthly";
				if (acctDno
						.getDebitNo()
						.matches(
								"([a-z]|[A-Z])([a-z]|[A-Z])(0[1-9]|1[0-2])[A-Z](0[1-9]|1[0-2])-.*")) {
					remark += (" " + acctDno.getDebitNo().substring(5, 7));
					sectionStr = acctDno.getDebitNo().substring(8);
				} else if (acctDno
						.getDebitNo()
						.matches(
								"([a-z]|[A-Z])([a-z]|[A-Z])(0[1-9]|1[0-2])[A-Z]([1-9])-.*")) {
					remark += (" 0" + acctDno.getDebitNo().substring(5, 6));
					sectionStr = acctDno.getDebitNo().substring(7);
				}
			}
			// 都沒有時用目前時間
			if (remark.equals("")) {
				Integer month = Calendar.getInstance().get(Calendar.MONTH) + 1;
				java.text.NumberFormat nf2 = java.text.NumberFormat
						.getInstance();
				nf2.setMaximumIntegerDigits(2);
				nf2.setMinimumIntegerDigits(2);
				remark = "Monthly " + nf2.format(month);
				sectionStr = acctDno.getSection() == null ? "" : acctDno
						.getSection();
			}
			remark += (" Process " + sectionStr);

			// remark 抓 getReportIiiTitle
			if (null != acctSectionReceiverMapping.getReportIiiTitle()
					&& !acctSectionReceiverMapping.getReportIiiTitle().trim()
							.equals(""))
				remark = acctSectionReceiverMapping.getReportIiiTitle();

			// 產生 發票資訊 _1.csv
			Set acctInvoices = acctDno.getAcctInvoices();
			String invoiceTitle = remark;
			if (acctInvoices.size() > 0) { // 有輸入發票資料
				List acctInvoiceInfoList = debitNoteFullInfo
						.getAcctInvoiceInfoList();
				for (int i = 0; i < acctInvoiceInfoList.size(); i++) {
					AcctInvoiceInfo acctInvoiceInfo = (AcctInvoiceInfo) acctInvoiceInfoList
							.get(i);
					//有輸入發票號碼的才顯示
					if (acctInvoiceInfo.getInvoiceno() != null
							&& acctInvoiceInfo.getInvoiceno().trim().length() == 10) {
						amountIncludeTax = acctInvoiceInfo
								.getAmountIncludeTax(); // 含稅金額
						amountExcludeTax = acctInvoiceInfo
								.getAmountExcludeTax(); // 未稅金額
						amountTax = acctInvoiceInfo.getAmountTax(); // 稅額
						
						if(acctDno.getRefundamt() != null && acctDno.getRefundamt().doubleValue() != 0){
							amountIncludeTax = (int) (amountIncludeTax + acctDno.getRefundamt());
							amountExcludeTax = (int)Math.round(amountIncludeTax / 1.05);
							amountTax = amountIncludeTax - amountExcludeTax;
						}
						


						// 發票號碼為空值，且cost center也為空值時，就不列出
						boolean printout = false;
						String costCenter = acctInvoiceInfo.getCostCenter();
						if ((acctInvoiceInfo.getInvoiceno() != null && !acctInvoiceInfo
								.getInvoiceno().trim().equals(""))
								|| (costCenter != null
										&& !costCenter.trim().equals("") && !costCenter
										.trim().equals("defaul_C")))
							printout = true;

						if (acctInvoiceInfo.getInvoiceno() != null
								&& acctInvoiceInfo.getInvoiceno().length() == 10) {
							invoiceNo = acctInvoiceInfo.getInvoiceno()
									.substring(2, 10);
							invoiceWord = acctInvoiceInfo.getInvoiceno()
									.substring(0, 2);
						} else {
							invoiceNo = "";
							invoiceWord = "";
						}
						if (printout) {
							// "1","CTW11M04-2","XJ","   1",04/30/2011,06/29/2011,04/30/2011,"667260","Weekly 04 Process 2-1309","64",1366,1301,65,"TWOT","060","41458575","SY","",""
							file1Line[arrayIndex1] = "1" + ","; // 1: 發票資訊
							file1Line[arrayIndex1] += "" + acctDno.getDebitNo()
									+ "" + ","; // Debit note no
							file1Line[arrayIndex1] += "XJ" + ","; // 固定字元 “XJ”
							file1Line[arrayIndex1] += "" + lineCounter1 + ""
									+ ","; // 序號1-N
							file1Line[arrayIndex1] += invoiceDate + ","; // INVOICE
																			// DATE
																			// (MM/DD/YYYY)
							file1Line[arrayIndex1] += invoiceDuteDate + ","; // INVOICE
																				// (MM/DD/YYYY)
							file1Line[arrayIndex1] += invoiceDate + ","; // INVOICE
																			// DATE
																			// (MM/DD/YYYY)
							file1Line[arrayIndex1] += "" + ep1Account + ""
									+ ","; // 客戶EP1 ACCOUNT
							if (costCenter != null
									&& !costCenter.trim().equals("")
									&& !costCenter.trim().equals("defaul_C")) {
								remark = invoiceTitle + "-" + costCenter;
							}
							file1Line[arrayIndex1] += "" + remark + "" + ","; // 說明文字
							file1Line[arrayIndex1] += "64" + ","; // 台灣代碼 “64”
							file1Line[arrayIndex1] += amountIncludeTax + ","; // 含稅金額
							file1Line[arrayIndex1] += amountExcludeTax + ","; // 未稅金額
							file1Line[arrayIndex1] += amountTax + ","; // 稅額
							file1Line[arrayIndex1] += "TWOT" + ","; // 固定字元
																	// “TWOT”
							file1Line[arrayIndex1] += paymentTerm + ","; // paymentTerm
							file1Line[arrayIndex1] += "" + invoiceNo + "" + ","; // 發票號碼(數字部份)
							file1Line[arrayIndex1] += "" + invoiceWord + ""
									+ ","; // 發票號碼(文字部份)
							file1Line[arrayIndex1] += "" + ",,"; // 固定字元-空白
							file1Line[arrayIndex1] += "\r\n"; // 固定字元-空白
							lineCounter1 = lineCounter1 + 1;
							arrayIndex1 = arrayIndex1 + 1;
						}
					}
				}
			} else { // 無輸入發票資料, 從 sum3 中 合計 總金額
				amountIncludeTax = debitNoteFullInfo.getAmountIncludeTax(); // 含稅金額
				amountExcludeTax = debitNoteFullInfo.getAmountExcludeTax(); // 未稅金額
				amountTax = debitNoteFullInfo.getAmountTax(); // 稅額
				if(acctDno.getRefundamt() != null && acctDno.getRefundamt().doubleValue() != 0){
					amountIncludeTax = (int) (amountIncludeTax + acctDno.getRefundamt());
					amountExcludeTax = (int)Math.round(amountIncludeTax / 1.05);
					amountTax = amountIncludeTax - amountExcludeTax;
				}
				

				// "1","CTW11M04-2","XJ","   1",04/30/2011,06/29/2011,04/30/2011,"667260","Weekly 04 Process 2-1309","64",1366,1301,65,"TWOT","060","41458575","SY","",""
				file1Line[arrayIndex1] = "1" + ","; // 1: 發票資訊
				file1Line[arrayIndex1] += "" + acctDno.getDebitNo() + "" + ","; // Debit
																				// note
																				// no
				file1Line[arrayIndex1] += "XJ" + ","; // 固定字元 “XJ”
				file1Line[arrayIndex1] += "" + lineCounter1 + "" + ","; // 序號1-N
				file1Line[arrayIndex1] += invoiceDate + ","; // INVOICE DATE
																// (MM/DD/YYYY)
				file1Line[arrayIndex1] += invoiceDuteDate + ","; // INVOICE DUE
																	// DATE
																	// (MM/DD/YYYY)
				file1Line[arrayIndex1] += invoiceDate + ","; // INVOICE DATE
																// (MM/DD/YYYY)
				file1Line[arrayIndex1] += "" + ep1Account + "" + ","; // 客戶EP1
																		// ACCOUNT
				file1Line[arrayIndex1] += "" + remark + "" + ","; // 說明文字
				file1Line[arrayIndex1] += "64" + ","; // 台灣代碼 “64”
				file1Line[arrayIndex1] += amountIncludeTax + ","; // 含稅金額
				file1Line[arrayIndex1] += amountExcludeTax + ","; // 未稅金額
				file1Line[arrayIndex1] += amountTax + ","; // 稅額
				file1Line[arrayIndex1] += "TWOT" + ","; // 固定字元 “TWOT”
				file1Line[arrayIndex1] += paymentTerm + ","; // paymentTerm
				file1Line[arrayIndex1] += "" + invoiceNo + "" + ","; // 發票號碼(數字部份)
				file1Line[arrayIndex1] += "" + invoiceWord + "" + ","; // 發票號碼(文字部份)
				file1Line[arrayIndex1] += "" + ",,"; // 固定字元-空白
				file1Line[arrayIndex1] += "\r\n"; // 固定字元-空白
				lineCounter1 = lineCounter1 + 1;
				arrayIndex1 = arrayIndex1 + 1;
			}

			// 作業內容資料 saveFile2
			String[] file2Line = new String[1000];
			Integer lineCounter2 = 1;
			Integer arrayIndex2 = 0;
			List acctSum3InfoList = debitNoteFullInfo.getAcctSum3InfoList();
			for (int i = 0; i < acctSum3InfoList.size(); i++) {
				AcctSum3Info acctSum3Info = (AcctSum3Info) acctSum3InfoList
						.get(i);
				amountIncludeTax = acctSum3Info.getAmountIncludeTax(); // 含稅金額
				amountExcludeTax = acctSum3Info.getAmountExcludeTax(); // 未稅金額
				amountTax = acctSum3Info.getAmountTax(); // 稅額
				Integer sumQty = acctSum3Info.getSumQty().intValue();
				// 是否不帶數量
				boolean retIncludeQyt = isIncludeQyt(acctSum3Info);
				if (!retIncludeQyt)
					sumQty = 0;

				String EP1Code = acctSum3Info.getEp1acccd();

				// "2","CTW11M04-2","XJ","   1",04/30/2011,04/30/2011,"667260","64",-489856,"641155.5010.2033",-8004182,"CTW11M04-2","",""
				file2Line[arrayIndex2] = "2" + ","; // 2: 作業內容資訊
				file2Line[arrayIndex2] += "" + acctDno.getDebitNo() + "" + ","; // Debit
																				// note
																				// no
				file2Line[arrayIndex2] += "XJ" + ","; // 固定字元 “XJ”
				file2Line[arrayIndex2] += "" + lineCounter2 + "" + ","; // 序號1-N
				file2Line[arrayIndex2] += invoiceDate + ","; // INVOICE DATE
																// (MM/DD/YYYY)
				file2Line[arrayIndex2] += invoiceDate + ","; // INVOICE DATE
																// (MM/DD/YYYY)
				file2Line[arrayIndex2] += "" + ep1Account + "" + ","; // 客戶EP1
																		// ACCOUNT
				file2Line[arrayIndex2] += "64" + ","; // 台灣代碼 “64”
				file2Line[arrayIndex2] += "-" + amountExcludeTax + ","; // 未稅金額
																		// 應收款為負數/應付款為正數
				file2Line[arrayIndex2] += "" + EP1Code + "" + ","; // 作業對應EP1
																	// ACCOUNT
																	// CODE
				file2Line[arrayIndex2] += "-" + sumQty + ","; // 作業數量
				file2Line[arrayIndex2] += "" + acctDno.getDebitNo() + "" + ","; // Debit
																				// note
																				// no
				file2Line[arrayIndex2] += "" + ",,"; // 固定字元-空白
				file2Line[arrayIndex2] += "\r\n"; // 固定字元-空白
				lineCounter2 = lineCounter2 + 1;
				arrayIndex2 = arrayIndex2 + 1;
			}

			// 若有退費, 需增加 一筆 退費相目
			// ◎ 針對Refund 的費用, 於EP1 中屬於 “641103.5010.2010” – “Sales Packs”
			if (null != acctDno.getRefundamt()
					&& acctDno.getRefundamt().compareTo(0.0) != 0) {
				String EP1Code = "641103.5010.2010";
				amountExcludeTax = (int) Math.round(acctDno.getRefundamt()
						.intValue() * -1 / 1.05); // 退費金額 以 + 表示, 轉為未稅價

				// "2","CTW11M04-2","XJ","   1",04/30/2011,04/30/2011,"667260","64",-489856,"641155.5010.2033",-8004182,"CTW11M04-2","",""
				file2Line[arrayIndex2] = "2" + ","; // 2: 作業內容資訊
				file2Line[arrayIndex2] += "" + acctDno.getDebitNo() + "" + ","; // Debit
																				// note
																				// no
				file2Line[arrayIndex2] += "XJ" + ","; // 固定字元 “XJ”
				file2Line[arrayIndex2] += "" + lineCounter2 + "" + ","; // 序號1-N
				file2Line[arrayIndex2] += invoiceDate + ","; // INVOICE DATE
																// (MM/DD/YYYY)
				file2Line[arrayIndex2] += invoiceDate + ","; // INVOICE DATE
																// (MM/DD/YYYY)
				file2Line[arrayIndex2] += "" + ep1Account + "" + ","; // 客戶EP1
																		// ACCOUNT
				file2Line[arrayIndex2] += "64" + ","; // 台灣代碼 “64”
				file2Line[arrayIndex2] += amountExcludeTax + ","; // 退費金額 以 + 表示
				file2Line[arrayIndex2] += "" + EP1Code + "" + ","; // 作業對應EP1
																	// ACCOUNT
																	// CODE
				file2Line[arrayIndex2] += "0 ,"; // 作業數量
				file2Line[arrayIndex2] += "" + acctDno.getDebitNo() + "" + ","; // Debit
																				// note
																				// no
				file2Line[arrayIndex2] += "" + ",,"; // 固定字元-空白
				file2Line[arrayIndex2] += "\n\r"; // 固定字元-空白
				lineCounter2 = lineCounter2 + 1;
				arrayIndex2 = arrayIndex2 + 1;
			}

			for (int i = 0; i < arrayIndex2; i++) {
				saveFile_2.write(file2Line[i]);
				saveFile.write(file2Line[i]);
			}

			for (int i = 0; i < arrayIndex1; i++) {
				saveFile_1.write(file1Line[i]);
				saveFile.write(file1Line[i]);
			}
			String[] fileNames = new String[3];
			fileNames[0] = targetfileName;
			fileNames[1] = targetfileName1;
			fileNames[2] = targetfileName2;
			return fileNames;
		} catch (Exception e) {
			log.error("", e);
			throw e;
		} finally {
			try {
				if (saveFile_1 != null)
					saveFile_1.close();
				if (saveFile_2 != null)
					saveFile_2.close();
				if (saveFile != null)
					saveFile.close();
				if (ow != null)
					ow.close();
				if (ow1 != null)
					ow1.close();
				if (ow2 != null)
					ow2.close();
				if (fos != null)
					fos.close();
				if (fos1 != null)
					fos1.close();
				if (fos2 != null)
					fos2.close();

			} catch (Exception e) {
				log.error("", e);
			}
			saveFile = null;
			saveFile_1 = null;
			saveFile_2 = null;
			ow = null;
			ow1 = null;
			ow2 = null;
			fos = null;
			fos1 = null;
			fos2 = null;
		}

	}

}
