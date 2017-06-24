package com.salmat.jbm.mappingrule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;

import com.painter.util.FileOperate;
import com.painter.util.Util;
import com.salmat.jbm.bean.AmexFormater;
import com.salmat.jbm.bean.CiticardFormater;
import com.salmat.jbm.bean.DamageFormater;
import com.salmat.jbm.bean.LogisticFormater;
import com.salmat.jbm.bean.OneGoldFormater;
import com.salmat.jbm.hibernate.Code;
import com.salmat.jbm.hibernate.HibernateSessionFactory;
import com.salmat.jbm.hibernate.JobBag;
import com.salmat.jbm.hibernate.JobBagSplite;
import com.salmat.jbm.hibernate.JobCode;
import com.salmat.jbm.hibernate.SysLog;
import com.salmat.jbm.service.AmexService;
import com.salmat.jbm.service.EmployeeService;
import com.salmat.jbm.service.JobBagService;
import com.salmat.jbm.service.JobBagSpliteService;
import com.salmat.jbm.service.JobCodeService;
import com.salmat.jbm.service.SchedulerService;
import com.salmat.jbm.service.SyslogService;

public class DamageReader {

	/**
	 * Log4j instance.
	 */
	private final static Logger log = Logger.getLogger(DamageReader.class);
    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
	/* Constants */
	private static final DamageReader instance = new DamageReader();
	private static final AmexService amexService = AmexService.getInstance();
	private static final SyslogService syslogService = SyslogService.getInstance();
	private static final JobBagService jobbagService = JobBagService.getInstance();
	private static final JobBagSpliteService jobbagSpliteService = JobBagSpliteService.getInstance();	

	public DamageReader() {

	}

