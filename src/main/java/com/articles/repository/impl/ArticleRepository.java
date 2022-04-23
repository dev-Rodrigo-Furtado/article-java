package com.articles.repository.impl;

import java.util.Optional;

import com.articles.entity.Article;
import com.articles.repository.ArticleRepositoryInterface;
import com.articles.repository.impl.JPA.ArticleJPARepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleRepository implements ArticleRepositoryInterface {

    @Autowired
    private ArticleJPARepository articleRepositoryJPAInterface;

    @Override
    public Article save(Article article) {
        return articleRepositoryJPAInterface.save(article);
    }

    @Override
    public void delete(Article article) {
        articleRepositoryJPAInterface.delete(article);
    }

    @Override
    public Optional<Article> findById(Long id) {
        return articleRepositoryJPAInterface.findById(id);
    }

}
