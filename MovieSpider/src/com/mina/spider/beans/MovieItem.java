package com.mina.spider.beans;

public class MovieItem {
	
	private int movieId;
	
	private String coverLink = "";
	private String movieName = "";
	private String date = "";
	private String actors = "";
	private String director = "";
	private String rawdata = "";
	

	public MovieItem() {
	}

	public MovieItem(int movieId) {
		this.movieId = movieId;
	}
	
	public String getCoverLink() {
		return coverLink;
	}

	public void setCoverLink(String coverLink) {
		this.coverLink = coverLink;
	}
	
	public String getRawdata() {
		return rawdata;
	}

	public void setRawdata(String rawdata) {
		this.rawdata = rawdata;
	}
	
	public int getMovieId() {
		return movieId;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getActors() {
		return actors;
	}
	public void setActors(String actors) {
		this.actors = actors;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	
	

}
