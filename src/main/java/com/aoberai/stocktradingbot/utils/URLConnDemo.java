package com.aoberai.stocktradingbot.utils;

// File Name : URLConnDemo.java
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLConnDemo {

    public static void main(String [] args) throws IOException {
        // Here we create a document object and use JSoup to fetch the website
        Document doc = Jsoup.connect("https://www.google.com/search?q=aapl+news&safe=strict&source=lnms&tbm=nws&sa=X&ved=2ahUKEwjNgIPm87zpAhU5KDQIHV5CC_MQ_AUoAXoECA0QAw&biw=1103&bih=719").get();

        // With the document fetched, we use JSoup's title() method to fetch the title
//        System.out.print("Title: " + doc.body().toString());

        // In case of any IO errors, we want the messages written to the console
//        URL url = new URL("https://www.google.com/search?q=aapl+news&safe=strict&source=lnms&tbm=nws&sa=X&ved=2ahUKEwjM3ZzY7rzpAhUcJzQIHTlYBKcQ_AUoAXoECBoQAw");
//        URLConnection urlc = url.openConnection();
//        urlc.setRequestProperty("User-Agent", "Mozilla 5.0 (Windows; U; "
//                + "Windows NT 5.1; en-US; rv:1.8.0.11) ");
//        InputStream inputFile = urlc.getInputStream();
//        InputStreamReader isReader = new InputStreamReader(inputFile);
//        BufferedReader reader = new BufferedReader(isReader);
//        StringBuffer sb = new StringBuffer();
//        String str;
//        while((str = reader.readLine())!= null){
//            sb.append(str);
//        }
//        System.out.println(sb.toString());

        Pattern urlPattern = Pattern.compile(
                "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
                        + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
                        + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        Matcher matcher = urlPattern.matcher(doc.body().toString());
        for  (int i = 0; matcher.find(); i++) {
            int matchStart = matcher.start(1);
            int matchEnd = matcher.end();
            if (i % 2 == 1) System.out.println(doc.body().toString().substring(matchStart, matchEnd));
        }
    }
}