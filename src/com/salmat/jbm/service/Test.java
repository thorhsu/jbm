package com.salmat.jbm.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import jxl.HeaderFooter;
import jxl.HeaderFooter.Contents;
import jxl.SheetSettings;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ArrayUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mozilla.javascript.edu.emory.mathcs.backport.java.util.Arrays;
import org.nfunk.jep.JEP;


import com.salmat.jbm.hibernate.HibernateSessionFactory;
import com.salmat.jbm.hibernate.JobCode;

public class Test {

	/**
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 * 
	 */

	public static void updateJobCode() throws IOException {
		Session session = null;
		Transaction tx = null;
		FileReader fr = null;
		BufferedReader br = null;
		try {
			session = HibernateSessionFactory.getSession();
			tx = session.beginTransaction();
			fr = new FileReader("D:/tmp/email.txt");
			br = new BufferedReader(fr);
			String text = null;
			while ((text = br.readLine()) != null && !text.trim().equals("")) {
				text = text.trim();
				System.out.println(text);
				JobCode jobCode = (JobCode) session.get(JobCode.class, text);
				if (jobCode != null) {
					jobCode.setSelectByWeek(true);
					jobCode.setTransferType(186);
					jobCode.setWeekDay1(true);
					jobCode.setWeekDay2(true);
					jobCode.setWeekDay3(true);
					jobCode.setWeekDay4(true);
					jobCode.setWeekDay5(true);
					jobCode.setWeekDay6(true);
					jobCode.setWeekDay7(true);
					
					jobCode.setCycle01(false);
					jobCode.setCycle02(false);
					jobCode.setCycle03(false);
					jobCode.setCycle04(false);
					jobCode.setCycle05(false);
					jobCode.setCycle06(false);
					jobCode.setCycle07(false);
					jobCode.setCycle08(false);
					jobCode.setCycle09(false);
					jobCode.setCycle10(false);
					jobCode.setCycle11(false);
					jobCode.setCycle12(false);
					jobCode.setCycle13(false);
					jobCode.setCycle14(false);
					jobCode.setCycle15(false);
					jobCode.setCycle16(false);
					jobCode.setCycle17(false);
					jobCode.setCycle18(false);
					jobCode.setCycle19(false);
					jobCode.setCycle20(false);
					jobCode.setCycle21(false);
					jobCode.setCycle22(false);
					jobCode.setCycle23(false);
					jobCode.setCycle24(false);
					jobCode.setCycle25(false);
					jobCode.setCycle26(false);
					jobCode.setCycle27(false);
					jobCode.setCycle28(false);
					jobCode.setCycle29(false);
					jobCode.setCycle30(false);
					jobCode.setCycle31(false);
					jobCode.setCycleEnd(false);
					jobCode.setIsEnabledContract(false);
					
					//session.update(jobCode);
					System.out.println("update");
				}
			}
			if (tx != null)
				tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			if (session != null)
				session.close();
			if (br != null)
				br.close();
			if (fr != null)
				fr.close();
		}

	}

