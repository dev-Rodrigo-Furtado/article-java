package com.articles.repository;

import java.util.Optional;

import com.articles.entity.Article;

public interface ArticleRepositoryInterface {

    Article save(Article article);

    void delete(Article article);

    Optional<Article> findById(Long id);
}
