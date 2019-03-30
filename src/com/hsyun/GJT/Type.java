package com.hsyun.GJT;

import java.nio.ByteBuffer;
import java.util.Iterator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * 关于数据类型的类，主要是数据类型之间的转换，最后更新：2019-3-30
 * @author caiwen
 * @version 0.1
 */
public class Type {
	private static ByteBuffer buffer = ByteBuffer.allocate(8); 
	public static float bytes2Float(byte[] b) {  
		ByteBuffer buf=ByteBuffer.allocateDirect(4);
		buf.put(b);
		buf.rewind();
		float f2=buf.getFloat();       
		return f2;
	}
	public static double bytes2Double(byte[] arr) {
		long value = 0;
		for (int i = 0; i < 8; i++) {
			value |= ((long) (arr[i] & 0xff)) << (8 * i);
		}
		return Double.longBitsToDouble(value);
	}
	public static long bytes2Long(byte[] bytes) {
		// 将byte[] 封装为 ByteBuffer 
        ByteBuffer buffer = ByteBuffer.wrap(bytes,0,8);
        return buffer.getLong();  
    }
	public static byte[] long2Bytes(long x) {
        buffer.putLong(0, x);
        return buffer.array();
    }
	public static short bytes2Short(byte[] b) {
        short s = 0;
        short s0 = (short) (b[0] & 0xff);
        short s1 = (short) (b[1] & 0xff);
        s1 <<= 8;
        s = (short) (s0 | s1);
        return s;
    }
	public static int bytes2Int(byte[] data) {
        if (data == null || data.length < 4) {
            return 0xDEADBEEF;
        }
        return ByteBuffer.wrap(data, 0, 4).getInt();
    }
	
	/**
	 * minecraft协议专用，将int转换为Fixed-point numbers
	 * @param value
	 * @return
	 */
	public static double int2FPN(int value) {
		return (double)(value / 32.0D);
	}
	
	/**
	 * minecraft协议专用，将bytes转换为Fixed-point numbers
	 * @param value
	 * @return
	 */
	public static double bytes2FPN(byte value) {
		return (double)(value / 32.0D);
	}
	
	/**
	 * minecraft协议专用
	 * @param data
	 * @return
	 */
	public static String toChat(String data) {
		String value="";
		JsonParser json=new JsonParser();
        JsonElement part5 = json.parse(data);
        JsonElement temp=part5.getAsJsonObject().get("extra");
        if(temp!=null) {
        	JsonArray part7=temp.getAsJsonArray();
            Iterator it=part7.iterator();
            while(it.hasNext()){
                JsonElement e = (JsonElement)it.next();
                try{
                	value+=e.getAsJsonObject().get("text").getAsString();
                }catch(Exception err) {
                	value+=e.getAsString();
                }
            }
        }
        return value;
	}
}
