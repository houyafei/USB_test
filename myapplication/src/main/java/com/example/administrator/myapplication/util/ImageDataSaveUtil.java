package com.example.administrator.myapplication.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ImageDataSaveUtil {


	String ImagePath;

 
	private static int count = 0 ;


	/**
	 * 字符串
	 */
	public static void saveLogData(ArrayList<Integer> mData){
		
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddhhmmss") ;
		Date date = new Date() ;
		String strDate = f.format(date);
		
		//1.解析数据
		//暂不需要
		
		//2.创建文件File
		File file = new File("/sdcard/philLog01/"+ strDate +".csv");
		//3.创建输出流，并且在后面添加数据
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file,true);
			String msg = "";
			//4.1,构造一个字符串
			for (int data: mData){

				msg += String.valueOf(data)+",";


			}
			count++  ;
			
			
			try {
				fos.write(msg.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				ImageDataSaveUtil.printExceptionMsg(e);
			}
		}

		//提示数据已经保存↑↑↑
		//Toast.makeText(context, "OK", 100).show();
	}

	/**
	 * 字符串
	 */
	public static void saveLogData(String str, String filename){
		
		//1.创建文件夹
		File folder = null ;
		try {
			folder = new File("/sdcard/philLog01/");
			if(!folder.exists()){
				folder.mkdir();
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//2.创建文件File
		File file = new File("/sdcard/philLog01/"+ filename +".txt");
		//3.创建输出流，并且在后面添加数据
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file,true);
			String[] strdata = str.split("s+");
			String msg = "";
			//4.1,构造一个字符串
			for (String string :
					strdata) {
				msg = msg+"\n";
			}

			try {
				fos.write(msg.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * 间数据保存为  数据表格
	 */
	public static boolean saveDataAsExcel(ArrayList<HashMap<String, ArrayList<Integer>>> listDatas){




		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddhhmmss") ;
		Date date = new Date() ;
		String strDate = f.format(date);

		//1.解析数据
		//暂不需要

		//2.创建文件File
		File file = new File("/sdcard/philLog01/"+ strDate +".csv");
		//3.创建输出流，并且在后面添加数据
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file,true);
			String msg = "";
			//4.1,构造一个字符串
			for (HashMap<String, ArrayList<Integer>> map: listDatas){
				for (int i=0;i<3;i++)
				for (Integer j:map.get(String.valueOf(i))) {
					msg += String.valueOf(j)+",";
				}
				msg +="\n" ;
			}

			try {
				fos.write(msg.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				ImageDataSaveUtil.printExceptionMsg(e);
			}
		}

		return false;
	}

	/**
	 * 打印异常信息到指定文件中。
	 */
	public static void  printExceptionMsg(Exception e){
		
		PrintStream newOut = null ;
		try {
			newOut = new PrintStream(new BufferedOutputStream(new FileOutputStream("/sdcard/ExceptionLog.txt")));
			//-----------------☝↑-------------------☝↑---------------------☝↑
			e.printStackTrace(newOut) ;
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
			newOut.close();
		}
	}

}
