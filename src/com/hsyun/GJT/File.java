package com.hsyun.GJT;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 对文件进行读取，操作等，最后更新：2019-3-30
 * @author Caiwen
 * @version 0.1
 */
public class File {
	/**
	 * 读取一个文本文件（UTF8编码)
	 * @param filePath
	 * 文件的绝对路径，获取程序的运行路径请看com.hsyun.GJT.Code
	 * @return
	 * 文件的内容
	 * @throws FileNotFoundException
	 * 文件不存在
	 */
	public static String readFile_string(String filePath) throws IOException{  
		String content="";//读到的文本
	    String encoding = "UTF-8";  //文件编码
	    java.io.File file = new java.io.File(filePath);  //获得file对象
	    //如果不存在
	    if (!file.exists()) {  
            throw new FileNotFoundException(filePath);  
        }
	    //获得流
	    InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);  
	    BufferedReader bufferedReader = new BufferedReader(read);  
	    String lineTxt = null;  //每行的内容
	    while ((lineTxt = bufferedReader.readLine()) != null) {  
	    	content=content+lineTxt+"\n";//逐行读取
	    }  
	    read.close();  
	    return content;  
	}  
	
	/**
	 * 读小文件
	 * @param filePath
	 * 文件的绝对路径，获取程序的运行路径请看com.hsyun.GJT.Code
	 * @return
	 * 文件的byte
	 * @throws FileNotFoundException
	 * 文件不存在
	 */
	public static byte[] readFile_byte_small(String filePath) throws IOException {  
		  
		java.io.File f = new java.io.File(filePath);  
        if (!f.exists()) {  
            throw new FileNotFoundException(filePath);  
        }  
  
        FileChannel channel = null;  
        FileInputStream fs = null;  
        try {  
            fs = new FileInputStream(f);  
            channel = fs.getChannel();  
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());  
            while ((channel.read(byteBuffer)) > 0) {  
                // do nothing  
                // System.out.println("reading");  
            }  
            return byteBuffer.array();   
        } finally {    
            channel.close();   
            fs.close();   
        }  
    }  
	
	/**
	 * 写一个文件（覆盖)
	 * @param path
	 * 文件的绝对路径，获取程序的运行路径请看com.hsyun.GJT.Code
	 * @param context
	 * 要写入的内容
	 * @param create
	 * 如果为true，则文件不存在时自动创建<br>
	 * 如果为false，则文件不存在时不会创建，还有可能抛出FileNotFoundException
	 * @throws FileNotFoundException
	 * 文件不存在
	 */
	public static void writeFile_text(String path,String context,boolean create) throws IOException {
	    java.io.File f = new java.io.File(path);
	    OutputStreamWriter writer = null;
	    BufferedWriter bw = null;
	    if(!getFileObj(path).exists()&&create) {
	    	getFileObj(path).createNewFile();
	    }
	    try {
	        OutputStream os = new FileOutputStream(f);
	        writer = new OutputStreamWriter(os);
	        bw = new BufferedWriter(writer);
	        bw.write(context);
	        bw.flush();
	    } finally {
	        bw.close();
	    }
	}
	
	/**
	 * 获取文件的对象
	 * @param path
	 * 文件的路径
	 * @return
	 * java.io.File对象
	 */
	public static java.io.File getFileObj(String path) {
		return new java.io.File(path);
	}
}
