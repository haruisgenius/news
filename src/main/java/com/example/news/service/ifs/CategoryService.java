package com.example.news.service.ifs;

import java.util.List;

import com.example.news.entity.Category;
import com.example.news.vo.CategoryResponse;

public interface CategoryService {
	
	// カテゴリーの新規登録
	public CategoryResponse createCategory(String categoryName, String categoryGroup);
	
	// カテゴリー名称の編集
	public CategoryResponse updateCategory(Integer categoryNumber, String newCategoryName);

	// カテゴリー分類の削除
	public CategoryResponse deleteCategory(List<String> deleteCategoryStrList);
	
	// 全カテゴリーの表示
	public CategoryResponse getAllCategory();
	
	// 某グループのカテゴリーの表示
	public CategoryResponse getCategoryInGroup(String categoryGroup);
	
}
