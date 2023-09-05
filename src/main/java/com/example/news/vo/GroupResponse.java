package com.example.news.vo;

import java.util.List;

import com.example.news.entity.Group;

public class GroupResponse {
	
	private Group group;
	
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
	
	// ----------------------------

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
