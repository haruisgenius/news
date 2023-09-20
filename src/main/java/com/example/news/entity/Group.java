package com.example.news.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "news_group")
public class Group {

	// グループ(分類)の番号
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "group_number")
	private Integer groupNumber;

	// グループ(分類)の名称
	@Column(name = "group_name")
	private String groupName;

	// 文章数
	@Column(name = "news_amount")
	private int newsAmount = 0;

	// 削除かどうか true>削除した
	@Column(name = "is_group_delete")
	private boolean isGroupDelete = false;

//	----------------------------------

	public Group() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Group(Integer groupNumber, String groupName, int newsAmount, boolean isGroupDelete) {
		super();
		this.groupNumber = groupNumber;
		this.groupName = groupName;
		this.newsAmount = newsAmount;
		this.isGroupDelete = isGroupDelete;
	}

	public Group(String groupName, int newsAmount) {
		super();
		this.groupName = groupName;
		this.newsAmount = newsAmount;
	}

	public Group(String groupName) {
		super();
		this.groupName = groupName;
	}

//	----------------------------------

	public Integer getGroupNumber() {
		return groupNumber;
	}

//	public void setGroupNumber(Integer groupNumber) {
//		this.groupNumber = groupNumber;
//	}

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

	public boolean isGroupDelete() {
		return isGroupDelete;
	}

	public void setGroupDelete(boolean isGroupDelete) {
		this.isGroupDelete = isGroupDelete;
	}

}
