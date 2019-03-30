package com.hsyun.GJT;

/**
 * 提供关于数学的功能，最后更新：2019-3-30
 * @author caiwen
 * @version 0.1
 */
public class Math {
	/**
	 * 判断一个文本是否为数字
	 * @param text
	 * 要判断的文本
	 * @return
	 * 是数字返回true，反之返回false
	 */
	public static boolean isNum(String text){
		boolean dotLock=false;
		boolean minLock=false;
		
	    for(int a=0;a<text.length();a++){
	    	String th=text.substring(a, a+1);
	    	int b=Integer.valueOf(th.charAt(0));
	        if(th.equals(".")) {
	        	if(a==0||a==text.length()-1||dotLock) {
	        		return false;
	        	}
	        	dotLock=true;
	        	continue;
	        }
	        if(th.equals("-")) {
	        	if(a!=0||a==text.length()-1) {
	        		return false;
	        	}
	        	minLock=true;
	        	continue;
	        }
	        if(b<48 || b>57)
	            return false;
	    }
	   return true;
	}     
}
