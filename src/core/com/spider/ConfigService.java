package com.spider;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: wnj
 * Date: 2010-11-26
 * Time: 14:41:19
 * To change this template use File | Settings | File Templates.
 */
public class ConfigService {
	private static final Logger log = Logger.getLogger(ConfigService.class);
    private static Properties p = new Properties();
    
    public synchronized static void init() throws IOException {
        p.clear();
        InputStream in = ConfigService.class.getResourceAsStream("/config.properties");
        p.load(in);
        in.close();
        log.info("读取配置成功");
    }
    
    public static String getProperty(String key){
    	return p.getProperty(key);
    }
    
    public static String getProperty(String key, String def){
    	return p.getProperty(key, def);
    }
    
    public static List<String> getPropertyAsArray(String key){
    	List<String> list = new LinkedList<String>();
    	if(p.getProperty(key) != null){
    		list = Arrays.asList(p.getProperty(key).split(","));
    		for(int i=0;i<list.size();i++){
    			list.set(i, list.get(i).trim());
    		}
    	}
    	return list;
    }
}
