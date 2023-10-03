package com.example.news.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.news.service.ifs.NewsService;
import com.example.news.vo.NewsResponse;

@Controller
public class NewsController {

	private NewsService nService;
	
	@RequestMapping(value = "/home")
	public String findAllNewsInUser(@RequestParam(name = "name", required = false, defaultValue = "World") 
	String name, Model model) {
		NewsResponse allNews = nService.findAllNews();
		model.addAttribute("allGroup", allNews);
		return "home";
	}
	
}
