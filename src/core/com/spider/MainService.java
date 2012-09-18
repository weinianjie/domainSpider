package com.spider;

public class MainService {
	
	public static void main(String[] args){
		int spiderCount = 5;
//		long sleepMillis = 6000;
		
		//获取规则
		//启动线程
        for(int i = 0;i < spiderCount;i++){
        	new SpiderThread().start();
        }
	}
}
