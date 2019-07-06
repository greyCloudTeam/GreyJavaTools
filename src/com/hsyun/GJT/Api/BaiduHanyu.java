package com.hsyun.GJT.Api;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hsyun.GJT.Safe;

/**
 * 最后更新：2019-7-6<br>
 * 提供了百度汉语的某些api，因为时间紧，所以只有一个方法
 * @author caiwen
 * @version 0.1
 */
public class BaiduHanyu {
	/**
	 * 在百度汉语中查询一个文字
	 * @param w
	 * 要查询的字
	 * @return
	 * 一个word类型，你可以直接调用这个类型的属性
	 * @throws WordNotFoundException
	 * 文字没有找到
	 * @throws IOException 
	 */
	public static Word getWord(String w) throws WordNotFoundException, IOException{
		String word=w;
		//System.out.println(word);
		word=Safe.urlEncode(word);
		//https://hanyu.baidu.com/s?wd=%E5%92%8C&ptype=zici
		//System.out.println(word);
		String url="https://hanyu.baidu.com/s?wd="+word+"&ptype=zici";
		//System.out.println(url);
		Connection tempConn=Jsoup.connect(url);
		tempConn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:29.0) Gecko/20100101 Firefox/29.0");
		Connection.Response demo=null;
		demo = tempConn.ignoreContentType(true).method(Connection.Method.GET)
					.execute();
		org.jsoup.nodes.Document documentDemo = demo.parse();
		// TODO 自动生成的 catch 块
		//System.out.println(documentDemo.toString());
		
		String piny="";
		String c="";
		Word v=new Word();
		v.text=w;
		try {
			Element p1=documentDemo.getElementById("pinyin");
			Elements p2=p1.getElementsByTag("b");
			v.pinyin=new String[p2.size()];
			for(int i=0;i<p2.size();i++) {
				v.pinyin[i]=p2.get(i).text();
			}
			Element p3=documentDemo.getElementById("zuci-wrapper");
			Elements p4=p3.getElementsByTag("a");
			v.c=new String[p4.size()-1];
			for(int i=0;i<p4.size()-1;i++) {
				v.c[i]=p4.get(i).text();
			}
			
			return v;
		}catch(Exception e) {
			throw new WordNotFoundException();
		}
	}
}

class WordNotFoundException extends RuntimeException {
    public WordNotFoundException() {
        super("没有找到此文字");
    }
}

class Word{
	/**
	 * 这个字的内容
	 */
	public String text="";
	/**
	 * 这个字的拼音，带声调
	 */
	public String[] pinyin=null;
	/**
	 * 这个字的组词
	 */
	public String[] c=null;
}
