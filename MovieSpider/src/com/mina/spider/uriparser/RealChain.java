package com.mina.spider.uriparser;

import org.jsoup.nodes.Document;

import com.mina.spider.beans.DownloadUrls;

import java.util.List;

/**
 * Created by mina on 2018/2/20.
 */

public class RealChain implements AbstractParser.Chain {

    private Document mDocument;
    private List<AbstractParser> mAbstractParsers;
    private int index;

    public RealChain(List<AbstractParser> abstractParsers, Document document, int index){
        this.mAbstractParsers = abstractParsers;
        this.mDocument = document;
        this.index = index;
    }


    @Override
    public Document content() {
        return mDocument;
    }

    @Override
    public DownloadUrls proceed(Document document) {
        if (index >= mAbstractParsers.size()) throw new AssertionError();
        AbstractParser parser = mAbstractParsers.get(index);
        RealChain nextChain = new RealChain(mAbstractParsers, document, ++index);
        DownloadUrls downloadUrls = parser.parse(nextChain);
        if(downloadUrls == null){
            throw new NullPointerException("parser " + parser + " returned null");
        }
        return downloadUrls;
    }
}
