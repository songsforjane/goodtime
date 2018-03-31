package com.mina.spider.collector;

import java.util.ArrayList;
import java.util.List;

import com.mina.spider.beans.DetailItem;
import com.mina.spider.beans.MovieItem;
import com.mina.spider.beans.ProfileItem;

public class DataCollector {
	
	private static final int PROFILE_CACHED_COUNT = 100;
	private static final int DETAIL_CACHED_COUNT = 300;
	private static final int MOVIE_CACHED_COUNT = 100;
	
	private List<ProfileItem> mProfiles;
	private List<DetailItem> mDetails;
	private List<MovieItem> mMovies;
	
	private Object lock = new Object();
	
	private DataCollector(){
		mProfiles = new ArrayList<>();
		mDetails = new ArrayList<>();
		mMovies = new ArrayList<>();
	}
	
	private static class InstanceHolder{
		private static DataCollector instance = new DataCollector();
	}
	
	public static synchronized DataCollector get(){
		return InstanceHolder.instance;
	}
	
	//
	public boolean isProfilesEmpty(){
		synchronized (lock) {
			return mProfiles.isEmpty();
		}
	}
	
	public int getProfilesSize(){
		synchronized (lock) {
			return mProfiles.size();
		}
	}
	
	
	public void addProfile(ProfileItem item){
		synchronized (lock) {
			
			if(mProfiles.size() > PROFILE_CACHED_COUNT){
				List<ProfileItem> cache = new ArrayList<>();
				cache.addAll(mProfiles);
				mProfiles.clear();
				notifyUpdateProfiles(cache);
			}
			
			mProfiles.add(item);
		}
	}
	
	public void addProfiles(List<ProfileItem> items){
		synchronized (lock) {
			
			if(mProfiles.size() > PROFILE_CACHED_COUNT){
				List<ProfileItem> cache = new ArrayList<>();
				cache.addAll(mProfiles);
				mProfiles.clear();
				notifyUpdateProfiles(cache);
			}
			
			mProfiles.addAll(items);
		}
	}
	
	//
	public int getDetailsSize(){
		synchronized (lock) {
			return mDetails.size();
		}
	}
	
	public boolean isDetailsEmpty(){
		synchronized (lock) {
			return mDetails.isEmpty();
		}
	}
	
	public void addDetail(DetailItem item){
		synchronized (lock) {
			
			if(mDetails.size() > DETAIL_CACHED_COUNT){
				List<DetailItem> cache = new ArrayList<>();
				cache.addAll(mDetails);
				mDetails.clear();
				notifyUpdateDetails(cache);
			}
			
			mDetails.add(item);
		}
	}
	
	public void addDetails(List<DetailItem> items){
		synchronized (lock) {
			
			if(mDetails.size() > DETAIL_CACHED_COUNT){
				List<DetailItem> cache = new ArrayList<>();
				cache.addAll(mDetails);
				mDetails.clear();
				notifyUpdateDetails(cache);
			}
			
			mDetails.addAll(items);
		}
	}
	
	//
	public int getMoviesSize(){
		synchronized (lock) {
			return mMovies.size();
		}
	}
	
	public boolean isMoviesEmpty(){
		synchronized (lock) {
			return mMovies.isEmpty();
		}
	}
	
	public void addMovie(MovieItem item){
		synchronized (lock) {
			
			if(mMovies.size() > MOVIE_CACHED_COUNT){
				List<MovieItem> cache = new ArrayList<>();
				cache.addAll(mMovies);
				mMovies.clear();
				notifyUpdateMovies(cache);
			}
			
			mMovies.add(item);
		}
	}
	
	public void addMovies(List<MovieItem> items){
		synchronized (lock) {
			
			if(mMovies.size() > MOVIE_CACHED_COUNT){
				List<MovieItem> cache = new ArrayList<>();
				cache.addAll(mMovies);
				mMovies.clear();
				notifyUpdateMovies(cache);
			}
			
			mMovies.addAll(items);
		}
	}
	
	private void notifyUpdateProfiles(List<ProfileItem> items){
		boolean succeed = DbHelper.get().insertProfilesByBatch(items);
		System.out.println("notifyUpdateProfiles succeed : " + succeed);
	}
	
	private void notifyUpdateDetails(List<DetailItem> items){
		boolean succeed = DbHelper.get().insertDetailsByBatch(items);
		System.out.println("notifyUpdateDetails succeed : " + succeed);
	}
	
	private void notifyUpdateMovies(List<MovieItem> items){
		boolean succeed = DbHelper.get().insertMoviesByBatch(items);
		System.out.println("notifyUpdateMovies succeed : " + succeed);
	}
	
	public void notifyUpdateAll(){
		synchronized (lock) {
			boolean succeed1 = DbHelper.get().insertProfilesByBatch(mProfiles);
			boolean succeed2 = DbHelper.get().insertDetailsByBatch(mDetails);
			boolean succeed3 = DbHelper.get().insertMoviesByBatch(mMovies);
			
			mProfiles.clear();
			mDetails.clear();
			mMovies.clear();
			
			System.out.println("notifyUpdateAllDetails succeed : " + succeed2);
			System.out.println("notifyUpdateAllProfiles succeed : " + succeed1);
			System.out.println("notifyUpdateAllMovies succeed : " + succeed3);
		}
		
		
	}
	
	

}
