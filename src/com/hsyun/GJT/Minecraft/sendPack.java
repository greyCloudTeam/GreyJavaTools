package com.hsyun.GJT.Minecraft;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import com.hsyun.GJT.ZLib;

import java.util.Arrays;

/**
 * minecraft数据包制作类，最后更新：2019-3-30
 * @author caiwen
 * @version 0.1
 */
public class sendPack {
	/**
	 * 要发送到的流
	 */
	public DataOutputStream sendStream=null;
	private ByteArrayOutputStream b;
	
	/**
	 * 当前数据包的DataOutputStream
	 */
	public DataOutputStream thisPack;
	
	/**
	 * 构造一个数据包
	 * @param sendStream
	 * 服务端或客户端的DataOutputStream
	 * @param id
	 * 数据包的id
	 * @throws IOException
	 */
	public sendPack(DataOutputStream sendStream,int id) throws IOException {
		this.sendStream=sendStream;
		b = new ByteArrayOutputStream();
		thisPack = new DataOutputStream(b);
		common.writeVarInt(thisPack,id);
	}
	
	public void writeVarInt(int data) throws IOException {
		common.writeVarInt(thisPack, data);
	}
	public void writeString(String data) throws IOException {
		writeVarInt(data.length());
		thisPack.writeBytes(data);
	}
	public void writeChat(String data) throws IOException{
		String text=data;
		writeString(text);
	}
	public void writeBoolean(boolean value) throws IOException {
		if(value) {
			thisPack.writeByte(0x01);
		}else {
			thisPack.writeByte(0x00	);
		}
	}
	
	/**
	 * 获取数据包的大小
	 * @return
	 */
	public int getSize() {
		return thisPack.size();
	}
	
	/**
	 * 将当前数据包发送到构造数据包时提供的sendStream中
	 * @param compress
	 * 是否压缩，不知道的话填false
	 * @param maxSize
	 * 压缩的最大大小，仅compress为true时有效，为false时请填-1
	 * @throws IOException
	 */
	public void sendPack(boolean compress,int maxSize) throws IOException {
		byte[] data=b.toByteArray();
		if(compress) {
			ByteArrayOutputStream b ;
			DataOutputStream handshake;
			b= new ByteArrayOutputStream();
			handshake = new DataOutputStream(b);
			if(data.length>=maxSize) {
				common.writeVarInt(handshake,data.length);
				byte[] lengthData=b.toByteArray();
				data=ZLib.compress(data);
				common.writeVarInt(sendStream,lengthData.length+data.length);
				sendStream.write(lengthData);
				sendStream.write(data);
			}else {
				common.writeVarInt(handshake,0);
				byte[] lengthData=b.toByteArray();
				common.writeVarInt(sendStream,lengthData.length+data.length);
				sendStream.write(lengthData);
				sendStream.write(data);
			}
		}else {
			common.writeVarInt(sendStream,data.length);
			sendStream.write(data);
		}
	}
}