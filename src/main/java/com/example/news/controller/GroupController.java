package com.example.news.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.news.service.ifs.GroupService;
import com.example.news.vo.GroupResponse;

@Controller
public class GroupController {
	
	private GroupService gService;

//	@RequestMapping(value = "/home")
//	public String getAllGroup(@RequestParam(name = "name", required = false, defaultValue = "World") 
//	String name, Model model) {
//		GroupResponse allGroup = gService.getAllGroup();
//		model.addAttribute("allGroup", allGroup);
//		return "home";
//	}
	
}
