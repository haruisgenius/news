package com.example.news.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "news_group")
public class Group {
	
	// グループ(分類)の名称
	@Id
	@Column(name = "group_name")
	private String groupName;
	
	//　文章数
	@Column(name = "news_amount")
	private int newsAmount;
	
//	----------------------------------

	public Group() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public Group(String groupName) {
		super();
		this.groupName = groupName;
	}

	public Group(String groupName, int newsAmount) {
		super();
		this.groupName = groupName;
		this.newsAmount = newsAmount;
	}
	
//	----------------------------------

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getNewsAmount() {
		return newsAmount;
	}

	public void setNewsAmount(int newsAmount) {
		this.newsAmount = newsAmount;
	}
	
	

}
