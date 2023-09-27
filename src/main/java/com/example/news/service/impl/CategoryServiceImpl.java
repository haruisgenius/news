package com.example.news.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.news.constants.RtnCode;
import com.example.news.entity.Category;
import com.example.news.entity.Group;
import com.example.news.repository.CategoryDao;
import com.example.news.repository.GroupDao;
import com.example.news.service.ifs.CategoryService;
import com.example.news.vo.CategoryResponse;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private GroupDao groupDao;

	@Autowired
	private CategoryDao categoryDao;

	// ---------------------------

	// カテゴリーの新規登録
	@Override
	public CategoryResponse createCategory(String categoryName, String categoryGroup) {
		// カテゴリー名称と所属グループ 入力したかどうか
		if (!StringUtils.hasText(categoryName) || !StringUtils.hasText(categoryGroup)) {
			return new CategoryResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}

		// グループ 存在しているかどうか
		Group existedGroup = groupDao.findByGroupName(categoryGroup);
		if(existedGroup != null) {
			return new CategoryResponse(RtnCode.NOT_FOUND.getMessage());
		}
			
		// 増やしたいカテゴリーと同じ名称グループ 存在しているかどうか
		Group ctgNameGroup = groupDao.findByGroupName(categoryName);
		if(ctgNameGroup != null) {
			return new CategoryResponse(RtnCode.DATA_EXISTED.getMessage());
		}
		
		// 同じ名称カテゴリー 存在しているかどうか
		Category existedCategory = categoryDao.findByCategoryName(categoryName);
		//　カテゴリー存在
		if(existedCategory != null) {
			// カテゴリー未削除
			if(!existedCategory.isCategoryDelete()) {
				return new CategoryResponse(RtnCode.DATA_EXISTED.getMessage());
			}
			//  カテゴリー削除 > 開放
			existedCategory.setCategoryDelete(true);
			existedCategory.setCategoryGroup(categoryGroup);
			categoryDao.save(existedCategory);
			return new CategoryResponse(existedCategory, RtnCode.SUCCESSFUL.getMessage());
		}
		
		int defaultAmount = 0;

		// データベースへの保存
		Category category = new Category(categoryName, categoryGroup, defaultAmount);
		categoryDao.save(category);

		return new CategoryResponse(category, RtnCode.SUCCESSFUL.getMessage());
	}

	// カテゴリーの編集
	@Override
	public CategoryResponse updateCategory(Integer categoryNumber, String newCategoryName) {
		// 編集したいカテゴリーと新しいカテゴリー名称 入力したかどうか
		if(categoryNumber == null) {
			return new CategoryResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}
		if (!StringUtils.hasText(newCategoryName)) {
			return new CategoryResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}

		// 編集したいカテゴリー 存在しているかどうか
		Optional<Category> updateCategoryOp = categoryDao.findById(categoryNumber);
		if (!updateCategoryOp.isPresent()) {
			return new CategoryResponse(RtnCode.NOT_FOUND.getMessage());
		}

		// TODO
		// 新しいカテゴリー名称 存在しているかどうか
		Category existedCategory = categoryDao.findByCategoryName(newCategoryName);
		if(existedCategory != null) {
			return new CategoryResponse(RtnCode.DATA_EXISTED.getMessage());
		}
		
		// グループに同じ名称存在しているかどうか
		Group existedGroup = groupDao.findByGroupName(newCategoryName);
		if(existedGroup != null) {
			return new CategoryResponse(RtnCode.DATA_EXISTED.getMessage());
		}
		
		Category updateCategory = updateCategoryOp.get();

		// データベースへの保存
		updateCategory.setCategoryName(newCategoryName);
		categoryDao.save(updateCategory);

		return new CategoryResponse(updateCategory, RtnCode.SUCCESSFUL.getMessage());
	}

	// カテゴリー分類の削除
	@Override
	public CategoryResponse deleteCategory(List<String> deleteCategoryStrList) {
		// 入力したかどうか
		if(deleteCategoryStrList.size() == 0) {
			return new CategoryResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}
		
		// 削除したいカテゴリー　存在しているかどうか
		List<Category> deleteCategoryList = categoryDao.findAllByCategoryNameInAndIsCategoryDeleteFalse(deleteCategoryStrList);
		if(deleteCategoryList.size() != deleteCategoryStrList.size()) {
			return new CategoryResponse(RtnCode.NOT_FOUND.getMessage());
		}
		
		// 削除かどうか　> ture
		for(Category deleteCategory : deleteCategoryList) {
			deleteCategory.setCategoryDelete(true);
		}
		
		// データベースへの保存
		categoryDao.saveAll(deleteCategoryList);

		return new CategoryResponse(RtnCode.SUCCESSFUL.getMessage());
	}

	// 全カテゴリーの表示
	public CategoryResponse getAllCategory() {
		List<Category> allCategory = categoryDao.findAllByIsCategoryDeleteFalse();	
		return new CategoryResponse(allCategory, RtnCode.SUCCESSFUL.getMessage());
	}

	// 某グループのカテゴリーの表示
	@Override
	public CategoryResponse getCategoryInGroup(String categoryGroup) {
		// 入力かどうか
		if(!StringUtils.hasText(categoryGroup)) {
			return new CategoryResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}
		
		// データベースへの検索
		List<Category> categoryInGroup = categoryDao.findAllByCategoryGroupAndIsCategoryDeleteFalse(categoryGroup);
		
		return new CategoryResponse(categoryInGroup, RtnCode.SUCCESSFUL.getMessage());
	}

	
	
	
}
