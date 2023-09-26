package com.example.news.service.ifs;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.news.entity.News;
import com.example.news.vo.NewsResponse;

public interface NewsService {
	
	// ニュースの新規登録
	public NewsResponse createNews(String title, String subTitle, LocalDate releaseDate, String category, String content);

	// ニュースの編集
	public NewsResponse updateNews(Integer newsNumber, String title, String subTitle, LocalDate releaseDate, String category,
			String content);
	
	// ニュースの削除
	public NewsResponse deleteNews(List<Integer> deleteNewsNumberList);
	
	// ニュースの内容表示
	public NewsResponse findNews(Integer newsNumber);
	
	// 全ニュースの表示(開放中&閉鎖) > 管理者
	public NewsResponse findAllNews();
	
	// ニュースを閉鎖
	public NewsResponse closeNews(List<Integer> newsNumberList);
	
	// ニュースを開放
	public NewsResponse openNews(List<Integer> newsNumberList);
	
	// ニュースをオート開放
	public NewsResponse autoSetOpen();
	
}
