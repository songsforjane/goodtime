package com.mina.spider.beans;

public class ProfileItem {
	
	private int movieId;

	private String introduction;
	private String movieName;
	private String detailLink;
	
	public ProfileItem() {}
	
	public ProfileItem(int movieId) {
		this.movieId = movieId;
	}

	public ProfileItem(String detailLink) {
		super();
		this.detailLink = detailLink;
	}

	public ProfileItem(String introduction, String movieName, String detailLink) {
		this.introduction = introduction;
		this.movieName = movieName;
		this.detailLink = detailLink;
	}
	
	public String getIntroduction() {
		return introduction;
	}
	
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	
	public String getMovieName() {
		return movieName;
	}
	
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	
	public String getDetailLink() {
		return detailLink;
	}
	
	public void setDetailLink(String detailLink) {
		this.detailLink = detailLink;
	}
	
	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

}
