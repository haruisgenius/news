package com.example.news.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.news.constants.Constant;
import com.example.news.constants.RtnCode;
import com.example.news.entity.Category;
import com.example.news.entity.Group;
import com.example.news.entity.News;
import com.example.news.repository.CategoryDao;
import com.example.news.repository.GroupDao;
import com.example.news.repository.NewsDao;
import com.example.news.service.ifs.GroupService;
import com.example.news.vo.CategoryResponse;
import com.example.news.vo.GroupResponse;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupDao groupDao;

	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private NewsDao newsDao;

	// -------------------------------------

	// グループ分類の新規登録
	@Override
	public GroupResponse createGroup(String groupName) {
		// グループ名称 チェック
		GroupResponse checkGroupNameResult = checkGroupName(groupName);
		if(checkGroupNameResult != null) {
			return checkGroupNameResult;
		}
		
		// 
		GroupResponse checkInGroupResult = checkNameInGroup(groupName);
		if(checkInGroupResult != null) {
			return checkInGroupResult;
		}
		
		// 
		GroupResponse checkInCategoryResult = checkNameInCategory(groupName);
		if(checkInCategoryResult != null) {
			return checkInCategoryResult;
		}

		// データベースへの保存
		Group group = new Group(groupName, Constant.defaultNewsAmount.getDefaultAmount());
		groupDao.save(group);

		return new GroupResponse(group, RtnCode.SUCCESSFUL.getMessage());
	}

	// グループ分類名称の編集
	@Override
	public GroupResponse updateGroupName(Integer groupNumber, String newGroupName) {
		// 編集したいグループ入力したかどうか
		if(groupNumber == null) {
			return new GroupResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}
		// 入力したかどうか
		GroupResponse checkGroupNameResult = checkGroupName(newGroupName);
		if(checkGroupNameResult != null) {
			return checkGroupNameResult;
		}

		// 編集したいグループ 存在しているかどうか
		Optional<Group> oldGroupOp = groupDao.findById(groupNumber);
		if(!oldGroupOp.isPresent()) {
			return new GroupResponse(RtnCode.NOT_FOUND.getMessage());
		}
		
		// 新しいグループ名称 カテゴリーに存在しているかどうか
		GroupResponse checkCategoryDatabaseResult = checkNameInCategory(newGroupName);
		if(checkCategoryDatabaseResult != null) {
			return checkCategoryDatabaseResult;
		}

		// 新しいグループ名称 存在しているかどうか
		GroupResponse checkGroupDatabaseResult = checkNameInGroup(newGroupName);
		if(checkGroupDatabaseResult != null) {
			return checkGroupDatabaseResult;
		}
		

		Group updateGroup = oldGroupOp.get();
		// カテゴリー　所属グループ変更
		List<Category> allCategoryInGroup = categoryDao.findAllByCategoryGroupAndIsCategoryDeleteFalse(updateGroup.getGroupName());
		if(allCategoryInGroup.size() > 0) {
			for(Category category : allCategoryInGroup) {
				category.setCategoryGroup(newGroupName);
			}
		}
		categoryDao.saveAll(allCategoryInGroup);
		
		// ニュース　所属グループ変更
		if(updateGroup.getNewsAmount() > 0) {
			List<News> allNewsInGroup = newsDao.findAllByNewsGroupAndIsNewsDeleteFalse(updateGroup.getGroupName());
				for(News news : allNewsInGroup) {
					news.setNewsGroup(newGroupName);
				}
			newsDao.saveAll(allNewsInGroup);
		}
		
		// 編集したいグループ名称 新しい名称の保存
		updateGroup.setGroupName(newGroupName);
		groupDao.save(updateGroup);
		

		return new GroupResponse(updateGroup, RtnCode.SUCCESSFUL.getMessage());
	}

	// グループ分類の削除
	@Override
	public GroupResponse deleteGroup(List<String> deleteGroupStrList) {
		
		// 入力したかどうか
		for(String deleteGroupStr : deleteGroupStrList) {
			GroupResponse checkGroupNameResult = checkGroupName(deleteGroupStr);
			if(checkGroupNameResult != null) {
				return checkGroupNameResult;
			}
		}

		// 削除したいグループ 存在しているかどうか
		List<Group> deleteGroupList = groupDao.findAllByGroupNameInAndIsGroupDeleteFalse(deleteGroupStrList);
		if(deleteGroupList.size() != deleteGroupStrList.size()) {
			return new GroupResponse(RtnCode.NOT_FOUND.getMessage());
		}
		
		// 文章数0以上かどうか
		for(Group group : deleteGroupList) {
			if(group.getNewsAmount() > 0) {
				return new GroupResponse(RtnCode.INCORRECT.getMessage());
			}
		}
		
		// データベースへの削除
		for(Group group : deleteGroupList) {
			group.setGroupDelete(true);
		}
		groupDao.saveAll(deleteGroupList);

		return new GroupResponse(RtnCode.SUCCESSFUL.getMessage());
	}

	// 全グループの表示
	@Override
	public GroupResponse getAllGroup() {
		List<Group> allGroup = groupDao.findAllByIsGroupDeleteFalse();
		return new GroupResponse(allGroup, RtnCode.SUCCESSFUL.getMessage());
	}

	// グループの表示
	@Override
	public GroupResponse getOneGroup(String groupName) {
		// 入力チェック
		GroupResponse checkGroupNameResult = checkGroupName(groupName);
		if(checkGroupNameResult != null) {
			return checkGroupNameResult;
		}
		
		// 
		Group group = groupDao.findByGroupNameAndIsGroupDeleteFalse(groupName);
		return new GroupResponse(group, RtnCode.SUCCESSFUL.getMessage());
	}
	
	// -----------------------------
	
	// 入力かどうか　チェック
	private GroupResponse checkGroupName(String gorupName) {
		if(!StringUtils.hasText(gorupName)) {
			return new GroupResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}
		
		return null;
	}
	
	// カテゴリーデータベース　チェック
	private GroupResponse checkNameInCategory(String groupName) {
		// 同じカテゴリー確認
		Category sameNameCategory = categoryDao.findByCategoryNameAndIsCategoryDeleteFalse(groupName);
		if(sameNameCategory != null) {
			return new GroupResponse(RtnCode.DATA_EXISTED.getMessage());
		}
		
		return null;
	}
	
	// グループデータベース　チェック
	private GroupResponse checkNameInGroup(String groupName) {
		// 同じグループ確認
		Group existedGroup = groupDao.findByGroupNameAndIsGroupDeleteFalse(groupName);
		if(existedGroup != null) {
			return new GroupResponse(RtnCode.DATA_EXISTED.getMessage());
		}
		
		return null;
	}
	
	

}
