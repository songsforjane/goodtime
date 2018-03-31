package com.mina.spider.uriparser;


import org.jsoup.nodes.Document;

import com.mina.spider.beans.DownloadUrls;

/**
 * Created by mina on 2018/2/18.
 */

public interface AbstractParser {

    DownloadUrls parse(Chain chain);

    interface Chain{

        Document content();

        DownloadUrls proceed(Document document);
    }


}
