package com.example.news.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.news.constants.RtnCode;
import com.example.news.entity.Group;
import com.example.news.repository.GroupDao;
import com.example.news.service.ifs.GroupService;
import com.example.news.vo.GroupResponse;

@Service
public class GroupServiceImpl implements GroupService {
	
	@Autowired
	private GroupDao groupDao;
	
	// -------------------------------------

	// グループ分類の新規登録
	@Override
	public GroupResponse createGroup(String groupName) {
		// グループ名称　入力したかどうか
		if(!StringUtils.hasText(groupName)) {
			return new GroupResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}
		
		// グループ分類　存在しているかどうか
		Optional<Group> groupOp = groupDao.findById(groupName);
		if(groupOp.isPresent()) {
			return new GroupResponse(RtnCode.DATA_EXISTED.getMessage());
		}
		
		// データベースへの保存
		Group group = new Group(groupName);
		groupDao.save(group);
		
		return new GroupResponse(group, RtnCode.SUCCESSFUL.getMessage());
	}

	
	// グループ分類名称の編集
	@Override
	public GroupResponse updateGroupName(String oldGroupName, String newGroupName) {
		// 編集したいグループ名称　新旧　入力したかどうか
		if(!StringUtils.hasText(oldGroupName) || !StringUtils.hasText(newGroupName)) {
			return new GroupResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}
		
		// 編集したいグループ名称　存在しているかどうか
		Optional<Group> oldGroupOp = groupDao.findById(oldGroupName);
		if(!oldGroupOp.isPresent()) {
			return new GroupResponse(RtnCode.NOT_FOUND.getMessage());
		}
		// 新しいグループ名称　存在しているかどうか
		Optional<Group> newGroupOp = groupDao.findById(newGroupName);
		if(newGroupOp.isPresent()) {
			return new GroupResponse(RtnCode.DATA_EXISTED.getMessage());
		}
		Group updateGroup = oldGroupOp.get();
		
		// 編集したいグループ名称　新しい名称の保存
		updateGroup.setGroupName(newGroupName);
		groupDao.save(updateGroup);
		
		return new GroupResponse(updateGroup, RtnCode.SUCCESSFUL.getMessage());
	}
	
	// グループ分類の削除
	@Override
	public GroupResponse deleteGroup(List<Group> deleteGroupList) {
		// 
		List<Group> allGroupList = groupDao.findAll();
		// 
		List<Group> canDeleteGroupList = new ArrayList<>();
		// 
		if(deleteGroupList.size() == 0) {
			return new GroupResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}
		
		// 削除したいグループ　存在しているかどうか
		for(Group deleteGroup : deleteGroupList) {
			for(Group oldGroup : allGroupList) {
				if(deleteGroup.getGroupName().equals(oldGroup.getGroupName())) {
					if(oldGroup.getNewsAmount() != 0) {
						return new GroupResponse(RtnCode.INCORRECT.getMessage());
					}
					canDeleteGroupList.add(oldGroup);
				}
			}
		}
		if(canDeleteGroupList.size() != deleteGroupList.size() || canDeleteGroupList.size() == 0) {
			return new GroupResponse(RtnCode.NOT_FOUND.getMessage());
		}
		
		// データベースへの削除
		groupDao.deleteAll(canDeleteGroupList);
		
		return new GroupResponse(RtnCode.SUCCESSFUL.getMessage());
	}


	
	
}
