package com.hsyun.GJT.Api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.bind.DatatypeConverter;

import com.hsyun.GJT.Safe;

/**
 * 网易云音乐接口
 * @author caiwen
 * @version 0.1
 */
public class neteaseMusic {
	/**
	 * 网易自己的密钥
	 */
	public static String key="7246674226682325323F5E6544673A51";
	
	/**
	 * 网易云音乐接口数据加密
	 * @param data
	 * 要加密的数据
	 * @return
	 * 加密后的数据
	 * @throws Exception
	 */
	public static String encode_data(String data) throws Exception {
		String part1=Safe.ase_128_ecb(data, Safe.toStringHexTest(key));//ase加密
		byte[] part2=Safe.base64_decode(part1);//base64加密
		String part3=DatatypeConverter.printHexBinary(part2);//再变成16进制
		return part3;
	}
	
	/**
	 * 网易云音乐接口请求
	 * @param data
	 * 请求的数据
	 * @return
	 * 服务器返回的结果
	 * @throws Exception
	 */
		public static String get_result(String data) throws Exception {
			PrintWriter out = null;
	        BufferedReader in = null;
	        String result = "";
	        try {
	            URL realUrl = new URL("http://music.163.com/api/linux/forward");
	            // 打开连接
	            URLConnection conn = realUrl.openConnection();
	            // 设置http头
	            conn.setRequestProperty("accept", "*/*");
	            conn.setRequestProperty("connection", "Keep-Alive");
	            conn.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            conn.setRequestProperty("Referer","http://music.163.com/");
	            conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
	            
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            out = new PrintWriter(conn.getOutputStream());
	            out.print("eparams="+data);
	            out.flush();
	            in = new BufferedReader(
	                    new InputStreamReader(conn.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        }finally{
	            if(out!=null){
	            	out.close();
	            }
	            if(in!=null){
	            	in.close();
	            }
	        }
			return result;
		}
		
		/**
		 * 搜音乐
		 * @param key
		 * 搜索的内容
		 * @param page
		 * 搜索结果的页数
		 * @return
		 * 服务器返回的数据，json类型，自己去解析
		 * @throws Exception
		 */
		public static String search(String key,int page) throws Exception {
			String data = "{\"method\":\"POST\",\"url\":\"http:\\/\\/music.163.com\\/api\\/cloudsearch\\/pc\",\"params\":{\"s\":\""+key+"\",\"type\":1,\"offset\":"+(page*10-10)+",\"limit\":10}}";
			String part1=Safe.ase_128_ecb(data, Safe.toStringHexTest(key));
			byte[] part2=Safe.base64_decode(part1);
	        String part3=DatatypeConverter.printHexBinary(part2);
	        String part4=get_result(part3);
	        return part4;
		}
		
		/**
		 * 通过歌曲id获取歌曲的信息
		 * @param id
		 * 歌曲的id
		 * @return
		 * 歌曲的信息
		 * @throws Exception
		 */
		public static String getInfo(String id) throws Exception {
			String data = "{\"method\":\"GET\",\"url\":\"http:\\/\\/music.163.com\\/api\\/song\\/detail\",\"params\":{\"id\":\""+id+"\",\"ids\":\"["+id+"]\"}}" ;
			String part1=Safe.ase_128_ecb(data, Safe.toStringHexTest(key));
			byte[] part2=Safe.base64_decode(part1);
	        String part3=DatatypeConverter.printHexBinary(part2);
	        String part4=get_result(part3);
	        return part4;
		}
		
		/**
		 * 通过歌曲id获取歌词
		 * @param id
		 * 歌曲的id
		 * @return
		 * 歌词的信息
		 * @throws Exception
		 */
		public static String music_getLyric(String id) throws Exception {
			String data = "{\"method\":\"GET\",\"url\":\"http:\\/\\/music.163.com\\/api\\/song\\/lyric\",\"params\":{\"id\":\""+id+"\",\"lv\":1}}";
	        String part1=Safe.ase_128_ecb(data, Safe.toStringHexTest(key));
			byte[] part2=Safe.base64_decode(part1);
	        String part3=DatatypeConverter.printHexBinary(part2);
	        String part4=get_result(part3);
	        return part4;
		}
}
