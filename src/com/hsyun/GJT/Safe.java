package com.hsyun.GJT;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 此类提供各种加密方式，最后更新：2019-3-30
 * @author caiwen
 * @version 0.1
 */
public class Safe {
	public static byte[] encryptByPublicKey(byte[] plainData, PublicKey publicKey)
            throws Exception {
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(plainData);
    }
	/**
	 * ase128加密，和php的ase128加密是一样的
	 * @param sSrc
	 * 要加密的数据
	 * @param sKey
	 * 16位的密钥
	 * @return
	 * 加密后数据的base64
	 * @throws Exception
	 */
		public static String ase_128_ecb(String sSrc, String sKey) throws Exception {
	        if (sKey == null) {
	        	throw new RuntimeException("key不能为空");
	        }
	        if (sKey.length() != 16) {
	        	throw new RuntimeException("key不等于16位");
	        }
	        byte[] raw = sKey.getBytes("utf-8");
	        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
	        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
	        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

	        return Base64.getEncoder().encodeToString(encrypted);
	    }
		/**
		 * 将string转成hex，仿php的pack函数
		 * @param s
		 * 要转换的文本
		 * @return
		 * 转换后的文本
		 */
		public static String toStringHexTest(String s) { 
			  byte[] baKeyword = new byte[s.length() / 2]; 
			  for (int i =0; i < baKeyword.length; i++) { 
			   try { 
			     baKeyword[i]=(byte)(0xff&Integer.parseInt(s.substring(i*2,i*2+2),16)); 
			   }catch (Exception e) { 
			     e.printStackTrace(); 
			   } 
			  } 
			  try { 
				    s= new String(baKeyword,"gbk");// UTF-16le:Not 
				  } catch (Exception e1) { 
				    e1.printStackTrace(); 
				  } 
				  return s; 
			}
		
		/**
		 * base64解码
		 * @param text
		 * 被解码的base64文本
		 * @return
		 * 解码后的数据
		 */
		public static byte[] base64_decode(String text) {
			return Base64.getDecoder().decode(text);
		}
		
		/**
		 * md5加密
		 * @param plainText
		 * 要加密的文本
		 * @return
		 * 加密后的文本
		 */
		public static String getMD5(String plainText) {
	        try {
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            md.update(plainText.getBytes());
	            byte b[] = md.digest();

	            int i;

	            StringBuffer buf = new StringBuffer("");
	            for (int offset = 0; offset < b.length; offset++) {
	                i = b[offset];
	                if (i < 0)
	                    i += 256;
	                if (i < 16)
	                    buf.append("0");
	                buf.append(Integer.toHexString(i));
	            }
	            // 32位加密
	            return buf.toString();
	            // 16位的加密
	            // return buf.toString().substring(8, 24);
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	            return null;
	        }

	    }
}
