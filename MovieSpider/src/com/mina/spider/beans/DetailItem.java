package com.mina.spider.beans;

public class DetailItem {
	
	private int movieId;
	private String downloadLink;
	private int linkType;
	
	
	public DetailItem() {
	}


	public DetailItem(int movieId) {
		this.movieId = movieId;
	}


	public DetailItem(int movieId, String downloadLink, int linkType) {
		this.movieId = movieId;
		this.downloadLink = downloadLink;
		this.linkType = linkType;
	}
	
	
	public int getMovieId() {
		return movieId;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	public String getDownloadLink() {
		return downloadLink;
	}
	public void setDownloadLink(String downloadLink) {
		this.downloadLink = downloadLink;
	}
	public int getLinkType() {
		return linkType;
	}
	public void setLinkType(int linkType) {
		this.linkType = linkType;
	}
	
	
	
}
