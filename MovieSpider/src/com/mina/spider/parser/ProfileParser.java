package com.mina.spider.parser;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mina.spider.Constants;
import com.mina.spider.beans.ProfileItem;

public class ProfileParser implements Parser<List<ProfileItem>> {
	
	private ProfileParser() {}
	
	private static class InstanceHolder{
		private static ProfileParser instance = new ProfileParser();
	}
	
	public synchronized static ProfileParser get(){
		return InstanceHolder.instance;
	}

	//http://www.kan2008.com/thread-266453-1-1.html
	
	@Override
	public List<ProfileItem> parse(String html) {
		
		List<ProfileItem> urlItems = new ArrayList<>();
		Document document = Jsoup.parse(html);
		Elements elements = document.select("tbody[id^=normalthread_]");
		for(Element element : elements){
            String title = element.child(0).child(1).text();
            String link = element.child(0).child(1).child(0).attr("href");
            
            String thumbNail = element.child(0).child(0).child(0).child(0).attr("src");
            
            String id = link.split("-")[1];
            int movieId = Integer.parseInt(id);
            
            int startIndex = title.indexOf("¡¶");
            int endIndex = title.lastIndexOf("¡·");
            String movieName = "null";
            if(startIndex>=0 && endIndex>=0){
            	movieName = title.substring(startIndex+1, endIndex);
            }
            
            
            ProfileItem urlItem = new ProfileItem(movieId);
            urlItem.setDetailLink(Constants.Urls.BASE_MOVIES_URL + link);
            urlItem.setIntroduction(title);
            urlItem.setMovieName(movieName);
            urlItems.add(urlItem);
            
        }
		
		return urlItems;
	}

}
