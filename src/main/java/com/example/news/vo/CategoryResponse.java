package com.example.news.vo;

import java.util.List;

import com.example.news.entity.Category;

public class CategoryResponse {
	
	private Category category;
	
	private List<Category> categoryList;
	
	private String message;
	
	// ------------------------------

	public CategoryResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	// ------------------------------
	
	public CategoryResponse(Category category, List<Category> categoryList, String message) {
		super();
		this.category = category;
		this.categoryList = categoryList;
		this.message = message;
	}

	public CategoryResponse(Category category, String message) {
		super();
		this.category = category;
		this.message = message;
	}

	public CategoryResponse(List<Category> categoryList, String message) {
		super();
		this.categoryList = categoryList;
		this.message = message;
	}
	
	public CategoryResponse(String message) {
		super();
		this.message = message;
	}

	// ------------------------------

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
