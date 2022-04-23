package com.articles.repository.impl.JPA;

import com.articles.entity.Article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleJPARepository extends JpaRepository<Article, Long> {

}