	public static void main(String[] args) throws InterruptedException {
/*
		String test = "\"\",abc,aaa,123";
		String [] split = test.replaceAll("\"", "").split(",");
		System.out.println(split.length);
		System.out.println(split[0].equals(""));
		
		System.out.println("---------------------------------------------");
		//System.out.println(new Double(1.0).doubleValue() == 1);
		String testStr = "5759_20130605_102829";
		String matchStr = "[0-9]*_20[0-9][0-9](0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])_([0-1][0-9]|2[0-3])([0-5][0-9])([0-5][0-9])";
		System.out
				.println( null != testStr
						&& (testStr.matches(matchStr)));
		
		/*
		 * 
		 * 

		String[][] testarray = { { "test11", "test12", "test13" },
				{ "test21", "test22", "test23" } };
		System.out.println(testarray.length);

		List<String[]> list = Arrays.asList(testarray);

		testarray = (String[][]) ArrayUtils.remove(testarray, 0);
		for (int i = 0; i < testarray.length; i++) {
			String[] test2 = (String[]) testarray[i];
			for (int j = 0; j < test2.length; j++) {
				System.out.println(test2[j]);
			}
		}

		System.out.println(2 + "."
				+ ("edd_dm-1.pdf".matches("edd_dm-[0-9]\\.pdf")));
*/
		/*
		 * 
		 * try { String s = "$"; s = URLEncoder.encode(s, "utf-8");
		 * 
		 * System.out.println(s); s = URLEncoder.encode(s, "utf-8");
		 * System.out.println(s); String test = "H4RS7azT"; Base64 base64 = new
		 * Base64(); System.out.println(new
		 * String(base64.encode(test.getBytes()))); System.out.println(new
		 * String(base64.encode(test.getBytes())).equals("SDRSUzdhelQ="));
		 * System.out.println("test".substring(4));
		 * System.out.println("test".substring(0, 0));
		 * System.out.println("test"); } catch (UnsupportedEncodingException e)
		 * { // TODO Auto-generated catch block e.printStackTrace(); }
		 */
		/*
		 * NumberFormat nf = new DecimalFormat("#0.000");;
		 * System.out.println(nf.format(123456789));
		 * System.out.println(nf.format(123456789.2));
		 * System.out.println(nf.format(0)); List list = new ArrayList();
		 * list.add("1"); list.add("2"); list.add("3");
		 * System.out.println("--------------------------------------"); for(int
		 * i = 0 ; i < list.size() ; i++){ System.out.println(i);
		 * System.out.println(list.size()); list.remove(i);
		 * System.out.println("--------------------------------------"); }
		 * for(int i = 0 ; i < list.size() ; i++){
		 * System.out.println(list.get(i)); }
		 * 
		 * Calendar cal = Calendar.getInstance(); Calendar testCal = (Calendar)
		 * cal.clone(); cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)
		 * - 1, cal.get(Calendar.DATE)); System.out.println(cal.getTime());
		 * System.out.println(testCal.getTime());
		 */ 
		 
		 JEP jep = new JEP(); 
		 String expr =	 "if($pages > 0 && $accounts > 0,($accounts + $pages), ($pages * 4))";
		 //expr = "abs($pages)";
		 //expr = "round($accounts*100/30) / 100"; 
		 jep.addVariable("$accounts", 0);
		 jep.addVariable("$pages", 5);
		  //jep.addVariable("$pages", 1); 
		 try { jep.addStandardFunctions();
		  jep.parseExpression(expr);
		  
		  double value = jep.getValue(); 
		  System.out.println("value:" + value);
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		  
		  
		 
	}

	public static void demo() {
		File file = new File("c:/tmp/test.xls");
		try {
			// 建立工作表
			WritableWorkbook workbook = Workbook.createWorkbook(file);
			// 建立分頁
			WritableSheet sheet = workbook.createSheet("First Sheet", 0);
			// 取得分頁環境設定(如:標頭/標尾,分頁,欄長,欄高等設定
			SheetSettings settings = sheet.getSettings();
			// 設置打印縮放比例
			settings.setScaleFactor(70);
			// 建立標頭
			HeaderFooter header = new HeaderFooter();
			// 設定分頁標頭
			settings.setHeader(header);
			// 建立分頁標頭左區塊
			Contents headerLeft = header.getLeft();
			// 加入預設日期
			headerLeft.appendDate();
			// 建立分頁標頭左區塊
			Contents headerCentre = header.getCentre();
			// 加入自定內容
			headerCentre.append("標題");
			// 建立分頁標頭左區塊
			Contents headerRight = header.getRight();
			// 加入預設頁碼
			headerRight.appendPageNumber();
			// 設定第一欄及第二欄長度
			sheet.setColumnView(0, 30);
			sheet.setColumnView(1, 40);
			// 設定第一列高度, 其他採用預設值
			sheet.setRowView(0, 500);
			// 設定字型
			// 字形為ARIAL, 大小為12, 字色為紅色, 斜體
			WritableFont font = new WritableFont(WritableFont.ARIAL, 12);
			font.setItalic(true);
			font.setColour(jxl.format.Colour.RED);
			WritableCellFormat cellFormat1 = new WritableCellFormat(font);
			// 字形為ARIAL, 大小為12, 字色為紅色, 正常
			font = new WritableFont(WritableFont.ARIAL, 14);
			font.setItalic(false);
			font.setColour(jxl.format.Colour.BLUE);
			WritableCellFormat cellFormat2 = new WritableCellFormat(font);
			// 字形為ARIAL, 大小為16, 粗體
			WritableCellFormat cellFormat3 = new WritableCellFormat(
					new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD));
			cellFormat3.setAlignment(jxl.format.Alignment.RIGHT);
			sheet.addCell(new Label(0, 0, "FirstColumns", cellFormat3));
			sheet.addCell(new Label(1, 0, "SecondColumns", cellFormat3));
			// 設置每頁打印的表頭
			settings.setPrintTitlesRow(0, 0);
			for (int i = 1; i < 100; i++) {
				// 資料加入分頁表中
				sheet.addCell(new jxl.write.Number(0, i, i, cellFormat1));
				sheet.addCell(new jxl.write.Number(1, i, i * 1000, cellFormat2));
				if ((i % 30) == 0) {
					// 每20列則換頁
					sheet.addRowPageBreak(i);
				}
			}
			// 工作表關閉
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
