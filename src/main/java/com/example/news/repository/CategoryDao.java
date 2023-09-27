package com.example.news.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.news.entity.Category;

@Repository
public interface CategoryDao extends JpaRepository<Category, Integer> {
	
	// 某グループのカテゴリーの表示
	public List<Category> findAllByCategoryGroupAndIsCategoryDeleteFalse(String categoryGroup);
	
	// 全カテゴリーの表示
	public List<Category> findAllByIsCategoryDeleteFalse();
	
	// カテゴリー検索
	public Category findByCategoryName(String categoryName);
	
	// 開放カテゴリー検索
	public Category findByCategoryNameAndIsCategoryDeleteFalse(String categoryName);
	
	// カテゴリー名称で検索
	public List<Category> findAllByCategoryNameInAndIsCategoryDeleteFalse(List<String> categoryNameList);

}
