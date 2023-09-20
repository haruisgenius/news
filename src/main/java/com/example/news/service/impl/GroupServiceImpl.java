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
import com.example.news.service.ifs.GroupService;
import com.example.news.vo.CategoryResponse;
import com.example.news.vo.GroupResponse;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupDao groupDao;

	@Autowired
	private CategoryDao categoryDao;

	// -------------------------------------

	// グループ分類の新規登録
	@Override
	public GroupResponse createGroup(String groupName) {
		// グループ名称 入力したかどうか
		if (!StringUtils.hasText(groupName)) {
			return new GroupResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}

		// グループ分類 存在しているかどうか
		Group existedGroup = groupDao.findByGroupName(groupName);
		if (existedGroup != null) {
			// グループ未削除
			if(!existedGroup.isGroupDelete()) {
				return new GroupResponse(RtnCode.DATA_EXISTED.getMessage());
			}
			
			// グループ削除
			if(existedGroup.isGroupDelete()) {
				existedGroup.setGroupDelete(false);
				groupDao.save(existedGroup);
				return new GroupResponse(RtnCode.SUCCESSFUL.getMessage());
			}
		}
		
		// カテゴリーと同じ名称かどうか
		Category existedCategory = categoryDao.findByCategoryName(groupName);
		if (existedCategory != null) {
			return new GroupResponse(RtnCode.DATA_EXISTED.getMessage());
		}
		
		int defaultAmount = 0;

		// データベースへの保存
		Group group = new Group(groupName, defaultAmount);
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
		
		// 新しい名称入力したかどうか
		if (!StringUtils.hasText(newGroupName)) {
			return new GroupResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}

		// 編集したいグループ 存在しているかどうか
		Optional<Group> oldGroupOp = groupDao.findById(groupNumber);
		if(!oldGroupOp.isPresent()) {
			return new GroupResponse(RtnCode.NOT_FOUND.getMessage());
		}
		
		// 新しいグループ名称 カテゴリーに存在しているかどうか
		Category existedCategory = categoryDao.findByCategoryNameAndIsCategoryDeleteFalse(newGroupName);
		if(existedCategory != null) {
			return new GroupResponse(RtnCode.DATA_EXISTED.getMessage());
		}

		// 新しいグループ名称 存在しているかどうか
		Group existedGroup = groupDao.findByGroupName(newGroupName);
		// 存在
		if(existedGroup != null) {
			// 開放している
			if(!existedGroup.isGroupDelete()) {
				return new GroupResponse(RtnCode.DATA_EXISTED.getMessage());
			}
		}
		

		Group updateGroup = oldGroupOp.get();

		// 編集したいグループ名称 新しい名称の保存
		updateGroup.setGroupName(newGroupName);
		groupDao.save(updateGroup);

		return new GroupResponse(updateGroup, RtnCode.SUCCESSFUL.getMessage());
	}

	// グループ分類の削除
	@Override
	public GroupResponse deleteGroup(List<String> deleteGroupStrList) {
		
		// 入力したかどうか
		if (deleteGroupStrList.size() == 0) {
			return new GroupResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}

		// 削除したいグループ 存在しているかどうか
		List<Group> deleteGroupList = groupDao.findAllByGroupNameAndIsGroupDeleteFalse(deleteGroupStrList);
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

	@Override
	public GroupResponse getOneGroup(String groupName) {
		// 
		if(!StringUtils.hasText(groupName)) {
			return new GroupResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}
		Group group = groupDao.findByGroupNameAndIsGroupDeleteFalse(groupName);
		return new GroupResponse(group, RtnCode.SUCCESSFUL.getMessage());
	}

}
