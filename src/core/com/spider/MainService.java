package com.spider;

import java.io.IOException;

public class MainService {

	public static void main(String[] args) throws IOException {
		/**
		 * 载入配置
		 */
		ConfigService.init();
		
		/**
		 * 初始化域名字符服务
		 */
		DomainCharsService.init();
		
		/**
		 * 初始化数据库服务
		 */
		DBService.init();		
		
		int spiderCount = Integer.parseInt(ConfigService.getProperty("threadCount", "1"));
		for (int i = 0; i < spiderCount; i++) {
			new SpiderThread().start();
		}
	}
}
