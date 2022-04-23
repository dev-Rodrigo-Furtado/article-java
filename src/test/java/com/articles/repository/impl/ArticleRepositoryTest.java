package com.articles.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.articles.entity.Article;
import com.articles.repository.impl.JPA.ArticleJPARepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ArticleRepositoryTest {

    @InjectMocks
    private ArticleRepository articleRepository;

    @Mock
    private ArticleJPARepository articleJPARepository;

    @Test
    public void shouldSaveArticle() {
        Article article = new Article("Article A", "Hello, I'm a article!");
        Article articleCreated = new Article(1L, "Article A", "Hello, I'm a article!");

        when(articleJPARepository.save(eq(article))).thenReturn(articleCreated);

        Article articleExpected = new Article(1L, "Article A", "Hello, I'm a article!");
        Article articleReturned = articleRepository.save(article);

        assertEquals(articleExpected, articleReturned);
    }

    @Test
    public void shouldDeleteArticle() {
        Article article = new Article("Article A", "Hello, I'm a article!");

        doNothing().when(articleJPARepository).delete(article);

        articleRepository.delete(article);

        verify(articleJPARepository, times(1)).delete(eq(article));
    }

    @Test
    public void shouldUpdateArticle() {
        Long id = 1L;

        Article article = new Article(1L, "Article A", "Hello, I'm a article!");
        when(articleJPARepository.findById(eq(id))).thenReturn(Optional.ofNullable(article));

        Article articleExpected = new Article(1L, "Article A", "Hello, I'm a article!");
        Article articleReturned = articleRepository.findById(id).get();

        assertEquals(articleExpected, articleReturned);
    }

}
