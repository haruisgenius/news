package com.example.news.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.news.entity.News;

@Repository
public interface NewsDao extends JpaRepository<News, Integer> {

	// ニュースナンバーでニュース（開放中）検索　>　削除or閉鎖
	public List<News> findAllByNewsNumberInAndIsNewsDeleteFalse(List<Integer> deleteNewsNumberList);
	
	// 全ニュース（(開放中&閉鎖)）を表示 > 管理者
	public List<News> findAllByIsNewsDeleteFalse();
	
	// ニュース検索（開放中） > ユーザー&管理者
	public News findByNewsNumberAndIsNewsDeleteFalse(Integer newsNumber);
	
	// 全ニュース（開放中）を表示　>　ユーザー
	public News findAllByIsNewsDeleteFalseAndIsOpenTrue();
}
