package com.mina.spider.uriparser;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.mina.spider.beans.DownloadUrls;
import com.mina.spider.beans.MovieItem;

public class MovieInfoParser implements AbstractParser{

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
		Elements links = elements.select("img[id^=aimg_]");
		
		MovieItem movieItem = new MovieItem();
		String[] segments = elements.text().split(" ");
		if(links.size()>0){
			movieItem.setCoverLink(links.get(0).attr("src"));
		}
		
		StringBuilder sb = new StringBuilder();
		
		for(String segment : segments){
			sb.append(segment.trim()).append("\n");
		}
		movieItem.setRawdata(sb.toString());
		
		urls.setMovieInfo(movieItem);

		return urls;
	}

}
