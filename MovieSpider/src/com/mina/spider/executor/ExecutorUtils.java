package com.mina.spider.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorUtils {
	
	private final static int THREAD_NUM = 30;
	private static ExecutorService mFixedThreadPool = Executors.newFixedThreadPool(THREAD_NUM);
	
	private ExecutorUtils(){}
	
	public static synchronized void runOnWorkerThread(Runnable runnable){
		
		if(runnable==null){
			throw new NullPointerException("runnable is null");
		}
		
		mFixedThreadPool.execute(runnable);
	}
	
	public static synchronized Future runOnWorkerThreadWithFuture(Runnable runnable){
		
		if(runnable==null){
			throw new NullPointerException("runnable is null");
		}
		
		return mFixedThreadPool.submit(runnable);
	}
	
	

}
