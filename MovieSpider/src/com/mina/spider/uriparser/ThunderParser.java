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

public class ThunderParser implements AbstractParser {


    private final static String TAG = ThunderParser.class.getSimpleName();

    @Override
    public DownloadUrls parse(Chain chain) {

        Document document = chain.content();

        if (document == null) {
            throw new NullPointerException("ThunderParser document is null");
        }

        DownloadUrls urls = chain.proceed(document);
        Map<String, String> thunderUrls = new HashMap<>();
        urls.setThunderUrls(thunderUrls);
        Elements elements = document.select("td[id^=postmessage_]");
        Elements links = elements.select("a[href^=" + Constants.Schemes.SCHEME_THUNDER + "]");
        if (links != null && links.size() > 0) {
            String title = "thunder link";
            for (int i=0; i<links.size(); i++) {
                String url = links.get(i).text();
                thunderUrls.put(title + " " + i, url);
//                System.out.println("thunder title: " + title + " ,url : " + url);
            }
        }

        return urls;
    }

}
