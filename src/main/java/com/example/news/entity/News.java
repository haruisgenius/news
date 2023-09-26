package com.example.news.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "news")
public class News {

	// ニュースの番号
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "news_number")
	private Integer newsNumber;

	// タイトル
	@Column(name = "title")
	private String title;

	// サブタイトル
	@Column(name = "sub_title")
	private String subTitle;

	// リリース日
	@Column(name = "release_date")
	private LocalDate releaseDate;

	// グループ(分類)
	@Column(name = "news_group")
	private String newsGroup;

	// カテゴリー(小分類)
	@Column(name = "category")
	private String category;

	// 内容
	@Column(name = "content")
	private String content;

	// 閲覧可能
	@Column(name = "is_open")
	private boolean isOpen;

	// 編集時間(デフォルト：編集した時点)
	@Column(name = "update_time")
	private LocalDateTime updateTime = LocalDateTime.now();
	
	// 削除かどうか true>削除した
	@Column(name = "is_news_delete")
	private boolean isNewsDelete = false;

//	----------------------------------

	public News() {
		super();
		// TODO Auto-generated constructor stub
	}

	public News(Integer newsNumber, String title, String subTitle, LocalDate releaseDate, String newsGroup, String category,
		String content, boolean isOpen, LocalDateTime updateTime, boolean isNewsDelete) {
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
	this.isNewsDelete = isNewsDelete;
}

	public News(String title, String subTitle, LocalDate releaseDate, String newsGroup, String category, String content,
			boolean isOpen, LocalDateTime updateTime) {
		super();
		this.title = title;
		this.subTitle = subTitle;
		this.releaseDate = releaseDate;
		this.newsGroup = newsGroup;
		this.category = category;
		this.content = content;
		this.isOpen = isOpen;
		this.updateTime = updateTime;
	}

//	----------------------------------

	public Integer getNewsNumber() {
		return newsNumber;
	}

//	public void setNewsNumber(Integer newsNumber) {
//		this.newsNumber = newsNumber;
//	}

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

	public boolean isNewsDelete() {
		return isNewsDelete;
	}

	public void setNewsDelete(boolean isNewsDelete) {
		this.isNewsDelete = isNewsDelete;
	}

}
