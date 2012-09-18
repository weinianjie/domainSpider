package com.spider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: wnj
 * Date: 2010-11-26
 * Time: 14:41:19
 * To change this template use File | Settings | File Templates.
 */
public class KeywordService {
    public static List<Map.Entry<String, String>> keywords =new LinkedList<Map.Entry<String, String>>();  
    public static void load() throws IOException {
        keywords.clear();
        BufferedReader br=new BufferedReader(new InputStreamReader(KeywordService.class.getResourceAsStream("/keywords.properties"),"UTF-8"));
        for(String line = br.readLine();line != null;line = br.readLine()){
            String[] temArray = line.split(";");
            Map.Entry<String, String> site = new AbstractMap.SimpleEntry<String, String>(temArray[0],temArray[1]);
            keywords.add(site);
        }
    }
}
