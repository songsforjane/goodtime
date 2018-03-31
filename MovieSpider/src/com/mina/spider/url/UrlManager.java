package com.mina.spider.url;

public interface UrlManager {
	
	void addUrl(String url);
	
	void deleteUrl(String url);
	
	String nextUrl();
	
	void clearAll();
	
	boolean isEmpty();
	
	boolean check(String url);

}
