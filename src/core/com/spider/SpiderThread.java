package com.spider;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class SpiderThread extends Thread {
	private final static Logger log = Logger.getLogger(SpiderThread.class);
	private final static String[] orgType = { ".com", ".cn", ".net" };
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
		String dm = new String();
		while ((dm = DomainCharacter.nextDomain()) != null) {
			try {
				for (String org : orgType) {
					StringBuffer sb = new StringBuffer();
					sb.append("http://panda.www.net.cn/cgi-bin/check.cgi");
					sb.append("?area_domain=").append(dm).append(".com");
					URL checkUrl = new URL(sb.toString());
					urlConnection = (HttpURLConnection) checkUrl
							.openConnection();
					urlConnection.setRequestMethod("GET");
					urlConnection.setDoOutput(true);
					urlConnection.setDoInput(true);
					urlConnection.setUseCaches(false);
					
					Document doc = this.getDocBuilder().parse( new InputSource((urlConnection.getInputStream())));
					urlConnection.disconnect();
					
					sb = new StringBuffer();
					sb.append("【" + dm + org + "----"
							+ Thread.currentThread() + "】【");
					sb.append(doc.getElementsByTagName("returncode").item(0)
							.getFirstChild().getNodeValue());
					sb.append("------");
					sb.append(doc.getElementsByTagName("original").item(0)
							.getFirstChild().getNodeValue());
					sb.append("】");

					log.info(sb.toString());
					if (doc.getElementsByTagName("original").item(0)
							.getFirstChild().getNodeValue().startsWith("210")) {
						log.fatal(dm + org);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
