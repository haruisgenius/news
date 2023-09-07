package com.example.news.vo;

import java.util.List;

import com.example.news.entity.Group;

public class GroupResponse {
	
	private Group group;
	
	private List<Group> groupList;
	
	private String message;
	
	// ----------------------------

	public GroupResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	// ----------------------------

	public GroupResponse(Group group, String message) {
		super();
		this.group = group;
		this.message = message;
	}

	public GroupResponse(String message) {
		super();
		this.message = message;
	}

	public GroupResponse(List<Group> groupList, String message) {
		super();
		this.groupList = groupList;
		this.message = message;
	}
	
	// ----------------------------

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public List<Group> getGroupList() {
		return groupList;
	}
	
	public void setGroupList(List<Group> groupList) {
		this.groupList = groupList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	

}
