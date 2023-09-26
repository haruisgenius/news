package com.example.news.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.news.entity.News;

public class NewsRequest {
	
	// ニュースの番号
	private Integer newsNumber;
	
	// タイトル
	private String title;
	
	// サブタイトル
	private String subTitle;

	// リリース日
	private LocalDate releaseDate;

	// グループ(分類)
	private String newsGroup;

	// カテゴリー(小分類)
	private String category;

	// 内容
	private String content;

	// 閲覧可能
	private boolean isOpen;

	// 編集時間(デフォルト：編集した時点)
	private LocalDateTime updateTime;
	
	// 
	private List<News> newsList;
	
	// -------------------------

	public NewsRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	// -------------------------

	public NewsRequest(Integer newsNumber, String title, String subTitle, LocalDate releaseDate, String newsGroup,
			String category, String content, boolean isOpen, LocalDateTime updateTime) {
		super();
		this.newsNumber = newsNumber;
		this.title = title;
		this.subTitle = subTitle;
		this.releaseDate = releaseDate;
		this.newsGroup = newsGroup;
		this.category = category;
		this.content = content;
		this.isOpen = isOpen;
		this.updateTime = updateTime;
	}
	
	// -------------------------

	public Integer getNewsNumber() {
		return newsNumber;
	}


	public void setNewsNumber(Integer newsNumber) {
		this.newsNumber = newsNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getNewsGroup() {
		return newsGroup;
	}

	public void setNewsGroup(String newsGroup) {
		this.newsGroup = newsGroup;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	public List<News> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<News> newsList) {
		this.newsList = newsList;
	}
	
	

}
