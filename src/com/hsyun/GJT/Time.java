package com.hsyun.GJT;

import java.util.*;

/**
 * 关于时间的类，最后更新：2019-3-30
 * @author caiwen
 * @version 0.1
 */
public class Time {
	/**
	 * 获取当前的年份
	 * @return
	 * 当前的年份
	 */
	public static int getYear() {
		Calendar now = Calendar.getInstance();  
		return now.get(Calendar.YEAR);
	}
	
	/**
	 * 获取当前的月份
	 * @return
	 * 当前的月份
	 */
	public static int getMonth() {
		Calendar now = Calendar.getInstance();  
		return (now.get(Calendar.MONTH) + 1);
	}
	
	/**
	 * 获取当前日
	 * @return
	 * 当前日
	 */
	public static int getDay() {
		Calendar now = Calendar.getInstance();  
		return now.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 获取当前的小时数
	 * @return
	 * 当前的小时数
	 */
	public static int getHour() {
		Calendar now = Calendar.getInstance();  
		return now.get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 获取当前的分钟数
	 * @return
	 * 当前的分钟数
	 */
	public static int getMinute() {
		Calendar now = Calendar.getInstance();  
		return now.get(Calendar.MINUTE);
	}
	
	/**
	 * 获取当前的秒数
	 * @return
	 * 当前的秒数
	 */
	public static int getSecond() {
		Calendar now = Calendar.getInstance();  
		return now.get(Calendar.SECOND);
	}
}
