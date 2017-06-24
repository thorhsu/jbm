package com.painter.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import jxl.SheetSettings;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.painter.filter.SetParamValFilter;

public class GenerateMaterialUsageReport {
	private static Logger log = Logger
			.getLogger(GenerateMaterialUsageReport.class);

	public static List<String> generateReport(
			Map<String, List<Object[]>> queryResult, String dateBegin,
			String dateEnd) {
		String serverPath = SetParamValFilter.getRealPath("");
		Set<String> keySet = queryResult.keySet();
		TreeSet<String> keyTreeSet = new TreeSet<String>(keySet);
		List<String> fileNameList = new ArrayList<String>();
		//SimpleDateFormat cycleSdf = new SimpleDateFormat("MM/dd");

		String nowTime = new SimpleDateFormat("yyyyMMdd_HHmmssSSS")
				.format(new Date());
		String sourcefile = serverPath + "\\report\\materialReport.xls";
		String targetfileName = "";
		targetfileName = "All_materialReport_" + nowTime + ".xls";
		String targetfile = serverPath + "\\pdf\\" + targetfileName;
		jxl.Workbook rw = null;
		WritableWorkbook wwb = null;
		int customerCounter = 1;
		try {
			rw = jxl.Workbook.getWorkbook(new File(sourcefile));
			if(keySet.size() > 1)
			   wwb = Workbook.createWorkbook(new File(targetfile), rw); // 創建可寫工作薄
			
			for (String key : keyTreeSet) {
				String custNm = key.substring(key.indexOf("#_#") + 3);
				String custNo = key.substring(0, key.indexOf("#_#"));
				if (keySet.size() == 1){
					//只有一個客戶時，檔案名稱使用客戶名不同
					targetfileName = custNo + "_materialReport_" + nowTime
							+ ".xls";
					targetfile = serverPath + "\\pdf\\" + targetfileName;
					wwb = Workbook.createWorkbook(new File(targetfile), rw); // 創建可寫工作薄
				
				}
				try {
					
					jxl.write.NumberFormat nf = new jxl.write.NumberFormat(
							"#,###0"); // 用於Number的格式
					WritableCellFormat numberCellFormat = new jxl.write.WritableCellFormat(
							nf);
					numberCellFormat.setBorder(jxl.format.Border.ALL,
							jxl.format.BorderLineStyle.THIN);

					jxl.write.NumberFormat nf2 = new jxl.write.NumberFormat(
							"#0.000000"); // 用於小數的格式
					WritableCellFormat priceCellFormat = new jxl.write.WritableCellFormat(
							nf2);
					priceCellFormat.setBorder(jxl.format.Border.ALL,
							jxl.format.BorderLineStyle.THIN);

					jxl.write.NumberFormat nf3 = new jxl.write.NumberFormat(
							"#0.000"); // 用於整數/小數的格式
					WritableCellFormat priceCellFormat3 = new jxl.write.WritableCellFormat(
							nf3);
					priceCellFormat3.setBorder(jxl.format.Border.ALL,
							jxl.format.BorderLineStyle.THIN);

					WritableCellFormat StringCellFormat = new jxl.write.WritableCellFormat(); // 用於字串的格式
					StringCellFormat.setBorder(jxl.format.Border.ALL,
							jxl.format.BorderLineStyle.THIN);

					WritableFont wfBOLD = new WritableFont(WritableFont.ARIAL,
							10, WritableFont.BOLD);
					WritableCellFormat StringCellFormatSummary = new jxl.write.WritableCellFormat(); // 用於字串的格式
					StringCellFormatSummary.setBorder(jxl.format.Border.ALL,
							jxl.format.BorderLineStyle.THIN);
					StringCellFormatSummary.setFont(wfBOLD);

					WritableCellFormat StringCellFormatSummaryNoBorder = new jxl.write.WritableCellFormat(); // 用於字串的格式無格線
					StringCellFormatSummaryNoBorder.setBorder(
							jxl.format.Border.NONE,
							jxl.format.BorderLineStyle.NONE);
					StringCellFormatSummaryNoBorder.setFont(wfBOLD);

					WritableCellFormat numberCellFormatSummary = new jxl.write.WritableCellFormat(
							nf);
					numberCellFormatSummary.setBorder(jxl.format.Border.ALL,
							jxl.format.BorderLineStyle.THIN);
					numberCellFormatSummary.setFont(wfBOLD);

					WritableCellFormat numberCellFormatSummaryNoBorder = new jxl.write.WritableCellFormat(
							nf);
					numberCellFormatSummaryNoBorder.setBorder(
							jxl.format.Border.NONE,
							jxl.format.BorderLineStyle.NONE);
					numberCellFormatSummaryNoBorder.setFont(wfBOLD);

					
					WritableSheet ws = null;					
                    //複製模板工作表
					wwb.copySheet(0, custNo, customerCounter );
					ws = wwb.getSheet(customerCounter );
					SheetSettings sheetSettings = ws.getSettings();
					sheetSettings.setFitToPages(false);					
					sheetSettings.setScaleFactor(98);                    
					sheetSettings.setPrintTitlesRow(0, 3);

					Label custName = new Label(0, 0, custNm);
					Label queryDates = new Label(1, 2, dateBegin + "~"
							+ dateEnd);
					ws.addCell(custName);
					ws.addCell(queryDates);

					List<Object[]> result = queryResult.get(key);
					String jobCodeNo = "";
					int index = 3; // 計算目前寫到第幾行，從第四行開始
					int pagesTotal = 0;
					int acctsTotal = 0;
					int sheetsTotal = 0;
					int counter = 0;
					boolean first = true;
					for (Object[] line : result) {
						counter++;
						index++;
						String job_code_no = (String) line[0] == null ? ""
								: ((String) line[0]).trim();
						String prog_nm = (String) line[1] == null ? ""
								: (String) line[1];
						String p_code1 = (String) line[2] == null ? ""
								: (String) line[2];
						Integer pages = (Integer) line[3] == null ? 0
								: (Integer) line[3];
						Integer sheets = (Integer) line[4] == null ? 0
								: (Integer) line[4];
						String print_code = (String) line[5] == null ? ""
								: (String) line[5];
						Integer accounts = (Integer) line[6] == null ? 0
								: (Integer) line[6];
						String cycle_Date = (String) line[7] == null ? ""
								: (String) line[7];												
						

						if (!jobCodeNo.equals(job_code_no)) {
							if (jobCodeNo.equals(""))
								first = true;
							else
								first = false;
							if (!first) {
								// 如果換了job_code，要算合計
								Label labelTotal = new Label(2, index, "合計：",
										StringCellFormatSummary);
								jxl.write.Number pageTotal = new jxl.write.Number(
										3, index, pagesTotal, numberCellFormat);
								jxl.write.Number sheetTotal = new jxl.write.Number(
										4, index, sheetsTotal, numberCellFormat);
								jxl.write.Number acctTotal = new jxl.write.Number(
										6, index, acctsTotal, numberCellFormat);								
								ws.addCell(labelTotal);
								ws.addCell(pageTotal);
								ws.addCell(sheetTotal);
								ws.addCell(acctTotal);
								index++;
							}
							jobCodeNo = job_code_no;
							Label jobCode = new Label(0, index, jobCodeNo,
									StringCellFormat);
							ws.addCell(jobCode);
							index++;
							pagesTotal = 0;
							acctsTotal = 0;
							sheetsTotal = 0;
						}

						Label cycleDate = new Label(0, index, cycle_Date,
								StringCellFormat);
						Label progNm = new Label(1, index, prog_nm,
								StringCellFormat);
						Label pCode1 = new Label(2, index, p_code1,
								StringCellFormat);
						jxl.write.Number pageInd = new jxl.write.Number(3,
								index, pages, numberCellFormat);
						pagesTotal += pages;
						jxl.write.Number sheetInd = new jxl.write.Number(4,
								index, sheets, numberCellFormat);
						sheetsTotal += sheets;						
						Label printCode = new Label(5, index, print_code,
								StringCellFormat);
						jxl.write.Number acctsInd = new jxl.write.Number(6,
								index, accounts, numberCellFormat);
						acctsTotal += accounts;
						ws.addCell(cycleDate);
						ws.addCell(progNm);
						ws.addCell(pCode1);
						ws.addCell(pageInd);
						ws.addCell(sheetInd);
						ws.addCell(printCode);
						ws.addCell(acctsInd);
						
						// 如果到最後面了，加入合計
						if (counter == result.size()) {
							index++;
							Label labelTotal = new Label(2, index, "合計：",
									StringCellFormatSummary);
							jxl.write.Number pageTotal = new jxl.write.Number(
									3, index, pagesTotal, numberCellFormat);
							jxl.write.Number sheetTotal = new jxl.write.Number(
									4, index, sheetsTotal, numberCellFormat);
							jxl.write.Number acctTotal = new jxl.write.Number(
									6, index, acctsTotal, numberCellFormat);
							ws.addCell(labelTotal);
							ws.addCell(pageTotal);
							ws.addCell(sheetTotal);
							ws.addCell(acctTotal);
						}
					}
				}catch (WriteException e) {
					log.error("", e);
					e.printStackTrace();
				}
				customerCounter++;
			}// end of for loop
			wwb.removeSheet(0); //移除模板sheet
			wwb.write();
			fileNameList.add(targetfileName);
		} catch (Exception e) {
			log.error("", e);
			e.printStackTrace();
		} finally {
			if (wwb != null) {
				try {
					wwb.close();
				} catch (WriteException e) {
					log.error("", e);
					e.printStackTrace();
				} catch (IOException e) {
					log.error("", e);
					e.printStackTrace();
				}
				wwb = null;
			}
			if (rw != null) {
				rw.close();
			}
		}
		return fileNameList;
	}

}
