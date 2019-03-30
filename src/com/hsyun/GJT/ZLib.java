package com.hsyun.GJT;

import java.io.ByteArrayOutputStream;  
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;  
import java.util.zip.DeflaterOutputStream;  
import java.util.zip.Inflater;  
import java.util.zip.InflaterInputStream;  

/**
 * ZLib类，提供数据的压缩之类的方法，最后更新：2019-3-30<br>
 * 主要用于minecraft协议
 * @author caiwen
 * @version 0.1
 */
public class ZLib {
	/**
	 * 压缩byte[]
	 * @param data
	 * 要压缩的数据
	 * @return
	 * 压缩后的数据
	 * @throws IOException 
	 */
	public static byte[] compress(byte[] data) throws IOException {  
        byte[] output = new byte[0];  

        Deflater compresser = new Deflater();  

        compresser.reset();  
        compresser.setInput(data);  
        compresser.finish();  
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);  
        try {  
            byte[] buf = new byte[1024];  
            while (!compresser.finished()) {  
                int i = compresser.deflate(buf);  
                bos.write(buf, 0, i);  
            }  
            output = bos.toByteArray();  
        } finally {  
            bos.close();    
        }  
        compresser.end();  
        return output;  
    } 

    /**
     * 解压压缩后的数据
     * @param data
     * 要解压的数据
     * @return
     * 解压后的数据
     * @throws DataFormatException 
     * @throws IOException 
     */
    public static byte[] decompress(byte[] data) throws DataFormatException, IOException {  
        byte[] output = new byte[0];  

        Inflater decompresser = new Inflater();  
        decompresser.reset();  
        decompresser.setInput(data);  

        ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);  
        try {  
            byte[] buf = new byte[1024];  
            while (!decompresser.finished()) {  
                int i = decompresser.inflate(buf);  
                o.write(buf, 0, i);  
            }  
            output = o.toByteArray();  
        } finally {  
            o.close(); 
        }  

        decompresser.end();  
        return output;  
    }  
}
