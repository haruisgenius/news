package com.example.news.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class Category {

	// カテゴリーの番号
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_number")
	private Integer categoryNumber;

	// カテゴリーの名称
	@Column(name = "category_name")
	private String categoryName;

	// グループ(分類)
	@Column(name = "category_group")
	private String categoryGroup;

	// 文章の量
	@Column(name = "amount")
	private int amount = 0;

//	----------------------------------

	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Category(Integer categoryNumber, String categoryName, String categoryGroup, int amount) {
		super();
		this.categoryNumber = categoryNumber;
		this.categoryName = categoryName;
		this.categoryGroup = categoryGroup;
		this.amount = amount;
	}
	
	public Category(String categoryName, String categoryGroup, int amount) {
		super();
		this.categoryName = categoryName;
		this.categoryGroup = categoryGroup;
		this.amount = amount;
	}

	public Category(String categoryName) {
		super();
		this.categoryName = categoryName;
	}
	
//	----------------------------------

	public Integer getCategoryNumber() {
		return categoryNumber;
	}

//	public void setCategoryNumber(Integer categoryNumber) {
//		this.categoryNumber = categoryNumber;
//	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryGroup() {
		return categoryGroup;
	}

	public void setCategoryGroup(String categoryGroup) {
		this.categoryGroup = categoryGroup;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
