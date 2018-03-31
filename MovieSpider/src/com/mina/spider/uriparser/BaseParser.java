package com.mina.spider.uriparser;

import com.mina.spider.beans.DownloadUrls;

/**
 * Created by mina on 2018/2/20.
 */

public class BaseParser implements AbstractParser {

    public BaseParser(){}

    @Override
    public DownloadUrls parse(Chain chain) {
        return new DownloadUrls();
    }
}
