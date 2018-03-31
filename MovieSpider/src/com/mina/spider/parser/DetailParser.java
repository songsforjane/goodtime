package com.mina.spider.parser;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.mina.spider.beans.DetailItem;
import com.mina.spider.beans.DetailPage;
import com.mina.spider.beans.DownloadUrls;
import com.mina.spider.beans.MovieItem;
import com.mina.spider.http.HttpUtils;
import com.mina.spider.uriparser.AbstractParser;
import com.mina.spider.uriparser.UriParserManager;

public class DetailParser implements Parser<DetailPage> {
	
	
	private DetailParser() {
		
	}
	
	private static class InstanceHolder{
		private static DetailParser instance = new DetailParser();
	}
	
	public synchronized static DetailParser get(){
		return InstanceHolder.instance;
	}

	@Override
	public DetailPage parse(String html) {
		
		if(HttpUtils.isEmpty(html)){
			throw new NullPointerException("the html page is null, html: " + html);
		}
		
		List<DetailItem> urlItems = new ArrayList<>();
		
		Document document = Jsoup.parse(html);
		DetailPage detailPage = new DetailPage();
		AbstractParser.Chain chain = UriParserManager.get().getParserChain(document);
		DownloadUrls urls = chain.proceed(document);
		//for thunder
		for(String url : urls.getThunderUrls().values()){
			DetailItem item = new DetailItem();
			item.setLinkType(1);
			item.setDownloadLink(url);
			urlItems.add(item);
		}
		
		//for magnet
		for(String url : urls.getMagnetUrls().values()){
			DetailItem item = new DetailItem();
			item.setLinkType(2);
			item.setDownloadLink(url);
			urlItems.add(item);
		}
		
		//for ed2k
		for(String url : urls.getEd2kUrls().values()){
			DetailItem item = new DetailItem();
			item.setLinkType(3);
			item.setDownloadLink(url);
			urlItems.add(item);
		}
		
		//for movie item
		MovieItem movieInfo = urls.getMovieInfo();
		
		detailPage.setMovieItem(movieInfo);
		detailPage.setDetailItems(urlItems);
		
		
		return detailPage;
	}

}
