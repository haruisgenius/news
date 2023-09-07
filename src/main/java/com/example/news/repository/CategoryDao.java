package com.example.news.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.news.entity.Category;

@Repository
public interface CategoryDao extends JpaRepository<Category, Integer> {
	
	//
	public List<Category> findByCategoryGroupEquals(String categoryGroup);

}
