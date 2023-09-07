package com.example.news.vo;

import java.util.List;

import com.example.news.entity.Category;

public class CategoryRequest {
	
	// カテゴリーの番号
	private Integer categoryNumber;
	
	// カテゴリー
	private String categoryName;
	
	// グループ(分類)
	private String categoryGroup;
	
	// カテゴリー リスト
	private List<Category> categoryList;
	
	// 文章数
	private int amount;
	
	// --------------------------------

	public CategoryRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	// --------------------------------

	public CategoryRequest(Integer categoryNumber, String categoryName, String categoryGroup,
			List<Category> categoryList, int amount) {
		super();
		this.categoryNumber = categoryNumber;
		this.categoryName = categoryName;
		this.categoryGroup = categoryGroup;
		this.categoryList = categoryList;
		this.amount = amount;
	}
	
	
	// --------------------------------

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

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	

}
