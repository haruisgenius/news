package com.example.news;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.news.entity.Category;
import com.example.news.repository.CategoryDao;
import com.example.news.service.ifs.CategoryService;
import com.example.news.vo.CategoryResponse;

@SpringBootTest(classes = NewsApplication.class)
public class CategoryTest {
	
	@Autowired
	private CategoryDao cDao;
	
	@Autowired
	private CategoryService cService;
	
	@Test
	public void createCategoryTest() {
		CategoryResponse res1 = cService.createCategory(" ", "AAA");
		System.out.println(res1.getMessage());
		CategoryResponse res2 = cService.createCategory("bb", "");
		System.out.println(res2.getMessage());
		CategoryResponse res3 = cService.createCategory("eee", "ZZ");
		System.out.println(res3.getMessage());
		CategoryResponse res4 = cService.createCategory("ayy", "CCA");
		System.out.println(res4.getMessage());
		CategoryResponse res5 = cService.createCategory("zzz", "AAA");
		System.out.println(res5.getMessage());
		CategoryResponse res6 = cService.createCategory("ayy", "AAA");
		System.out.println(res6.getMessage());
	}
	
	@Test
	public void updateCategoryTest() {
		CategoryResponse res1 = cService.updateCategory(null, "ZZZ");
		System.out.println(res1.getMessage());
		CategoryResponse res2 = cService.updateCategory(1, " ");
		System.out.println(res2.getMessage());
		CategoryResponse res3 = cService.updateCategory(1, "zzz");
		System.out.println(res3.getMessage());
		CategoryResponse res4 = cService.updateCategory(2, "wow");
		System.out.println(res4.getMessage());
	}
	
	@Test
	public void deleteCategoryTest() {
		List<Category> group1 = new ArrayList<>(Arrays.asList(new Category(" ")));
		CategoryResponse res1 = cService.deleteCategory(group1);
		System.out.println(res1.getMessage());
		List<Category> group2 = new ArrayList<>(Arrays.asList(new Category("ayy")));
		CategoryResponse res2 = cService.deleteCategory(group2);
		System.out.println(res2.getMessage());
		List<Category> group3 = new ArrayList<>(Arrays.asList(new Category("zzz")));
		CategoryResponse res3 = cService.deleteCategory(group3);
		System.out.println(res3.getMessage());
	}
	
	@Test
	public void getAllCategory() {
		CategoryResponse res1 = cService.getAllCategory();
		System.out.println(res1.getMessage());
	}
	
	@Test
	public void getCategoryInGroupTest() {
		CategoryResponse res1 = cService.getCategoryInGroup("");
		System.out.println(res1.getMessage());
		CategoryResponse res2 = cService.getCategoryInGroup("be");
		System.out.println(res2.getMessage());
	}

}
