package com.hsyun.GJT.Minecraft;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.zip.DataFormatException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.hsyun.GJT.Type;
import com.hsyun.GJT.ZLib;

/**
 * minecraft协议的数据包解析类，最后更新：2019-3-30<br>
 * 使用构造函数初始化类<br>
 * 初始化以后可以读取数据包里的数据<br>
 * 详情请参考http://github.com/greyCloudTeam/MCShell<br>
 * 此类在mc1.7.2版本下测试通过<br>
 * 此类很不完善，仅供参考
 * @author caiwen
 * @version 0.1
 */
public class acceptPack {
	/**
	 * 数据包的数据（解压后的，如果带压缩）
	 */
	public byte[] data=null;
	
	/**
	 * 数据包的id
	 */
	public int id=-1;
	
	/**
	 * 当前数据读取到的位置
	 */
	public int point=0;
	
	public byte[] thenData=null;
	
	/**
	 * 用服务器发送过来的数据来初始化类
	 * @deprecated
	 * @param data
	 * 服务器发送过来的数据
	 * @throws IOException 
	 */
	public acceptPack(byte[] data) throws IOException {
		this.data=data;
		id=readVarInt();
	}
	
	/**
	 * 用DataInputStream来初始化类
	 * @param in
	 * 服务端或客户端的DataInputStream
	 * @param compress
	 * 是否启用压缩（不知道的话填false）
	 * @throws IOException
	 * @throws DataFormatException 
	 */
	public acceptPack(DataInputStream in,boolean compress) throws IOException, DataFormatException {
			int length=common.readVarInt(in);
			this.data=new byte[length];
			in.readFully(data);
			this.thenData=data;
			if(compress) {
				int trueLength=readVarInt();
				if(trueLength!=0) {
					byte[] temp=getAllData();
					byte[] then=ZLib.decompress(temp);
					this.data=then;
					point=0;
				}
			}
			id=readVarInt();
	}
	
	/**
	 * Fixed-point numbers
	 * @return
	 */
	public int readIntA() {
		double a=readDouble();
		return (int)(a*32.0D);
	}
	
	public String readString() {
		String re="";
		try {
			int length=readVarInt();
			byte[] by=new byte[length];
			readFully(by);
			re=new String(by);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return re;
	}
	
	/**
	 * 获取剩下的所有数据
	 * @return
	 * 剩下的所有数据
	 */
	public byte[] getAllData() {
		byte[] data=new byte[this.data.length-point];
		readFully(data);
		return data;
	}
	
	public int readInt() {
		byte[] temp=new byte[4];
		readFully(temp);
		return Type.bytes2Int(temp);
	}
	
	public short readShort() {
		byte[] temp=new byte[2];
		readFully(temp);
		return Type.bytes2Short(temp);
	}
	
	public long readLong() {
		byte[] temp=new byte[8];
		readFully(temp);
		return Type.bytes2Long(temp);
	}
	
	public double readDouble() {
		byte[] temp=new byte[8];
		readFully(temp);
		return Type.bytes2Double(temp);
	}
	
	/**
	 * 读一个无符号的byte（char？)
	 * @return
	 */
	public int readUnsignedByte() {
		Byte b=readByte();
		Integer i=b.intValue();
		Integer i_trans=i&0xFF;
		return i_trans;
	}
	
	public boolean readBoolean() {
		byte temp=readByte();
		if(temp==0x00) {
			return false;
		}
		if(temp==0x01) {
			return true;
		}
		return false;
	}
	
	public float readFloat() {
		byte[] temp=new byte[4];
		readFully(temp);
		return Type.bytes2Float(temp);
	}
	
	public byte readByte() {
		byte re=data[point];
		point++;
		return re;
	}
	
	public int readVarInt() throws IOException {
		int numRead = 0 ;
	    int result = 0 ;
	    byte read;
	    do{
	    	read = readByte();
	        int value = (read & 0b01111111);
	        result |= (value << ( 7 * numRead));

	        numRead ++;
	        if (numRead > 5 ) {
	            throw new RuntimeException ("VarInt too big");
	        }
	     } while ((read & 0b10000000) != 0);

	     return result;
	}
	
	public int readVarInt(int num) throws IOException {
		int numRead = 0 ;
	    int result = 0 ;
	    byte read;
	    do{
	    	read = readByte();
	    	num++;
	        int value = (read & 0b01111111);
	        result |= (value << ( 7 * numRead));

	        numRead ++;
	        if (numRead > 5 ) {
	            throw new RuntimeException ("VarInt too big");
	        }
	     } while ((read & 0b10000000) != 0);

	     return result;
	}
	
	/**
	 * 用后面的数据将byte数组填充
	 * @param by
	 * byte数组
	 */
	public void readFully(byte[] by) {
		for(int i=0;by.length>i;i++) {
			by[i]=readByte();
		}
	}
}
