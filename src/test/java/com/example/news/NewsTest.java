package com.example.news;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.news.repository.CategoryDao;
import com.example.news.repository.GroupDao;
import com.example.news.repository.NewsDao;
import com.example.news.service.ifs.NewsService;
import com.example.news.vo.NewsResponse;

@SpringBootTest(classes = NewsApplication.class)
public class NewsTest {

	@Autowired
	private GroupDao gDao;
	
	@Autowired
	private CategoryDao cDao;
	
	@Autowired
	private NewsDao nDao;
	
	@Autowired
	private NewsService nService;
	
	@Test
	public void createNewsTest() {
		NewsResponse res1 = nService.createNews("", "test1", LocalDate.of(2023, 11, 12), "ayy", "test1 test1 test1");
		System.out.println(res1.getMessage());
		NewsResponse res2 = nService.createNews("test1", "", LocalDate.of(2023, 11, 12), "ayy", "test1 test1 test1");
		System.out.println(res2.getMessage());
		NewsResponse res3 = nService.createNews("test2", "test1", LocalDate.of(2023, 01, 12), "ayy", "test2 test2 test2");
		System.out.println(res3.getMessage());
		NewsResponse res4 = nService.createNews("test3", "test1", LocalDate.of(2023, 11, 12), "", "test3 test3 test3");
		System.out.println(res4.getMessage());
		NewsResponse res5 = nService.createNews("test4", "test1", LocalDate.of(2023, 11, 12), "ayy", "");
		System.out.println(res5.getMessage());
		NewsResponse res6 = nService.createNews("test5", "test1", LocalDate.of(2023, 11, 12), "ayy", "test5 test5 test5");
		System.out.println(res6.getMessage());
		NewsResponse res7 = nService.createNews("test6", "test1", LocalDate.now(), "ayy", "test6 test6 test6");
		System.out.println(res7.getMessage());
	}
	
	@Test
	public void updateNewsTest() {
//		NewsResponse res1 = nService.updateNews(null, null, null, null, null, null);
//		System.out.println(res1.getMessage());
//		NewsResponse res2 = nService.updateNews(1, null, null, null, null, null);
//		System.out.println(res2.getMessage());
//		NewsResponse res3 = nService.updateNews(1, "update1", null, null, null, null);
//		System.out.println(res3.getMessage());
//		NewsResponse res4 = nService.updateNews(1, "update1", "update1", LocalDate.of(2023, 01, 22), null, null);
//		System.out.println(res4.getMessage());
//		NewsResponse res5 = nService.updateNews(1, "update1", "update1", LocalDate.of(2023, 11, 17), "kkk", null);
//		System.out.println(res5.getMessage());
//		NewsResponse res6 = nService.updateNews(1, "update1", "update", null, "pog", null);
//		System.out.println(res6.getMessage());
//		NewsResponse res7 = nService.updateNews(99, null, null, null, null, null);
//		System.out.println(res7.getMessage());
//		NewsResponse res8 = nService.updateNews(1, "update1", "update", LocalDate.now(), "pog", "update1 update1");
//		System.out.println(res8.getMessage());
		NewsResponse res9 = nService.updateNews(2, "update2", "update2", LocalDate.of(2023, 11, 27), "wow", "update");
		System.out.println(res9.getMessage());
	}
	
	
	
	
}
