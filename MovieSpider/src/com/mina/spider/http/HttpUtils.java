package com.mina.spider.http;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtils {
	
	private static OkHttpClient mClient;
	
	private HttpUtils(){
		
		if(mClient==null){
			mClient = new OkHttpClient.Builder()
					.connectTimeout(10000, TimeUnit.MILLISECONDS)
					.readTimeout(10000, TimeUnit.MILLISECONDS)
					.retryOnConnectionFailure(false)
					.build();
		}
		
	}
	
	public static void init(){
		new HttpUtils();
	}
	
	public static Response request(String url){
		
		if(isEmpty(url)){
			throw new IllegalArgumentException("url is empty, url: " + url);
		}
		
		Request request = new Request.Builder()
				.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:59.0) Gecko/20")
				.header("Referer", "https://www.jianshu.com/u/a6ca99a1c463")
				.get()
				.url(url)
				.cacheControl(CacheControl.FORCE_NETWORK)
				.build();
		
		Call call = mClient.newCall(request);
		
		Response response = null;
		try {
			response  = call.execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	public static boolean isEmpty(String object){
		return object==null || "".equals(object);
	}
	
}
