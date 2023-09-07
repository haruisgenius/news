package com.example.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.news.entity.Group;

@Repository
public interface GroupDao extends JpaRepository<Group, Integer> {

}
