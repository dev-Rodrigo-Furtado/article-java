package com.articles.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.articles.dto.ArticleCreateDTO;
import com.articles.dto.ArticleDTO;
import com.articles.entity.Article;
import com.articles.exception.DataNotFoundException;
import com.articles.repository.ArticleRepositoryInterface;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ArticleServiceTest {

    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ArticleRepositoryInterface articleRepository;

    @Test
    public void shouldCreateArticle() {
        ArticleCreateDTO articleCreateDTO = new ArticleCreateDTO("Article A", "Hello, I'm a article!");
        Article article = new Article("Article A", "Hello, I'm a article!");

        Article articleCreated = new Article(1L, "Article A", "Hello, I'm a article!");
        when(articleRepository.save(eq(article))).thenReturn(articleCreated);

        ArticleDTO articleDTOExpected = new ArticleDTO(1L, "Article A", "Hello, I'm a article!");
        ArticleDTO articleDTOReturned = articleService.create(articleCreateDTO);

        assertEquals(articleDTOExpected, articleDTOReturned);
    }

    @Test
    public void shouldUpdateArticle() throws DataNotFoundException {
        Long id = 1L;
        ArticleDTO articleDTO = new ArticleDTO("Article B", "Hello, I'm a article updated!");

        Article article = new Article(1L, "Article A", "Hello, I'm a article!");
        when(articleRepository.findById(eq(id))).thenReturn(Optional.ofNullable(article));

        ArticleDTO articleDTOExpected = new ArticleDTO(1L, "Article B", "Hello, I'm a article updated!");
        ArticleDTO articleDTOReturned = articleService.update(id, articleDTO);

        assertEquals(articleDTOExpected, articleDTOReturned);
    }

    @Test
    public void shouldGetArticleById() throws DataNotFoundException {
        Long id = 1L;
        Article article = new Article(1L, "Article A", "Hello, I'm a article!");

        when(articleRepository.findById(eq(id))).thenReturn(Optional.ofNullable(article));

        ArticleDTO articleDTOExpected = new ArticleDTO(1L, "Article A", "Hello, I'm a article!");
        ArticleDTO articleDTOReturned = articleService.findById(id);

        assertEquals(articleDTOExpected, articleDTOReturned);
    }

    @Test
    public void shouldGetArticleByIdThrowsDataNotFoundException() {
        Long id = 1L;

        Article article = null;
        when(articleRepository.findById(eq(id))).thenReturn(Optional.ofNullable(article));

        DataNotFoundException thrown = assertThrows(
                DataNotFoundException.class,
                () -> {
                    articleService.findById(id);
                });

        assertEquals("Article with id 1 not found!", thrown.getMessage());
    }

    @Test
    public void shouldDeleteArticleById() throws DataNotFoundException {
        Long id = 1L;

        Article article = new Article(1L, "Article A", "Hello, I'm a article!");
        when(articleRepository.findById(eq(id))).thenReturn(Optional.ofNullable(article));

        doNothing().when(articleRepository).delete(article);

        articleService.delete(id);

        verify(articleRepository, times(1)).delete(eq(article));
    }
}
