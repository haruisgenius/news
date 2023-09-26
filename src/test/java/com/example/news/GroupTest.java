package com.example.news;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.news.entity.Group;
import com.example.news.repository.GroupDao;
import com.example.news.service.ifs.GroupService;
import com.example.news.vo.GroupResponse;

@SpringBootTest(classes = NewsApplication.class)
public class GroupTest {
	
	@Autowired
	private GroupDao gDao;
	
	@Autowired
	private GroupService gService;
	
	@Test
	public void createGroupTest() {
		GroupResponse res1 = gService.createGroup("");
		System.out.println(res1.getMessage());
		GroupResponse res2 = gService.createGroup("AAA");
		System.out.println(res2.getMessage());
		GroupResponse res3 = gService.createGroup("AAA");
		System.out.println(res3.getMessage());
		GroupResponse res4 = gService.createGroup("ZZZ");
		System.out.println(res4.getMessage());
	}
	
	@Test
	public void updateGroupTest() {
		GroupResponse res1 = gService.updateGroupName(null, "CCC");
		System.out.println(res1.getMessage());
		GroupResponse res2 = gService.updateGroupName(0, "aa");
		System.out.println(res2.getMessage());
		GroupResponse res3 = gService.updateGroupName(1, "");
		System.out.println(res3.getMessage());
		GroupResponse res4 = gService.updateGroupName(1, "ayy");
		System.out.println(res4.getMessage());
		GroupResponse res5 = gService.updateGroupName(2, "CCA");
		System.out.println(res5.getMessage());
	}
	
	@Test
	public void deleteGroupTest() {
		List<String> group1 = new ArrayList<>(Arrays.asList("AAA"));
		GroupResponse res1 = gService.deleteGroup(group1);
		System.out.println(res1.getMessage());
		List<String> group2 = new ArrayList<>(Arrays.asList(""));
		GroupResponse res2 = gService.deleteGroup(group2);
		System.out.println(res2.getMessage());
		List<String> group3 = new ArrayList<>(Arrays.asList("ERR","CCA"));
		GroupResponse res3 = gService.deleteGroup(group3);
		System.out.println(res3.getMessage());
		List<String> group4 = new ArrayList<>(Arrays.asList("CCA"));
		GroupResponse res4 = gService.deleteGroup(group4);
		System.out.println(res4.getMessage());
	}

	@Test
	public void findAllGroupTest() {
		GroupResponse res1 = gService.getAllGroup();
		System.out.println(res1.getMessage());
	}
	
	@Test
	public void findOneGroupTet() {
		GroupResponse res1 = gService.getOneGroup("CCA");
		System.out.println(res1.getMessage());
	}
	
}
