package com.example.homework.repository;

import com.example.homework.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ArticleRepository extends JpaRepository<Article, Long> {
    boolean existsByIdAndPassword(Long id, String password);
    boolean existsByIdAndUsername(Long id, String username);
}

