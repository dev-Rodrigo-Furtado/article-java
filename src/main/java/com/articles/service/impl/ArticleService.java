package com.articles.service.impl;

import com.articles.dto.ArticleCreateDTO;
import com.articles.dto.ArticleDTO;
import com.articles.entity.Article;
import com.articles.exception.DataNotFoundException;
import com.articles.repository.ArticleRepositoryInterface;
import com.articles.service.ArticleServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleService implements ArticleServiceInterface {

    @Autowired
    private ArticleRepositoryInterface articleRepository;

    @Transactional
    public ArticleDTO create(ArticleCreateDTO articleDTO) {
        Article article = articleRepository.save(articleAdapter(articleDTO));
        return articleAdapter(article);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ArticleDTO update(Long id, ArticleDTO articleDTO) throws DataNotFoundException {

        Article article = findArticleById(id);

        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());

        return articleAdapter(article);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ArticleDTO findById(Long id) throws DataNotFoundException {
        Article article = findArticleById(id);
        return articleAdapter(article);
    }

    @Transactional
    public void delete(Long id) throws DataNotFoundException {
        Article article = findArticleById(id);
        articleRepository.delete(article);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    private Article findArticleById(Long id) throws DataNotFoundException {
        return articleRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException("Article with id " + id + " not found!"));
    }

    private Article articleAdapter(ArticleCreateDTO articleDTO) {
        String title = articleDTO.getTitle();
        String content = articleDTO.getContent();

        return new Article(title, content);
    }

    private ArticleDTO articleAdapter(Article article) {
        Long id = article.getId();
        String title = article.getTitle();
        String content = article.getContent();

        return new ArticleDTO(id, title, content);
    }
}