	public static DamageReader getInstance() {
		return instance;
	}
	

	
	public static void readfile() {
		String currentDate = sdf.format(new Date());
		
		String folderString = Util.getString("damage.folder");
		String processFolderString = Util.getString("damage.folder") +"\\processing\\" ;
		String backupFolderString = Util.getString("damage.folder") +"\\backup\\";	


        File folder = new File(folderString);


        if (folder.isDirectory()) {
            String[] in_files = folder.list();
            for (int i=0; i<in_files.length;i++) {
                BufferedReader reader = null;
                if ( in_files[i].substring(in_files[i].length()-3, in_files[i].length()).equalsIgnoreCase("CSV") ) {
	                Boolean retMoving = FileOperate.moveFile(folderString+in_files[i], processFolderString+in_files[i]);
	                File file = new File(processFolderString + in_files[i]);		
	                String jobbagNo="";
	                if (retMoving) {
		                try {
		                    reader = new BufferedReader(new FileReader(file));
		                    String text = null;
		                    DamageFormater formater = null;

		                    while ((text = reader.readLine()) != null) {
								try {
									formater = new DamageFormater();
									String[] tokens = text.split(",");
									if (tokens.length < 6) {
										SysLog syslog = new SysLog();
										syslog.setLogType("CREATE_JOBBAG_DAMAGE");
										syslog.setSubject("DAMAGE格式錯誤");
										syslog.setMessageBody(file.getName() + ":" + text);
										syslog.setIsException(true);
										Calendar calendar = Calendar
												.getInstance();
										Date now = new Date();
										calendar.setTime(now);
										syslog.setCreateDate(calendar.getTime());
										syslogService.save(syslog);
										continue;
									}

									formater.setFilename(tokens[0].trim());// Filename
									String processDate_str = tokens[1].trim();// ProcessDate
									Date processDate = new Date();
									try {
										SimpleDateFormat sdf1 = new SimpleDateFormat(
												"yyyy/MM/dd");
										processDate = sdf1
												.parse(processDate_str);
									} catch (ParseException e) {
										e.printStackTrace();
										log.error("日期格式錯誤"
												+ formater.toString());
									}
									formater.setProcessDate(processDate);

									formater.setAccounts(tokens[2].trim()); // Accounts
									formater.setPages(tokens[3].trim());// Pages
									formater.setOrgAccounts(tokens[4].trim()); // OrgAccounts
									formater.setOrgPages(tokens[5].trim());// OrgPages

									Long lastModified = file.lastModified();
									Date date = new Date(lastModified);
									formater.setReceiveDate(date); // 以系統時間 作為
																	// Receive
																	// Date
									formater.setCycleDate(date); // 以系統時間 作為
																	// CycleDate
																	// Date
									formater.setLineData(text);

									// 檢察是否重複轉入
									String logFilename = in_files[i];
									String lineData = formater.getLineData();
									Boolean retCheckExistJobBagSplite = false;
									if (null != logFilename
											&& logFilename.length() > 0
											&& lineData.length() > 0) {
										retCheckExistJobBagSplite = jobbagService
												.checkExistLogFilenameLineData(
														logFilename, lineData);
										if (retCheckExistJobBagSplite) {
											log.info("重複轉入, log File Name: "
													+ logFilename + "Line:"
													+ lineData);
										}
									}
									try {
										new Integer(formater.getAccounts());
										new Integer(formater.getPages());
										new Integer(formater.getOrgPages());
										new Integer(formater.getOrgAccounts());
									} catch (Exception e) {
										SysLog syslog = new SysLog();
										syslog.setLogType("CREATE_JOBBAG_DAMAGE");
										syslog.setSubject("DAMAGE格式錯誤");
										syslog.setMessageBody(file.getName() + ":" + text);
										syslog.setIsException(true);
										Calendar calendar = Calendar
												.getInstance();
										Date now = new Date();
										calendar.setTime(now);
										syslog.setCreateDate(calendar.getTime());
										syslogService.save(syslog);
										continue;
									}

									// 尋找原工單(有Damage )
									JobBag jobbag = amexService
											.findJobBagByDamage(formater);
									HibernateSessionFactory.getSession()
											.clear();
									HibernateSessionFactory.getSession()
											.getTransaction().begin();

									if (null == jobbag) {
										SysLog syslog = new SysLog();
										syslog.setLogType("CREATE_JOBBAG_DAMAGE");
										syslog.setSubject("DAMAGE無對應工單 ");
										syslog.setMessageBody(formater
												.toString());
										syslog.setIsException(true);
										Calendar calendar = Calendar
												.getInstance();
										Date now = new Date();
										calendar.setTime(now);
										syslog.setCreateDate(calendar.getTime());
										syslogService.save(syslog);
									} else if (!retCheckExistJobBagSplite) {
										Integer accounts = Integer
												.parseInt(formater
														.getAccounts());
										Integer pages = Integer
												.parseInt(formater.getPages());
										String afpName = formater.getFilename();
										jobbag.setDamageCount(accounts);
										jobbag.setHasDamage(true);
										// Date cycleDate =
										// formater.getCycleDate();
										Date receiveDate = formater
												.getReceiveDate();
										jobbagService.save(jobbag); // 回壓原工單
																	// DamageCount
										JobCode jobCode = jobbag.getJobCode();
										boolean transferToNew = false;
										if (jobCode != null
												&& jobCode.getDamageJobCodeNo() != null
												&& !"".equals(jobCode
														.getDamageJobCodeNo()
														.trim())) {
											jobCode = (JobCode) HibernateSessionFactory
													.getSession()
													.get(JobCode.class,
															jobCode.getDamageJobCodeNo()
																	.trim());
											if (jobCode != null)
												transferToNew = true;
										}

										logFilename = in_files[i];

										// 產生 DAMAGE 工單
										JobBag jobbagDamage = new JobBag();

										java.util.Date defaultValue = null;
										DateConverter converter = new DateConverter(
												defaultValue);
										ConvertUtils.register(converter,
												java.util.Date.class);
										BeanUtils.copyProperties(jobbagDamage,
												jobbag);

										String damageNo = jobbag.getJobBagNo()
												+ "D";
										// 有多筆damage時的處理
										List<JobBag> jobBagDamages = jobbagService
												.findJobBagNosByLike(damageNo);

										if (jobBagDamages != null
												&& jobBagDamages.size() > 0) {
											int damageCount = jobBagDamages
													.size() + 1;
											damageNo = jobbag.getJobBagNo()
													+ "D" + damageCount;
										}
										jobbagDamage.setJobBagNo(damageNo);
										jobbagDamage.setJobBagStatus("INIT");
										jobbagDamage.setJobCode(jobbag
												.getJobCode());
										jobbagDamage.setAccounts(accounts);
										jobbagDamage.setSpliteCount(1);
										jobbagDamage.setPages(pages);

										String printType = null;
										Code code = jobbag
												.getCodeByLpCodePrintType();
										if (code != null) {
											printType = code.getCodeValueTw();
										}
										int doubleOrSingle = 1;
										if (printType != null
												&& printType.indexOf("雙面") >= 0)
											doubleOrSingle = 2;
										int sheets = pages / doubleOrSingle;
										if (pages % doubleOrSingle == 1)
											sheets++;

										jobbagDamage.setSheets(sheets); // damage工單的紙張數
										// jobbagDamage.setCycleDate(cycleDate);
										// cycle date用原來工單就行
										jobbagDamage
												.setReceiveDate(receiveDate);
										jobbagDamage.setAfpName(afpName);
										jobbagDamage
												.setLogFilename(logFilename);
										jobbagDamage.setLogFileSeq("001");
										jobbagDamage.setFeeder2(0);
										jobbagDamage.setFeeder3(0);
										jobbagDamage.setFeeder4(0);
										jobbagDamage.setFeeder5(0);
										jobbagDamage.setTray1(0);
										jobbagDamage.setTray2(0);
										jobbagDamage.setTray3(0);
										jobbagDamage.setTray4(0);
										jobbagDamage.setJobBagSplites(null); // 避免
																				// Exception:
																				// Found
																				// shared
																				// references
																				// to
																				// a
																				// collection:
																				// com.salmat.jbm.hibernate.JobBag.jobBagSplites
										jobbagDamage.setHasDamage(false);
										jobbagDamage.setIsDamage(true);
										jobbagDamage.setCreateDate(new Date());

										// 如果不是damage轉成其它的jobCode時，就是原來的jobbagNO
										// 後面加D的方式生成
										if (!transferToNew) {
											jobbagService.save(jobbagDamage);
											// 產生 DAMAGE 工單 分檔
											JobBagSplite jobbagSpliteDamage = new JobBagSplite();
											jobbagSpliteDamage
													.setJobBag(jobbagDamage);
											jobbagSpliteDamage
													.setJobBagSpliteNo(jobbagDamage
															.getJobBagNo()
															.toUpperCase()
															+ "001");
											jobbagSpliteDamage
													.setAfpFilename(afpName);
											jobbagSpliteDamage
													.setLineData(formater
															.getLineData());
											jobbagSpliteDamage
													.setLogFilename(logFilename);
											jobbagSpliteDamage
													.setReceiveDate(receiveDate);
											jobbagSpliteDamage
													.setLpAccountSeqBegin(1);
											jobbagSpliteDamage
													.setLpAccountSeqEnd(accounts);
											jobbagSpliteDamage
													.setLpAccountSeqDiff(accounts);
											jobbagSpliteDamage
													.setLpPagesSeqBegin(1);
											jobbagSpliteDamage
													.setLpPagesSeqEnd(pages);
											jobbagSpliteDamage
													.setLpPagesSeqDiff(pages);
											jobbagSpliteService
													.save(jobbagSpliteDamage);
										} else {
											JobBagService jbService = JobBagService
													.getInstance();
											jobCode.setLogFileSeq("001");
											jobbagDamage = jbService
													.createNewJobBag(
															jobCode,
															accounts,
															pages,
															null,
															afpName,
															jobbag.getCycleDate(),
															receiveDate,
															logFilename, 0, 0,
															0, 0, 0, 0, 0, 0,
															0, 0, 0, 0,
															lineData, 0, 0, 0,
															0, 0, 0, 0);
											jobbagDamage.setFromDamage(true);
											jobbagService.save(jobbagDamage);
										}

										if (null != jobbagDamage) {
											log.info("完成建立DAMAGE工單 "
													+ jobbagDamage
															.getJobBagNo()
													+ ":" + formater.toString());
											SysLog syslog = new SysLog();
											syslog.setLogType("CREATE_JOBBAG_DAMAGE");
											syslog.setSubject("完成建立DAMAGE工單 ");
											syslog.setMessageBody(jobbagDamage
													.getJobBagNo()
													+ ":"
													+ formater.toString());
											Calendar calendar = Calendar
													.getInstance();
											Date now = new Date();
											calendar.setTime(now);
											syslog.setCreateDate(calendar
													.getTime());
											syslogService.save(syslog);
										}
									}
									HibernateSessionFactory.getSession()
											.getTransaction().commit();

								} catch (Exception e) {
									log.error("", e);
									e.printStackTrace();
									SysLog syslog = new SysLog();
									syslog.setLogType("CREATE_JOBBAG_DAMAGE");
									syslog.setSubject("DAMAGE工單產生發生錯誤");
									syslog.setMessageBody(file.getName() + ":" + text);
									syslog.setIsException(true);
									Calendar calendar = Calendar.getInstance();
									Date now = new Date();
									calendar.setTime(now);
									syslog.setCreateDate(calendar.getTime());
									syslogService.save(syslog);
									continue;
								}
			                }
		                } catch (FileNotFoundException e) {
		                	log.error("", e);
		                    e.printStackTrace();
		                } catch (IOException e) {
		                	log.error("", e);
		                    e.printStackTrace();
		                } catch (Exception e) {
		                	log.error("", e);
		                    e.printStackTrace();		                    
		                } finally {
		                    try {
		                        if (reader != null) {
		                            reader.close();
		                        }
		                    } catch (IOException e) {
		                        e.printStackTrace();
		                    }
		                    if(HibernateSessionFactory.getSession().isOpen())
		                       HibernateSessionFactory.closeSession();		                    
		                }
			            //將processing 資料, 搬到 backup 
			             SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			             String backupFileName = file.getName() + "."+formatter.format(new Date());
			             Boolean ret = FileOperate.moveFile(processFolderString+file.getName(), backupFolderString+backupFileName);
		             } //
	           }// for 
	       }
        }
	}
	
	private static String getAfpFileName(String wriFullName) {
		String retAfpFileName="";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(wriFullName));
            String readLine = null;
            String text = null;
            AmexFormater formater = null;
            while ((readLine = reader.readLine()) != null) {
            	text = text + readLine;
            }
            
            String patternStr = "/laser/(.+).afp";
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(text);
            boolean matchFound = matcher.find();
            int k = 0;
            if (matchFound) {
              for(int i = 0; i <= matcher.groupCount(); i++) {
                String groupStr = matcher.group(i);
                if(i == 1)
                	retAfpFileName = groupStr;
              }

            }            

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return retAfpFileName;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final SchedulerService scheduleService = SchedulerService.getInstance();
		DamageReader logisticReader = new DamageReader();
		logisticReader.readfile();
	}

}
