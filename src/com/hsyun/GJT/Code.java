package com.hsyun.GJT;

/**
 * 获取程序的运行信息等，最后更新：2019-3-30
 * @author Caiwen
 * @version 0.1
 */
public class Code {
	
	/**
	 * 获取程序所在的目录（区别getPath_Runtime)<br>
	 * 末尾带/或\<br>
	 * 一般在eclipse等ide下，getPath和getPath_Runtime是相等的，只有打包成jar文件后才会有区别<br>
	 * 没有进行过多测试，出现bug请到http://github.com/greyCloudTeam/GreyJavaTools汇报问题
	 * @param mainClass
	 * 主类的类类型（其他的类也可以）比如Code.isJar(Main.class)<br>
	 * Main是你主类的名称
	 * @return
	 * 程序所在的目录
	 */
	public static String getPath(Class mainClass) {
		//获取是否是jar运行
		if(isJar(mainClass)) {
			//System.out.println(mainClass.getProtectionDomain().getCodeSource().getLocation().getPath());
			String path[]=System.getProperty("java.class.path").split(java.io.File.separator);
			String end="";
			if(path.length==1) {
				return java.io.File.separator;
			}
			for(int i=0;i<path.length;i++) {
				if(i==path.length-1) {
					break;
				}else {
					end+=path[i]+java.io.File.separator;
				}
			}
			return end;
		}
		return System.getProperty("user.dir")+java.io.File.separator;
	}
	
	/**
	 * 获取程序的执行目录（区别getPath)<br>
	 * 末尾带/或\<br>
	 * 一般在eclipse等ide下，getPath和getPath_Runtime是相等的，只有打包成jar文件后才会有区别<br>
	 * 没有进行过多测试，出现bug请到http://github.com/greyCloudTeam/GreyJavaTools汇报问题
	 * @return
	 * 程序的执行目录
	 */
	public static String getPath_Runtime() {
		return System.getProperty("user.dir")+java.io.File.separator;
	}
	
	/**
	 * 判断程序是否是在<br>
	 * @param mainClass
	 * 主类的类类型（其他的类也可以）比如Code.isJar(Main.class)<br>
	 * Main是你主类的名称
	 * @return
	 */
	public static boolean isJar(Class mainClass) {
		String a=mainClass.getResource("").toString();
		if(a.substring(0, 3).equals("jar")) {
			return true;
		}
		return false;
	}
}
