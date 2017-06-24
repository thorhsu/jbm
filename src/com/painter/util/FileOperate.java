package com.painter.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.io.FileUtils;
import org.jfree.util.Log;


public class FileOperate extends FileUtils{
	
	   public FileOperate() {
	   }
	  
	   public static void newFolder(String folderPath) {
	       try {
	           String filePath = folderPath;
	           filePath = filePath.toString();
	           java.io.File myFilePath = new java.io.File(filePath);
	           if (!myFilePath.exists()) {
	               myFilePath.mkdir();
	           }
	       }
	       catch (Exception e) {
	           e.printStackTrace();
	       }
	   }
	  
	   public static void newFile(String filePathAndName, String fileContent) {
	       try {
	           String filePath = filePathAndName;
	           filePath = filePath.toString();
	           File myFilePath = new File(filePath);
	           if (!myFilePath.exists()) {
	               myFilePath.createNewFile();
	           }
	           FileWriter resultFile = new FileWriter(myFilePath);
	           PrintWriter myFile = new PrintWriter(resultFile);
	           String strContent = fileContent;
	           myFile.println(strContent);
	           resultFile.close();
	       }
	       catch (Exception e) {
	           e.printStackTrace();
	       }
	   }
	  
	   public static boolean delFile(String filePathAndName) {
	       try {
	           String filePath = filePathAndName;
	           filePath = filePath.toString();
	           java.io.File myDelFile = new java.io.File(filePath);
	           boolean ret = myDelFile.delete();
	           return ret;
	       }
	       catch (Exception e) {
	    	   Log.error("", e);
	           e.printStackTrace();
	           return false;
	       }

	   }
	  
	   public static void delFolder(String folderPath) {
	       try {
	           delAllFile(folderPath); 
	           String filePath = folderPath;
	           filePath = filePath.toString();
	           java.io.File myFilePath = new java.io.File(filePath);
	           myFilePath.delete(); 
	       }
	       catch (Exception e) {
	           e.printStackTrace();
	       }
	   }
	  
	   public static void delAllFile(String path) {
	       File file = new File(path);
	       if (!file.exists()) {
	           return;
	       }
	       if (!file.isDirectory()) {
	           return;
	       }
	       String[] tempList = file.list();
	       File temp = null;
	       for (int i = 0; i < tempList.length; i++) {
	           if (path.endsWith(File.separator)) {
	               temp = new File(path + tempList[i]);
	           }
	           else {
	               temp = new File(path + File.separator + tempList[i]);
	           }
	           if (temp.isFile()) {
	               temp.delete();
	           }
	           if (temp.isDirectory()) {
	               delAllFile(path+"/"+ tempList[i]);//先删除目錄中的檔案 
	               delFolder(path+"/"+ tempList[i]);//再删除目錄 
	           }
	       }
	   }
	   public static boolean copyFile(String oldPath, String newPath) {
		   try {
			   FileUtils.copyFile(new File(oldPath), new File(newPath));
			   return true;
		   } catch (IOException e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
			   return false;
		   }
	   }
	   
	   public static void copyFolder(String oldPath, String newPath) {
	       try {
	           (new File(newPath)).mkdirs(); //如果目錄不存在就建立新目錄
	           File a=new File(oldPath);
	           String[] file=a.list();
	           File temp=null;
	           for (int i = 0; i < file.length; i++) {
	               if(oldPath.endsWith(File.separator)){
	                   temp=new File(oldPath+file[i]);
	               }
	               else{
	                   temp=new File(oldPath+File.separator+file[i]);
	               }
	               if(temp.isFile()){
	                   FileInputStream input = new FileInputStream(temp);
	                   FileOutputStream output = new FileOutputStream(newPath + "/" +
	                           (temp.getName()).toString());
	                   byte[] b = new byte[1024 * 5];
	                   int len;
	                   while ( (len = input.read(b)) != -1) {
	                       output.write(b, 0, len);
	                   }
	                   output.flush();
	                   output.close();
	                   input.close();
	               }
	               if(temp.isDirectory()){//如果是子目錄
	                   copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]);
	               }
	           }
	       }
	       catch (Exception e) {
	    	   
	           e.printStackTrace();
	       }
	   }
	  
	   public static boolean moveFile(String oldPath, String newPath) {
		   boolean ret1 = true;
		   boolean ret2 = true;
		   
	       ret1 = copyFile(oldPath, newPath);
	       
	       if (ret1) {
	    	   ret2 = delFile(oldPath);
	    	   if (! ret2) {
	    		   //刪除失敗, 檔案被咬住, 該檔不處理, 刪除copy 過去的檔案
	    		   System.out.println("刪除失敗, 檔案被咬住" + oldPath);
	    		   delFile(newPath);
	    		   return false;
	    	   } else {
	    		   return true;
	    	   }
	       } else {
	    	   return false;
	       }
	       

	   }
	  
	   public static void moveFolder(String oldPath, String newPath) {
	       copyFolder(oldPath, newPath);
	       delFolder(oldPath);
	   }
	}
