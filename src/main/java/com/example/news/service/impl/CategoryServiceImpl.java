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
		List<Group> allGroup = groupDao.findAll();
		List<String> allGroupStr = new ArrayList<>();
		for(Group group : allGroup) {
			allGroupStr.add(group.getGroupName());
		}
		if(!allGroupStr.contains(categoryGroup)) {
			return new CategoryResponse(RtnCode.NOT_FOUND.getMessage());
		}
		
		// カテゴリー 同じ名称存在しているかどうか
		List<Category> allCategory = categoryDao.findAll();
		if(allCategory.size() > 0 ) {
			for(Category category : allCategory) {
				if(category.getCategoryName().equals(categoryName)) {
					return new CategoryResponse(RtnCode.DATA_EXISTED.getMessage());
				}
			}
		}

		// グループと同じ名称存在しているかどうか
		for(Group group : allGroup) {
			if(group.getGroupName().equals(categoryName)) {
				return new CategoryResponse(RtnCode.INCORRECT.getMessage());
			}
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

		// 新しいカテゴリー名称 存在しているかどうか
		List<Category> allCategory = categoryDao.findAll();
		for(Category category : allCategory) {
			if(category.getCategoryName().equals(newCategoryName)) {
				return new CategoryResponse(RtnCode.DATA_EXISTED.getMessage());
			}
		}
		
		// グループに同じ名称存在しているかどうか
		List<Group> allGroup = groupDao.findAll();
		for(Group group : allGroup) {
			if(group.getGroupName().equals(newCategoryName)) {
				return new CategoryResponse(RtnCode.INCORRECT.getMessage());
			}
		}
		
		Category updateCategory = updateCategoryOp.get();

		// データベースへの保存
		updateCategory.setCategoryName(newCategoryName);
		categoryDao.save(updateCategory);

		return new CategoryResponse(updateCategory, RtnCode.SUCCESSFUL.getMessage());
	}

	// カテゴリー分類の削除
	@Override
	public CategoryResponse deleteCategory(List<Category> deleteCategoryList) {
		// 全部のカテゴリー
		List<Category> allCategoryList = categoryDao.findAll();
		// 削除できるカテゴリーのリスト
		List<Category> canDeleteCategoryList = new ArrayList<>();
		// 入力したかどうか
		if(deleteCategoryList.size() == 0) {
			return new CategoryResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}
		
		// 削除したいカテゴリー　存在しているかどうか
		for(Category deleteCategory : deleteCategoryList) {
			for(Category oldCategory : allCategoryList) {
				if(deleteCategory.getCategoryName().equals(oldCategory.getCategoryName())) {
					if(oldCategory.getAmount() != 0) {
						return new CategoryResponse(RtnCode.INCORRECT.getMessage());
					}
					canDeleteCategoryList.add(oldCategory);
				}
			}
		}
		
		if(canDeleteCategoryList.size() != deleteCategoryList.size()) {
			return new CategoryResponse(RtnCode.NOT_FOUND.getMessage());
		}
		
		// データベースへの削除
		categoryDao.deleteAll(canDeleteCategoryList);

		return new CategoryResponse(RtnCode.SUCCESSFUL.getMessage());
	}

	// 全カテゴリーの表示
	public CategoryResponse getAllCategory() {
		List<Category> allCategory = categoryDao.findAll();	
		return new CategoryResponse(allCategory, RtnCode.SUCCESSFUL.getMessage());
	}

	// 某グループのカテゴリーの表示
	@Override
	public CategoryResponse getCategoryInGroup(String categoryGroup) {
		// 
		if(!StringUtils.hasText(categoryGroup)) {
			return new CategoryResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}
		
		// 
		List<Category> categoryInGroup = categoryDao.findByCategoryGroupEquals(categoryGroup);
		
		return new CategoryResponse(categoryInGroup, RtnCode.SUCCESSFUL.getMessage());
	}

	
	
	
}
