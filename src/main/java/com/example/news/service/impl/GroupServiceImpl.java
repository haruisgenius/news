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
		
		return new GroupResponse(group, RtnCode.DATA_EXISTED.getMessage());
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
		Group updateGroup = oldGroupOp.get();
		
		// 編集したいグループ名称　新しい名称の保存
		updateGroup.setGroupName(newGroupName);
		groupDao.save(updateGroup);
		
		return new GroupResponse(updateGroup, RtnCode.SUCCESSFUL.getMessage());
	}
	
	// グループ分類の削除
	@Override
	public GroupResponse deleteGroup(List<Group> deleteGroupList) {
		// 削除したいグループ名称　入力したかどうか
		if(deleteGroupList.size() == 0) {
			return new GroupResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}
		
		// 削除したいグループ　存在しているかどうか
		List<Group> deleteList = new ArrayList<>();
		 List<Group> allGroupList = groupDao.findAll();
		 for(Group deleteGroup : deleteGroupList) {
			 if(allGroupList.contains(deleteGroup)) {
				 deleteList.add(deleteGroup);
			 }
		 }
		if(deleteList.size() != deleteGroupList.size()) {
			return new GroupResponse(RtnCode.NOT_FOUND.getMessage());
		}
		
		// 削除したいグループ分類　文章数0かどうか
		for(Group deleteGroup : deleteList) {
			if(deleteGroup.getNewsAmount() != 0) {
				return new GroupResponse(RtnCode.INCORRECT.getMessage());
			}
		}
		
		// 削除したいグループ分類名称をリストに入れる
		List<String> deleteGroupName = new ArrayList<>();
		for(Group deleteGroup : deleteList) {
			deleteGroupName.add(deleteGroup.getGroupName());
		}
		
		// データベースへの削除
		groupDao.deleteAllById(deleteGroupName);
		
		return new GroupResponse(RtnCode.SUCCESSFUL.getMessage());
	}


	
	
}
