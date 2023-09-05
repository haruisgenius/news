package com.example.news.service.ifs;

import java.util.List;

import com.example.news.entity.Group;
import com.example.news.vo.GroupResponse;

public interface GroupService {
	
	// グループ分類の新規登録
	public GroupResponse createGroup(String groupName);
	
	// グループ分類名称の編集
	public GroupResponse updateGroupName(String oldGroupName, String newGroupName);
	
	// グループ分類の削除
	public GroupResponse deleteGroup(List<Group> deleteGroupList);

}
