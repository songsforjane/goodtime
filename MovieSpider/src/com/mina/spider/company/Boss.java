package com.mina.spider.company;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.mina.spider.executor.ExecutorUtils;
import com.mina.spider.http.HttpUtils;
import com.mina.spider.url.ProfileUrlManager;

import okhttp3.Response;

public class Boss implements Runnable{
	
	private final static int WORKER_NUM = 15;
	
	private Worker[] mWorkers;
	
	private Object lock = new Object();
	
	private AtomicInteger mWorkerIndex = new AtomicInteger(0);
	
	private volatile boolean isStop = false;


	public Boss(){
		mWorkers = new Worker[WORKER_NUM];
		for(int i=0; i<WORKER_NUM; i++){
			mWorkers[i] = new Worker();
			ExecutorUtils.runOnWorkerThread(mWorkers[i]);
		}
	}
	
	public void addNewJob(String url){
		ProfileUrlManager.get().addUrl(url);
	}
	
	public boolean isEmpty(){
		return ProfileUrlManager.get().isEmpty();
	}
	
	public String nextUrl(){
		return ProfileUrlManager.get().nextUrl();
	}
	
	public void stopWorking(){
		synchronized(lock){
			isStop = true;
		}
	}
	
	public boolean isWorking(){
		synchronized(lock){
			return !isStop;
		}
	}
	
	public boolean isJobDone(){
		return true;
	}
	
	public Worker nextWorker(){
		synchronized (lock) {
			return mWorkers[Math.abs(mWorkerIndex.incrementAndGet() % WORKER_NUM)];
		}
	}
	

	@Override
	public void run() {
		isStop = false;
		for(;;){
			
			if(!isEmpty()){
				String next = nextUrl();
				try {

					Response response = HttpUtils.request(next);
					
					System.out.println("response : " + response.code());
					
					if(response == null || response.code()!=200){
						System.out.println("Load failed, url: " + next);
						addNewJob(next);
						continue;
					}
					
					List<ProfileItem> urlItems = ProfileParser.get().parse(response.body().string());
					
					if(urlItems==null){
						System.out.println("Load failed, url: " + next);
						addNewJob(next);
						continue;
					}
					
					DataCollector.get().addProfiles(urlItems);
					
					for(ProfileItem item : urlItems){
						String detailUrl = item.getDetailLink();
						if(!HttpUtils.isEmpty(detailUrl)){
							nextWorker().addNewTask(detailUrl);
						}
					}
					
					System.out.println("profile movie size: " + DataCollector.get().getProfilesSize());
					TimeUnit.MILLISECONDS.sleep(1000);
					
				}catch(Exception e){
					System.out.println("parsing failed, url: " + next);
					addNewJob(next);
				}
				
			}else{
				try {
					TimeUnit.MILLISECONDS.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
			
			if(isStop){
				break;
			}
		}
		
		workout();
		
	}

	public void workout() {
		for(Worker worker : mWorkers){
			worker.stopWorking();
		}
	}
	

}
