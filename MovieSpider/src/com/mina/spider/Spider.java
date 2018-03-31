package com.mina.spider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.mina.spider.collector.DataCollector;
import com.mina.spider.company.Boss;
import com.mina.spider.executor.ExecutorUtils;
import com.mina.spider.http.HttpUtils;

public class Spider {
	
	private final static String BASE_URL = "http://www.kan2008.com/forum-52-";
	private final static String SUBFIX = ".html";
	
	private static Boss boss; 
	private static List<String>mUrls;
	
	public static void prepare(){
		HttpUtils.init();
		mUrls = new ArrayList<>();
		boss = new Boss();
		extractUrls();
	}
	
	
	//http://www.kan2008.com/forum-52-1.html
	private static void extractUrls() {
		for(int i=1; i<=1000; i++){
			mUrls.add(BASE_URL + i + SUBFIX);
		}
	}

	public static void doJobs(){
		
		for(String url : mUrls){
			boss.addNewJob(url);
		}
		
		ExecutorUtils.runOnWorkerThread(boss);
		
	}
	
	public static void jobDone(){
		
		while(true){
			
			try {
				TimeUnit.MILLISECONDS.sleep(60*1000);
				DataCollector.get().notifyUpdateAll();
				
				if(isEmpty()){
					TimeUnit.MILLISECONDS.sleep(30*1000);
					if(isEmpty()){
						boss.stopWorking();
						break;
					}
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	private static boolean isEmpty(){
		return DataCollector.get().isProfilesEmpty() 
				&& DataCollector.get().isDetailsEmpty()
				&& DataCollector.get().isMoviesEmpty();
	}
	
	public static void main(String[] args) {
		
		prepare();
		doJobs();
		jobDone();
	}

}
