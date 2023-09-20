package com.example.news.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.news.entity.Group;

@Repository
public interface GroupDao extends JpaRepository<Group, Integer> {
	
	// グループ名称で検索
	public Group findByGroupName(String groupName);
	
	// グループ名称で検索（開放中）
	public Group findByGroupNameAndIsGroupDeleteFalse(String groupName);
	
	// 全開放中グループ表示
	public List<Group> findAllByIsGroupDeleteFalse();
	
	// 全開放中グループを名称で検索
	public List<Group> findAllByGroupNameAndIsGroupDeleteFalse(List<String> groupNameList);

}
