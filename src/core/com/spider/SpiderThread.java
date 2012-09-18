package com.spider;

public class SpiderThread extends Thread{
//	public static List<> xxx = new LinkList<>();
	private static Integer curAscii = 'a';
	private static Integer curLength = 1;
	
	public void run() {
		while(true){
			synchronized (curAscii) {
				
			}
			//TODO 得到需要拜访的域名
			//TODO 发送请求
		}
	}	

}
