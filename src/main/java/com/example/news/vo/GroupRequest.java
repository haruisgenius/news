package com.example.news.vo;

import java.util.List;

import com.example.news.entity.Group;

public class GroupRequest {
	
	// グループ(分類)の名称
	private String groupName;

	// グループ(分類) リスト
	private List<Group> groupList;
	
	//　文章数
	private int newsAmount;
	
	// ------------------------------

	public GroupRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	// ------------------------------

	public GroupRequest(String groupName, int newsAmount) {
		super();
		this.groupName = groupName;
		this.newsAmount = newsAmount;
	}
	
	// ------------------------------

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
