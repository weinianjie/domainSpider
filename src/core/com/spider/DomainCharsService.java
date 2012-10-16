package com.spider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DomainCharsService {
	private static String[] charSource = {};// 域名可能会包含的字符集合
	private static String[] orgType = {};// 域名可能的机构名
	private static int sLength = 0; // 字符集长度
	private static int minLength = 0; // 设置域名最短的可能
	private static int maxLength = 0; // 设置可能最长的域名长度
	private static long counter = 0l;// 计数器，多线程时可以对其加锁，当然得先转换成Long类型。
	
	/**
	 * 域名字符组合上限10位（不包括机构名），36进制10位数最大值约365*(10的13次方)，一共16位
	 * Java中int的上限约是21*(10的8次方)，一共10位
	 * Java中long的上限约是92*(10的17次方)，一共19位
	 * 四核(2.53GHz)+4G内存的笔记本每万次扫描耗时1个小时左右
	 */
	
	private static String curName = null;
	private static String curOrg = null;
	private static int orgPointer = 0;
	
	private final static int sizeDelimiter = 10;//扫描上限是10位字符组合
	/**
	 * 根据配置初始化字符规则
	 * @throws IOException
	 */
	public synchronized static void init() throws IOException {
		//http://hi.baidu.com/xinshenbuning2/item/183332341f035c322e20c48c
		List<String> list = new ArrayList<String>(ConfigService.getPropertyAsArray("domainChars"));
		if(list != null){
			for(int i=0;i<list.size();i++){
				if(list.get(i).length() != 1){
					list.remove(i);
					i--;
				}
			}
			if(list.size() == 0){
				list.add("a");
			}
			charSource = list.toArray(charSource);
		}

		
		list = new ArrayList<String>(ConfigService.getPropertyAsArray("domainOrgs"));
		if(list != null){
			if(list.size() == 0){
				list.add("com");
			}
			orgType = list.toArray(orgType);
		}
		
		sLength = charSource.length;
		minLength = Integer.parseInt(ConfigService.getProperty("domainMinsize", "0"));
		maxLength = Integer.parseInt(ConfigService.getProperty("domainMaxsize", "0"));
		
		minLength = minLength > sizeDelimiter? 0 : minLength;
		maxLength = maxLength > sizeDelimiter? sizeDelimiter :maxLength;
		
		String counterBegin  = ConfigService.getProperty("domainScanBegin");
		if(counterBegin != null){
			counter = Integer.parseInt(counterBegin);
		}else{
			for (int i = 1; i < minLength; i++) {
				counter += Math.pow(sLength, i);
			}
		}
		
		orgPointer = orgType.length;
	}

	/**
	 * 获取下一个字符串
	 * @return
	 */
	public static synchronized String nextDomain() {
		nextOrg();
		if(orgPointer == 0){
			nextName();
		}
		return curName.length()<=maxLength? curName + "." + curOrg : null;
	}
	
	/**
	 * 获取当前计数器
	 * @return
	 */
	public static long getCurrentCounter(){
		return counter;
	}
	
	private static void nextName(){
		StringBuilder buider = new StringBuilder(maxLength * 2);
		long _counter = counter;
		while (_counter >= sLength) {// 10进制转换成36进制
			buider.insert(0, charSource[(int)_counter % sLength]);// 获得低位
			_counter = _counter / sLength;
			_counter--;// 精髓所在，处理进制体系中只有10没有01的问题，在穷举里面是可以存在01的
		}
		buider.insert(0, charSource[(int)_counter]);// 最高位
		
		curName = buider.toString();
		counter++;
	}
	
	private static void nextOrg(){
		orgPointer++;
		if(orgPointer >= orgType.length){
			orgPointer = 0;
		}
		curOrg = orgType[orgPointer];
	}
}
