package com.spider;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.spider.entity.DomainEntity;
import com.spider.entity.StatusEntity;

public class SpiderThread extends Thread {
	private final static Logger log = Logger.getLogger(SpiderThread.class);
	private HttpURLConnection urlConnection = null;
	private DocumentBuilder docBuilder = null;

	private DocumentBuilder getDocBuilder() throws ParserConfigurationException {
		if (docBuilder != null) {
			return docBuilder;
		}
		docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		return docBuilder;
	}

	public void run() {
		logRun();
		String dm = new String();
		while ((dm = DomainCharsService.nextDomain()) != null) {
			try {
				StringBuffer sb = new StringBuffer();
				sb.append("http://panda.www.net.cn/cgi-bin/check.cgi");
				sb.append("?area_domain=").append(dm);
				URL checkUrl = new URL(sb.toString());
				urlConnection = (HttpURLConnection) checkUrl.openConnection();
				urlConnection.setRequestMethod("GET");
				urlConnection.setDoOutput(true);
				urlConnection.setDoInput(true);
				urlConnection.setUseCaches(false);

				InputStream in = urlConnection.getInputStream();
				if (in != null) {
					Document doc = this.getDocBuilder().parse(
							new InputSource(in));
					in.close();

					sb = new StringBuffer();
					sb.append("【");
					sb.append(Thread.currentThread());
					sb.append("-----");
					sb.append(dm);
					sb.append("-----");
					sb.append(doc.getElementsByTagName("original").item(0)
							.getFirstChild().getNodeValue());
					sb.append("】");

					log.info(sb.toString());
					if (doc.getElementsByTagName("original").item(0)
							.getFirstChild().getNodeValue().startsWith("210")) {
						log.fatal(dm);
						// TODO 调用评分系统，写入数据库(评分系统：组织名，单词)
						
						String[] part = dm.split("\\.");
						DomainEntity en = new DomainEntity();
						en.setName(part[0]);
						en.setOrg(part[1]);
						DBService.saveDomain(en);
					}
				} else {
					// TODO 记录失败日志
				}
				urlConnection.disconnect();

			} catch (IOException e) {
				try {
					logSleep();
					Thread.sleep(Integer.parseInt(ConfigService.getProperty("threadSleep", "600000")));
					logRun();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private void logRun(){
		log.fatal(Thread.currentThread() + "--run");
		StatusEntity status = new StatusEntity();
		status.setMkey(Thread.currentThread().toString());
		status.setMval("running");
		status.setDescr("线程状态监控");
		DBService.saveStatus(status);
	}
	
	private void logSleep(){
		log.fatal(Thread.currentThread() + "--sleep");
		StatusEntity status = new StatusEntity();
		status.setMkey(Thread.currentThread().toString());
		status.setMval("sleeping");
		status.setDescr("线程状态监控");
		DBService.saveStatus(status);
	}
}
