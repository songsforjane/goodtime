package com.mina.spider.uriparser;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.mina.spider.Constants;
import com.mina.spider.beans.DownloadUrls;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mina on 2018/2/20.
 */

public class Ed2kParser implements AbstractParser {

    private final static String TAG = Ed2kParser.class.getSimpleName();

    @Override
    public DownloadUrls parse(Chain chain) {
        Document document = chain.content();

        if (document == null) {
            throw new NullPointerException("ThunderParser document is null");
        }

        DownloadUrls urls = chain.proceed(document);
        Map<String, String> ed2kUrls = new HashMap<>();
        urls.setEd2kUrls(ed2kUrls);
        Elements elements = document.select("td[id^=postmessage_]");
        Elements links = elements.select("div[id^=code_]");
        if (links != null && links.size() > 0) {
        	String title = "ed2k link";
        	for (int i=0; i< links.size(); i++) {
                String url = links.get(i).text();
                if(url!=null && url.startsWith(Constants.Schemes.SCHEME_ED2K)){
                    ed2kUrls.put(title+" "+i, url);
//                    System.out.println("ed2k title: " + title + " ,url : " + url);
                }
            }
        }
        return urls;
    }


}
