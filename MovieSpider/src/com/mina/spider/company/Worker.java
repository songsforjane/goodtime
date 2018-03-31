package com.mina.spider.company;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.mina.spider.beans.DetailItem;
import com.mina.spider.beans.DetailPage;
import com.mina.spider.beans.MovieItem;
import com.mina.spider.collector.DataCollector;
import com.mina.spider.http.HttpUtils;
import com.mina.spider.parser.DetailParser;
import com.mina.spider.url.DetailUrlManager;

import okhttp3.Response;

public class Worker implements Runnable{
	
	private Object lock = new Object();
	private boolean stopWorking = false;
	
	public Worker(){}
	
	public void addNewTask(String url){
		DetailUrlManager.get().addUrl(url);
	}
	
	public void addFailedTask(String url){
		DetailUrlManager.get().addFailedUrl(url);
	}
	
	public boolean isEmpty(){
		return DetailUrlManager.get().isEmpty();
	}
	
	public String nextUrl(){
		return DetailUrlManager.get().nextUrl();
	}
	
	public void stopWorking(){
		synchronized (lock) {
			stopWorking = true;
		}
	}
	
	public boolean isWorking(){
		synchronized (lock) {
			return stopWorking;
		}
	}

	@Override
	public void run() {
		
		for(;;){
			
			if(!isEmpty()){
				
				String next = nextUrl();
				
				String movieId = next.split("-")[1];
				
				System.out.println("worker name: " + Thread.currentThread().getName() + " ,worker Load detail page, url: " + next);
				
				DetailPage detailPage = null;
				try {
					
					Response response = HttpUtils.request(next);
					
					if(response == null || response.code()!=200){
						System.out.println("Load failed, url: " + next);
						addFailedTask(next);
						continue;
					}
					
					detailPage = DetailParser.get().parse(response.body().string());
					
					int mid = Integer.parseInt(movieId);
					
					for(DetailItem item : detailPage.getDetailItems()){
						item.setMovieId(mid);
					}
					
					MovieItem movieInfo = detailPage.getMovieItem();
					movieInfo.setMovieId(mid);
					
					DataCollector.get().addDetails(detailPage.getDetailItems());
					DataCollector.get().addMovie(movieInfo);
					
//					System.out.println("profile movie size: " + DataCollector.get().getDetailsSize());
					
				} catch (IOException e) {
					System.out.println("parsing failed, url: " + next);
					addFailedTask(next);
					continue;
				}
				
				try {
					TimeUnit.MILLISECONDS.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}else{
				try {
					TimeUnit.MILLISECONDS.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			
			if(stopWorking){
				break;
			}
		}
		
		System.out.println("work out, name : " + Thread.currentThread().getName());
	}
}
