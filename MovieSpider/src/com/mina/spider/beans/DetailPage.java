package com.mina.spider.beans;

import java.util.List;

public class DetailPage {
	
	private List<DetailItem> detailItems;
	
	private MovieItem movieItem;
	
	

	public DetailPage() {
		super();
	}

	public List<DetailItem> getDetailItems() {
		return detailItems;
	}

	public void setDetailItems(List<DetailItem> detailItems) {
		this.detailItems = detailItems;
	}

	public MovieItem getMovieItem() {
		return movieItem;
	}

	public void setMovieItem(MovieItem movieItem) {
		this.movieItem = movieItem;
	}
	
	
	

}
