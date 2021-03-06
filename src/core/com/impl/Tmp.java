package com.impl;

import com.spider.DBService;
import com.spider.ConfigService;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: wnj
 * Date: 2010-11-30
 * Time: 15:35:57
 * To change this template use File | Settings | File Templates.
 */
public class Tmp {
    public static void doSomething(){
        String cityName = "";
        String siteName = "";
        for(int i = 0;i < ConfigService.keywords.size();i++ ){
            try{
                Map.Entry<String, String> entry = ConfigService.keywords.get(i);
                cityName = entry.getKey();
                siteName = entry.getValue();
                String[] keyArray = siteName.split(",");
                int cityid = 0;
                cityid = DBService.getCityId(cityName);
                if(cityid <= 0){
                    System.out.println("城市找不到:" + cityName);
                    break;
                }
                for(String s:keyArray){
                    System.out.println("当前：" + cityName + "---------" + s + "-----" + (i+1));
                    int n = DBService.save3(cityid, s);
                    if(n <= 0){
                        System.out.println(cityName + "---" + s + "失败");
                    }
                }
            }catch(Exception e){
                System.out.println("错误行:" + (i+1));
                e.printStackTrace();
            }
        }
        System.out.println("搞定!");
    }
}
