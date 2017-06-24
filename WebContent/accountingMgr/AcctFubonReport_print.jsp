<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@page import="com.salmat.jbm.service.*"%>
<%@page import="com.painter.util.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.*"%>
<%@page import="java.util.*"%>
<%@page import="jxl.*"%>
<%@page import="jxl.write.*"%>
<%@page import="java.io.*"%>
<%@page import="net.mlw.vlh.ValueList"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JBM v2.0</title>
<link rel="stylesheet" type="text/css" href="css/backend.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="css/screen.css" />
<link rel="stylesheet" href="js/aqua/theme.css" type="text/css" />
<script src="js/lib/jquery.js" type="text/javascript"></script>
<script src="js/jquery.validate.js" type="text/javascript"></script>
</head>
<%
	AcctCustomerContractService acctCustomerContractService = AcctCustomerContractService
			.getInstance();
	AcctSum1Service acctSum1Service = AcctSum1Service.getInstance();
	JobCodeService jobCodeService = JobCodeService.getInstance();
	JobBagService jobBagService = JobBagService.getInstance();
	AcctSectionReceiverMappingService sectionReceiverMappingService = AcctSectionReceiverMappingService
			.getInstance();

	String debitNotesList = (String) request
			.getAttribute("debitNotesList");

	String NowTime = new SimpleDateFormat("yyyyMMdd_HHmmss")
			.format(new Date());

	String serverPath = request.getSession().getServletContext()
			.getRealPath("");
	String sourcefile = serverPath + "\\report\\fubonReport.xls";
	String targetfileName = "FubonReport_" + NowTime + ".xls";
	;

	String targetfile = serverPath + "\\pdf\\" + targetfileName;

	jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#,###0"); //用於Number的格式
	WritableCellFormat numberCellFormat = new jxl.write.WritableCellFormat(
			nf);
	numberCellFormat.setBorder(jxl.format.Border.ALL,
			jxl.format.BorderLineStyle.THIN);

	jxl.write.NumberFormat nf2 = new jxl.write.NumberFormat("#0.000"); //用於小數的格式
	WritableCellFormat priceCellFormat = new jxl.write.WritableCellFormat(
			nf2);
	priceCellFormat.setBorder(jxl.format.Border.ALL,
			jxl.format.BorderLineStyle.THIN);

	jxl.write.NumberFormat nf3 = new jxl.write.NumberFormat("#0.000000"); //用於小數的格式
	WritableCellFormat priceUnitCellFormat = new jxl.write.WritableCellFormat(
			nf3);
	priceUnitCellFormat.setBorder(jxl.format.Border.ALL,
			jxl.format.BorderLineStyle.THIN);

	WritableCellFormat StringCellFormat = new jxl.write.WritableCellFormat(); //用於字串的格式
	StringCellFormat.setBorder(jxl.format.Border.ALL,
			jxl.format.BorderLineStyle.THIN);

	WritableFont wfBOLD = new WritableFont(WritableFont.ARIAL, 10,
			WritableFont.BOLD);
	WritableCellFormat StringCellFormatSummary = new jxl.write.WritableCellFormat(); //用於字串的格式
	StringCellFormatSummary.setBorder(jxl.format.Border.ALL,
			jxl.format.BorderLineStyle.THIN);
	StringCellFormatSummary.setFont(wfBOLD);

	WritableCellFormat numberCellFormatSummary = new jxl.write.WritableCellFormat(
			nf);
	numberCellFormatSummary.setBorder(jxl.format.Border.ALL,
			jxl.format.BorderLineStyle.THIN);
	numberCellFormatSummary.setFont(wfBOLD);

	jxl.Workbook rw = jxl.Workbook.getWorkbook(new File(sourcefile));
	WritableWorkbook wwb = Workbook.createWorkbook(
			new File(targetfile), rw); //創建可寫工作薄
	WritableSheet ws = wwb.getSheet(0);

	List acctSum1List = acctSum1Service
			.findByDebitNoteList(debitNotesList);//.getAcctSum1s();

	String itemName = "";
	String[][] line = new String[20000][1000];
	String prevJobBagNo = "";
	String prevLpPcode = "";
	Integer lineCounter = -1;

	//0:請款明細部
	//1:部門別	
	//2:工作代號
	//3:cycledate	
	//4:報表名稱	
	//5:紙張物料
	//6:信封
	//7:來檔總筆數 = account
	//8:紙本
	//9:N	
	//10:電子
	//11:紙本 + 電子
	//12:失敗轉紙本		
	//13:印數			:accounting.fubon.printing.ChargeItemId=72,142,123,
	//14:列印單價	
	//15:摺紙			:accounting.fubon.origami.ChargeItemId=93, 
	//16:摺紙單價
	//17:裝封數量	:accounting.fubon.mailProcessing.ChargeItemId=83,147,146,
	//18:裝封單價
	//19:壓封郵簡	:accounting.fubon.pressureProcessing.ChargeItemId=81, 
	//20:壓封單價
	//21:紙張數量	:accounting.fubon.paper.ChargeItemId=184,185,186,187,188,189,190,191,192,193,194,195,149,163,164,129,
	//22:紙張單價
	//23:信封數量	:accounting.fubon.envelope.ChargeItemId=130,160,
	//24:信封單價	
	//25:掛號件數量	:accounting.fubon.manualProcessing.ChargeItemId=128,
	//26:掛號件單價
	//27:EDD數量	:accounting.fubon.manualProcessing.ChargeItemId=128,
	//28:EDD單價 
	//29:費用 
	
	//String printingChargeItemId = Util
			//.getString("accounting.fubon.printing.ChargeItemId");
	org.hibernate.SQLQuery query = HibernateSessionFactory.getSession().createSQLQuery("select a.id from acct_charge_item a, code c where c.id = a.fubonCode and c.code_type_name = 'FUBON_CODE' and c.code_key = ?");
    List<Object> ids = query.setParameter(0, "FUBON_PRINTING").list();
	String printingChargeItemId = ",";
	for(Object id : ids){
		printingChargeItemId += (id + ",");
	}
	/*
	if (!printingChargeItemId.startsWith(","))
		printingChargeItemId = "," + printingChargeItemId;
	if (!printingChargeItemId.endsWith(","))
		printingChargeItemId = printingChargeItemId + ",";
    */
	//String origamiChargeItemId = Util
			//.getString("accounting.fubon.origami.ChargeItemId");
	ids = query.setParameter(0, "FUBON_ORIGAMI").list();
	String origamiChargeItemId = ",";
	for(Object id : ids){
		origamiChargeItemId += (id + ",");
	}
	/*
	if (!origamiChargeItemId.startsWith(","))
		origamiChargeItemId = "," + origamiChargeItemId;
	if (!origamiChargeItemId.endsWith(","))
		origamiChargeItemId = origamiChargeItemId + ",";
    */
	
	//String mailProcessingChargeItemId = Util
			//.getString("accounting.fubon.mailProcessing.ChargeItemId");
    ids = query.setParameter(0, "FUBON_MAILPROCESSING").list();
	String mailProcessingChargeItemId = ",";
	for(Object id : ids){
		mailProcessingChargeItemId += (id + ",");
	}
	
	
	/*
	if (!mailProcessingChargeItemId.startsWith(","))
		mailProcessingChargeItemId = "," + mailProcessingChargeItemId;
	if (!mailProcessingChargeItemId.endsWith(","))
		mailProcessingChargeItemId = mailProcessingChargeItemId + ",";

	String pressureProcessingChargeItemId = Util
			.getString("accounting.fubon.pressureProcessing.ChargeItemId");
	if (!pressureProcessingChargeItemId.startsWith(","))
		pressureProcessingChargeItemId = ","
				+ pressureProcessingChargeItemId;
	if (!pressureProcessingChargeItemId.endsWith(","))
		pressureProcessingChargeItemId = pressureProcessingChargeItemId
				+ ",";
	*/
	ids = query.setParameter(0, "FUBON_PRESSUREPROCESSING").list();
	String pressureProcessingChargeItemId = ",";
	for(Object id : ids){
		pressureProcessingChargeItemId += (id + ",");
	}

	/*
	String paperChargeItemId = Util
			.getString("accounting.fubon.paper.ChargeItemId");
	if (!paperChargeItemId.startsWith(","))
		paperChargeItemId = "," + paperChargeItemId;
	if (!paperChargeItemId.endsWith(","))
		paperChargeItemId = paperChargeItemId + ",";
	*/
	ids = query.setParameter(0, "FUBON_PAPER").list();
	String paperChargeItemId = ",";
	for(Object id : ids){
		paperChargeItemId += (id + ",");
	}
	

	/*
	String envelopeChargeItemId = Util
			.getString("accounting.fubon.envelope.ChargeItemId");
	if (!envelopeChargeItemId.startsWith(","))
		envelopeChargeItemId = "," + envelopeChargeItemId;
	if (!envelopeChargeItemId.endsWith(","))
		envelopeChargeItemId = envelopeChargeItemId + ",";
	*/
	ids = query.setParameter(0, "FUBON_ENVELOP").list();
	String envelopeChargeItemId = ",";
	for(Object id : ids){
		envelopeChargeItemId += (id + ",");
	}
	
    /*
	String manualProcessingChargeItemId = Util
			.getString("accounting.fubon.manualProcessing.ChargeItemId");
	if (!manualProcessingChargeItemId.startsWith(","))
		manualProcessingChargeItemId = ","
				+ manualProcessingChargeItemId;
	if (!manualProcessingChargeItemId.endsWith(","))
		manualProcessingChargeItemId = manualProcessingChargeItemId
				+ ",";	
	*/
	ids = query.setParameter(0, "FUBON_MANUALPROCESSING").list();
	String manualProcessingChargeItemId = ",";
	for(Object id : ids){
		manualProcessingChargeItemId += (id + ",");
	}
	
	ids = query.setParameter(0, "FUBON_EDD").list();
	String eddChargeItemId = ",";
	for(Object id : ids){
		eddChargeItemId += (id + ",");
	}
			
	
	
	Double subTotal = 0.0;
	//紙張物料是不是已被設定過
	boolean lpCodeSetBefore = false;
	boolean haveMail = false;
	for (int i = 0; i < acctSum1List.size(); i++) {        
		AcctSum1 acctSum1 = (AcctSum1) acctSum1List.get(i);		 
		if (acctSum1.getSumQty() != 0) {
			boolean oriAcctDisplay = false; 
			JobCode jobcode = jobCodeService.findById(acctSum1
					.getIdfJobCodeNo());
			JobBag jobbag = jobBagService.findById(acctSum1
					.getIdfJobBagNo());
			Integer oriAccts = jobbag.getCtAcctsOri();
			String n = jobbag.getNotSent() == null? "" : jobbag.getNotSent() + "";
			String lpPcode = acctSum1.getLpPcode();
			//換行規則
			//1. jobBagNo不同時
			//2. lpPcode不同時
			if (!prevJobBagNo.equalsIgnoreCase(acctSum1
					.getIdfJobBagNo())) {
				if (lineCounter.compareTo(-1) != 0)
					line[lineCounter][29] = Double.toString(subTotal);
				lineCounter = lineCounter + 1;
				//跳行後，此旗號被設為false
				lpCodeSetBefore = false;
				prevJobBagNo = acctSum1.getIdfJobBagNo();
				prevLpPcode = lpPcode;
				subTotal = 0.0;
			} else if (lpPcode != null && !lpPcode.trim().equals("")
					&& !lpPcode.equals(prevLpPcode)) {
				if (lineCounter.compareTo(-1) != 0)
					line[lineCounter][29] = Double.toString(subTotal);

				lineCounter = lineCounter + 1;
				prevLpPcode = lpPcode;
				subTotal = 0.0;
			}
			line[lineCounter][0] = acctSum1.getAcctDno().getDebitNo();
			line[lineCounter][1] = jobbag.getCustomerDept();
			if(line[lineCounter][1] == null || line[lineCounter][1].trim().equals("")){
				line[lineCounter][1] = jobcode.getCustomerDept();
			}
			line[lineCounter][2] = acctSum1.getIdfJobBagNo();
			line[lineCounter][3] = Util.getDateFormat(acctSum1
					.getCycleDate());
			line[lineCounter][4] = acctSum1.getProgNm();

			if (acctSum1.getLpPcode() != null
					&& !"".equals(acctSum1.getLpPcode())) {
				//accSum1裡如果有載明lpCode，為第一優先
				lpCodeSetBefore = true;
				line[lineCounter][5] = acctSum1.getLpPcode();
			} else if (jobbag.getLpPcode1() != null && !lpCodeSetBefore) {
				line[lineCounter][5] = jobbag.getLpPcode1();
			} else if (null != jobbag.getLpinfoByIdfLpNo1()
					&& null != jobbag.getLpinfoByIdfLpNo1().getPcode1()
					&& !lpCodeSetBefore) {
				line[lineCounter][5] = jobbag.getLpinfoByIdfLpNo1()
						.getPcode1();
			}
			if (null != jobbag.getMpPrintCode())
				line[lineCounter][6] = jobbag.getMpPrintCode();
			else if (null != jobbag.getMpinfoByIdfMpNo1()
					&& null != jobbag.getMpinfoByIdfMpNo1()
							.getPrintCode())
				line[lineCounter][6] = jobbag.getMpinfoByIdfMpNo1()
						.getPrintCode();
			
			
			//富邦寄件			
			//9:紙本
			//mailType == "mail" && not edd && not from damage
			if ("mail".equals(jobbag.getMailType()) 
				&& (jobbag.getIsEdd() == null || !jobbag.getIsEdd()) 
			    && (jobbag.getFromDamage() == null || !jobbag.getFromDamage())) {
				line[lineCounter][9] = Integer.toString(jobbag.getAccounts());							
				haveMail = true;
				oriAcctDisplay = true;
			}
			//10:紙本 + 電子	       
	       //mailType == "mail" && edd && not from damage
			if ("mail".equals(jobbag.getMailType()) 
				&& (jobbag.getIsEdd() != null && jobbag.getIsEdd()) 
			    && (jobbag.getFromDamage() == null || !jobbag.getFromDamage())) {
				line[lineCounter][10] = Integer.toString(jobbag.getAccounts() * 2);				
				haveMail = true;
				oriAcctDisplay = true;
			}
			//11:電子	       
			//mailType != "mail" && edd && not from damage
			if ((jobbag.getMailType() == null || !"mail".equals(jobbag.getMailType())) 
				&& (jobbag.getIsEdd() != null && jobbag.getIsEdd()) 
			    && (jobbag.getFromDamage() == null || !jobbag.getFromDamage())) {
				line[lineCounter][11] = Integer.toString(jobbag.getAccounts());
				haveMail = true;
				oriAcctDisplay = true;
			}
			//12:失敗轉紙本
			//mailType == "mail" && not edd && from damage
			if ("mail".equals(jobbag.getMailType()) 
				&& (jobbag.getIsEdd() == null || !jobbag.getIsEdd()) 
			    && jobbag.getFromDamage() != null && jobbag.getFromDamage()) {
				line[lineCounter][12] = Integer.toString(jobbag.getAccounts());
				haveMail = true;
				oriAcctDisplay = true;
			}
			if(oriAcctDisplay){
				line[lineCounter][7] = oriAccts + "";
				line[lineCounter][8] = n;
			}
			
			//13:印數			:accounting.fubon.printing.ChargeItemId=72,142,123,
			//14:列印單價	
           if (printingChargeItemId.indexOf(","
					+ acctSum1.getAcctChargeItem().getId().toString()
					+ ",") >= 0) {
				line[lineCounter][13] = Integer.toString(acctSum1
						.getSumQty());
				line[lineCounter][14] = Double.toString(acctSum1
						.getUnitPrice());
				subTotal += acctSum1.getSumQty()
						* acctSum1.getUnitPrice();
			}

			//15:摺紙			:accounting.fubon.origami.ChargeItemId=93, 
			//16:摺紙單價
            if (origamiChargeItemId.indexOf(","
					+ acctSum1.getAcctChargeItem().getId().toString()
					+ ",") >= 0) {
				line[lineCounter][15] = Integer.toString(acctSum1
						.getSumQty());
				line[lineCounter][16] = Double.toString(acctSum1
						.getUnitPrice());
				subTotal += acctSum1.getSumQty()
						* acctSum1.getUnitPrice();
			}

			//17:裝封數量	:accounting.fubon.mailProcessing.ChargeItemId=83,147,146,
			//18:裝封單價
			if (mailProcessingChargeItemId.indexOf(","
					+ acctSum1.getAcctChargeItem().getId().toString()
					+ ",") >= 0) {
				line[lineCounter][17] = Integer.toString(acctSum1
						.getSumQty());
				line[lineCounter][18] = Double.toString(acctSum1
						.getUnitPrice());
				subTotal += acctSum1.getSumQty()
						* acctSum1.getUnitPrice();
			}

			//19:壓封郵簡	:accounting.fubon.pressureProcessing.ChargeItemId=81, 
			//20:壓封單價
			if (pressureProcessingChargeItemId.indexOf(","
					+ acctSum1.getAcctChargeItem().getId().toString()
					+ ",") >= 0) {
				line[lineCounter][19] = Integer.toString(acctSum1
						.getSumQty());
				line[lineCounter][20] = Double.toString(acctSum1
						.getUnitPrice());
				subTotal += acctSum1.getSumQty()
						* acctSum1.getUnitPrice();
			}

			//21:紙張數量	:accounting.fubon.paper.ChargeItemId=184,185,186,187,188,189,190,191,192,193,194,195,149,163,164,129,
			//22:紙張單價
			if (paperChargeItemId.indexOf(","
					+ acctSum1.getAcctChargeItem().getId().toString()
					+ ",") >= 0) {
				line[lineCounter][21] = Integer.toString(acctSum1
						.getSumQty());
				line[lineCounter][22] = Double.toString(acctSum1
						.getUnitPrice());
				subTotal += acctSum1.getSumQty()
						* acctSum1.getUnitPrice();
			}

			//23:信封數量	:accounting.fubon.envelope.ChargeItemId=130,160,
			//24:信封單價	
			if (envelopeChargeItemId.indexOf(","
					+ acctSum1.getAcctChargeItem().getId().toString()
					+ ",") >= 0) {
				line[lineCounter][23] = Integer.toString(acctSum1
						.getSumQty());
				line[lineCounter][24] = Double.toString(acctSum1
						.getUnitPrice());
				subTotal += acctSum1.getSumQty()
						* acctSum1.getUnitPrice();
			}

			//25:掛號件數量	:accounting.fubon.manualProcessing.ChargeItemId=128,
			//26:掛號件單價
			if (manualProcessingChargeItemId.indexOf(","
					+ acctSum1.getAcctChargeItem().getId().toString()
					+ ",") >= 0) {
				line[lineCounter][25] = Integer.toString(acctSum1
						.getSumQty());
				line[lineCounter][26] = Double.toString(acctSum1
						.getUnitPrice());
				subTotal += acctSum1.getSumQty()
						* acctSum1.getUnitPrice();
			}
			//27:EDD數量	:accounting.fubon.manualProcessing.ChargeItemId=128,
			//28:EDD單價
			if (eddChargeItemId.indexOf(","
					+ acctSum1.getAcctChargeItem().getId().toString()
					+ ",") >= 0) {
				line[lineCounter][27] = Integer.toString(acctSum1
						.getSumQty());
				line[lineCounter][28] = Double.toString(acctSum1
						.getUnitPrice());
				subTotal += acctSum1.getSumQty()
						* acctSum1.getUnitPrice();
			}
			
			//29:費用 
			//最後一筆
			if (i == acctSum1List.size() - 1){
				System.out.println(lineCounter + ":" + subTotal);
				line[lineCounter][29] = Double.toString(subTotal);
			}
		}

	}

	//列印明細, 根據 job_code + cycle date 合計
	Integer x = 0;
	Integer y = 0;
	int[] sumQty = new int[100];
	double[] sumPrice = new double[100];
	int minusLine = 0;
	for (int i = 0; i <= lineCounter; i++) {		
		x = 0;
		for (int j = 0; j < 30; j++) {
			if (null != line[i][j] && line[i][j] != " "
					&& line[i][j] != "" && j >= 13) {
				Double cellValue = Double.valueOf(line[i][j]);
				if (j == 29) {
					//費用合計
					jxl.write.Number n = new jxl.write.Number(x, y + 1,
							cellValue, priceCellFormat);
					//int qtyValue = Double.valueOf(line[i][j-1]).intValue();
					ws.addCell(n);
					sumPrice[x] += cellValue;
				} else if (j == (j / 2) * 2) {
					//金額
					jxl.write.Number n = new jxl.write.Number(x, y + 1,
							cellValue, priceUnitCellFormat);
					int qtyValue = Double.valueOf(line[i][j - 1])
							.intValue();
					ws.addCell(n);
					sumPrice[x] += cellValue * qtyValue;
				} else {
					//數量
					jxl.write.Number n = new jxl.write.Number(x, y + 1,
							cellValue, numberCellFormat);
					ws.addCell(n);
					sumQty[x] += cellValue.intValue();
				}
			} else {
				Label label = new Label(x, y + 1, line[i][j],
						StringCellFormat);
				ws.addCell(label);
			}
			x = x + 1;
		}
		y++;
	}
  
	Label label5 = new Label(5, lineCounter + 2, "作業數量小計",
			StringCellFormat);
	ws.addCell(label5);
	Label label6 = new Label(5, lineCounter + 3, "金額小計",
			StringCellFormat);
	ws.addCell(label6);

	for (int j = 13; j < 30; j++) {
		if (j == 29) {
			//費用合計
			jxl.write.Number nPrice = new jxl.write.Number(j,
					lineCounter + 3, sumPrice[j], priceCellFormat);
			ws.addCell(nPrice);
		} else if (j != (j / 2) * 2) {
			jxl.write.Number nQty = new jxl.write.Number(j,
					lineCounter + 2, sumQty[j], numberCellFormat);
			ws.addCell(nQty);
		} else {
			jxl.write.Number nPrice = new jxl.write.Number(j - 1,
					lineCounter + 3, sumPrice[j], priceCellFormat);
			ws.addCell(nPrice);
		}
	}
	//如果job不用寄送的，就刪除column
	if(!haveMail){
		for(int i = 12 ; i >= 7 ; i--)
		   ws.removeColumn(i);
	}

	wwb.write();
	wwb.close();
%>



<body>


	<table border="0" cellpadding="0" cellspacing="0" id="SysMiddleSet2"
		width="100%">

		<tr>
			<td height="28" background="images/green_title_bg.jpg"
				class="black12b"
				style="background-repeat: no-repeat; padding-left: 10px">系統訊息</td>
		</tr>


		<tr>
			<td height="28" style="padding-top: 10px">Fubon Report 檔名(<%=targetfileName%>):
				<a href="pdf/<%=targetfileName%>" target="new">開啟</a></td>
		</tr>



	</table>


</body>
</html>
<%
	HibernateSessionFactory.closeSession();
%>


