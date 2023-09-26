package com.example.news.vo;

import java.util.List;

import com.example.news.entity.News;

public class NewsResponse {
	
	private News news;
	
	private List<News> newsList;
	
	private String message;
	
	// -----------------------------

	public NewsResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	// -----------------------------

	public NewsResponse(News news, List<News> newsList, String message) {
		super();
		this.news = news;
		this.newsList = newsList;
		this.message = message;
	}

	public NewsResponse(News news, String message) {
		super();
		this.news = news;
		this.message = message;
	}

	public NewsResponse(List<News> newsList, String message) {
		super();
		this.newsList = newsList;
		this.message = message;
	}

	public NewsResponse(String message) {
		super();
		this.message = message;
	}

	// -----------------------------
	
	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

	public List<News> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<News> newsList) {
		this.newsList = newsList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
