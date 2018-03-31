package com.mina.spider.url;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import com.mina.spider.http.HttpUtils;

public class DetailUrlManager implements UrlManager{
	
	private final static String HOST = "http://www.kan2008.com";
	
	private Object lock = new Object();
	private LinkedList<String> mUrls;
	private LinkedList<String> mFailedUrls;
	private Set<String> mVisitedUrl;
	
	private DetailUrlManager() {
		mUrls = new LinkedList<>();
		mFailedUrls = new LinkedList<>();
		mVisitedUrl = new HashSet<>();
	}
	
	private static class InstanceHolder{
		private static DetailUrlManager instance = new DetailUrlManager();
	}
	
	public static DetailUrlManager get(){
		return InstanceHolder.instance;
	}

	@Override
	public void addUrl(String url) {
		synchronized (lock) {
			if(check(url)){
				mUrls.addLast(url);
			}
		}
	}
	
	public void addFailedUrl(String url){
		synchronized (lock) {
			if(check(url)){
				mFailedUrls.addLast(url);
			}
		}
	}

	@Override
	public void deleteUrl(String url) {
		synchronized (lock) {
			if(url!=null && mUrls.contains(url)){
				mUrls.remove(url);
			}
		}
	}
	
	@Override
	public boolean isEmpty() {
		synchronized (lock) {
			return mUrls.isEmpty()
					&& mFailedUrls.isEmpty();
		}
	}

	@Override
	public String nextUrl() {
		synchronized (lock) {
			String url = null;
			if(!isEmpty()){
				url = mUrls.pollFirst();
			}
			
			if(HttpUtils.isEmpty(url)){
				url = mFailedUrls.pollFirst();
			}
			return url;
		}
	}

	@Override
	public void clearAll() {
		synchronized (lock) {
			mUrls.clear();
		}
	}

	@Override
	public boolean check(String url) {
		
		return true;
		
//		if(url==null || "".equals(url)){
//			return false;
//		}
//		
//		if(!url.startsWith("http://") 
//				|| !url.startsWith("https://") 
//				|| !url.contains(HOST)){
//			return false;
//		}
//		
//		return !mVisitedUrl.contains(url);
		
	}

}
