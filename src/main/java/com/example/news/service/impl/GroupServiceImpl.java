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
		List<Group> allGroup = groupDao.findAll();
		if (allGroup.size() > 0) {
			for(Group group : allGroup) {
				if(group.getGroupName().equals(groupName)) {
					return new GroupResponse(RtnCode.DATA_EXISTED.getMessage());
				}
			}
		}

		// カテゴリーと同じ名称かどうか
		List<Category> allCategory = categoryDao.findAll();
		if (allCategory.size() > 0) {
			for(Category category : allCategory) {
				if(category.getCategoryName().equals(groupName)) {
					return new GroupResponse(RtnCode.INCORRECT.getMessage());
				}
			}
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
		// 新しいグループ名称 存在しているかどうか
		List<Group> allGroup = groupDao.findAll();
		for(Group group : allGroup) {
			if(group.getGroupName().equals(newGroupName)) {
				return new GroupResponse(RtnCode.DATA_EXISTED.getMessage());
			}
		}
		
		// 新しいグループ名称 カテゴリーに存在しているかどうか
		List<Category> allCategory = categoryDao.findAll();
		for(Category category : allCategory) {
			if(category.getCategoryName().equals(newGroupName)) {
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
	public GroupResponse deleteGroup(List<Group> deleteGroupList) {
		// 全部のグループ
		List<Group> allGroupList = groupDao.findAll();
		// 削除できるグループのリスト
		List<Group> canDeleteGroupList = new ArrayList<>();
		// 入力したかどうか
		if (deleteGroupList.size() == 0) {
			return new GroupResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}

		// 削除したいグループ 存在しているかどうか
		for (Group deleteGroup : deleteGroupList) {
			for (Group oldGroup : allGroupList) {
				if (deleteGroup.getGroupName().equals(oldGroup.getGroupName())) {
					if (oldGroup.getNewsAmount() != 0) {
						return new GroupResponse(RtnCode.INCORRECT.getMessage());
					}
					canDeleteGroupList.add(oldGroup);
				}
			}
		}
		if (canDeleteGroupList.size() != deleteGroupList.size() || canDeleteGroupList.size() == 0) {
			return new GroupResponse(RtnCode.NOT_FOUND.getMessage());
		}

		// データベースへの削除
		groupDao.deleteAll(canDeleteGroupList);

		return new GroupResponse(RtnCode.SUCCESSFUL.getMessage());
	}

	// 全グループの表示
	@Override
	public GroupResponse getAllGroup() {
		List<Group> allGroup = groupDao.findAll();
		return new GroupResponse(allGroup, RtnCode.SUCCESSFUL.getMessage());
	}

}
