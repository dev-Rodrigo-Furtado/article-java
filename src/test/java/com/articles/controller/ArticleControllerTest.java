package com.articles.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.articles.dto.ArticleCreateDTO;
import com.articles.dto.ArticleDTO;
import com.articles.exception.DataNotFoundException;
import com.articles.response.Error;
import com.articles.response.Response;
import com.articles.service.ArticleServiceInterface;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class ArticleControllerTest {

    @InjectMocks
    private ArticleController articleController;

    @Mock
    private ArticleServiceInterface articleService;

    @Test
    public void createArticle_shoudlReturnExpectedBody() {

        ArticleCreateDTO articleCreateDTO = new ArticleCreateDTO("Article A", "Hello, I'm a article!");
        ArticleDTO articleDTO = new ArticleDTO(1L, "Article A", "Hello, I'm a article!");

        when(articleService.create(eq(articleCreateDTO))).thenReturn(articleDTO);

        ResponseEntity<Response<ArticleDTO>> response = articleController.createArticle(articleCreateDTO);

        ArticleDTO articleDTOExpected = new ArticleDTO(1L, "Article A", "Hello, I'm a article!");

        Response<ArticleDTO> responseBodyExpected = new Response<>();
        responseBodyExpected.setData(articleDTOExpected);

        Response<ArticleDTO> responseBodyReturned = response.getBody();

        assertEquals(responseBodyExpected, responseBodyReturned);
    }

    @Test
    public void createArticle_shouldReturnCreatedStatusCode() {

        ArticleCreateDTO articleCreateDTO = new ArticleCreateDTO("Article A", "Hello, I'm a article!");
        ArticleDTO articleDTO = new ArticleDTO(1L, "Article A", "Hello, I'm a article!");

        when(articleService.create(eq(articleCreateDTO))).thenReturn(articleDTO);

        ResponseEntity<Response<ArticleDTO>> response = articleController.createArticle(articleCreateDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void updateArticle_shouldReturnExpectedBody() throws DataNotFoundException {
        Long id = 1L;
        ArticleDTO articleDTO = new ArticleDTO("Article A", "Hello, I'm a article!");

        ArticleDTO articleDTOUpdated = new ArticleDTO(1L, "Article B", "Hello, I'm a article updated!");

        when(articleService.update(eq(id), eq(articleDTO))).thenReturn(articleDTOUpdated);

        ResponseEntity<Response<ArticleDTO>> response = articleController.updateArticle(id, articleDTO);

        ArticleDTO articleDTOExpected = new ArticleDTO(1L, "Article B", "Hello, I'm a article updated!");

        Response<ArticleDTO> responseBodyExpected = new Response<>();
        responseBodyExpected.setData(articleDTOExpected);

        Response<ArticleDTO> responseBodyReturned = response.getBody();

        assertEquals(responseBodyExpected, responseBodyReturned);
    }

    @Test
    public void updateArticle_shouldReturnOkStatusCode() throws DataNotFoundException {
        Long id = 1L;
        ArticleDTO articleDTO = new ArticleDTO("Article A", "Hello, I'm a article!");

        ArticleDTO articleDTOUpdated = new ArticleDTO(1L, "Article B", "Hello, I'm a article updated!");

        when(articleService.update(eq(id), eq(articleDTO))).thenReturn(articleDTOUpdated);

        ResponseEntity<Response<ArticleDTO>> response = articleController.updateArticle(id, articleDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void updateArticle_shouldReturnExpectedErrorBody() throws DataNotFoundException {
        Long id = 1L;
        ArticleDTO articleDTO = new ArticleDTO("Article A", "Hello, I'm a article!");

        when(articleService.update(eq(id), eq(articleDTO)))
                .thenThrow(new DataNotFoundException("Article with id 1 not found!"));

        ResponseEntity<Response<ArticleDTO>> response = articleController.updateArticle(id, articleDTO);

        Response<ArticleDTO> responseBodyExpected = new Response<>();
        Error error = new Error("404", "Article with id 1 not found!");
        responseBodyExpected.setError(error);

        Response<ArticleDTO> responseBodyReturned = response.getBody();

        assertEquals(responseBodyExpected, responseBodyReturned);
    }

    @Test
    public void updateArticle_shouldReturnNotFoundStatusCode() throws DataNotFoundException {
        Long id = 1L;
        ArticleDTO articleDTO = new ArticleDTO("Article A", "Hello, I'm a article!");

        when(articleService.update(eq(id), eq(articleDTO))).thenThrow(DataNotFoundException.class);

        ResponseEntity<Response<ArticleDTO>> response = articleController.updateArticle(id, articleDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void findArticleById_shouldReturnExpectResponseBody() throws DataNotFoundException {
        Long id = 1L;

        ArticleDTO articleDTO = new ArticleDTO("Article A", "Hello, I'm a article!");
        when(articleService.findById(eq(id))).thenReturn(articleDTO);

        ArticleDTO articleDTOExpected = new ArticleDTO("Article A", "Hello, I'm a article!");
        Response<ArticleDTO> responseBodyExpected = new Response<>();
        responseBodyExpected.setData(articleDTOExpected);

        ResponseEntity<Response<ArticleDTO>> response = articleController.findArticleById(id);
        Response<ArticleDTO> responseBodyReturned = response.getBody();

        assertEquals(responseBodyExpected, responseBodyReturned);
    }

    @Test
    public void findArticleById_shouldReturnOKStatusCode() throws DataNotFoundException {
        Long id = 1L;

        ArticleDTO articleDTO = new ArticleDTO("Article A", "Hello, I'm a article!");
        when(articleService.findById(eq(id))).thenReturn(articleDTO);

        ResponseEntity<Response<ArticleDTO>> response = articleController.findArticleById(id);

        HttpStatus statusCodeExpected = HttpStatus.OK;
        HttpStatus statusCodeReturned = response.getStatusCode();

        assertEquals(statusCodeExpected, statusCodeReturned);
    }

    @Test
    public void findArticleById_shouldReturnExpectResponseErrorBody() throws DataNotFoundException {
        Long id = 1L;
        when(articleService.findById(eq(id))).thenThrow(new DataNotFoundException("Article with id 1 not found!"));

        ResponseEntity<Response<ArticleDTO>> response = articleController.findArticleById(id);

        Response<ArticleDTO> responseBodyExpected = new Response<>();
        Error error = new Error("404", "Article with id 1 not found!");
        responseBodyExpected.setError(error);

        Response<ArticleDTO> responseBodyReturned = response.getBody();

        assertEquals(responseBodyExpected, responseBodyReturned);
    }

    @Test
    public void findArticleById_shouldReturnNotFoundStatusCode() throws DataNotFoundException {
        Long id = 1L;
        when(articleService.findById(eq(id))).thenThrow(new DataNotFoundException("Article with id 1 not found!"));

        ResponseEntity<Response<ArticleDTO>> response = articleController.findArticleById(id);

        HttpStatus statusCodeExpected = HttpStatus.NOT_FOUND;
        HttpStatus statusCodeReturned = response.getStatusCode();

        assertEquals(statusCodeExpected, statusCodeReturned);
    }

    @Test
    public void deleteArticle_shouldReturnOkStatusCode() throws DataNotFoundException {
        Long id = 1L;
        doNothing().when(articleService).delete(eq(id));

        ResponseEntity<Response<Void>> response = articleController.deleteArticle(id);

        HttpStatus statusCodeExpected = HttpStatus.OK;
        HttpStatus statusCodeReturned = response.getStatusCode();

        assertEquals(statusCodeExpected, statusCodeReturned);
    }

    @Test
    public void deleteArticle_shouldReturnNotFoundStatusCode() throws DataNotFoundException {
        Long id = 1L;
        doThrow(new DataNotFoundException("Article with id 1 not found!")).when(articleService).delete(eq(id));

        ResponseEntity<Response<Void>> response = articleController.deleteArticle(id);

        HttpStatus statusCodeExpected = HttpStatus.NOT_FOUND;
        HttpStatus statusCodeReturned = response.getStatusCode();

        assertEquals(statusCodeExpected, statusCodeReturned);
    }
}
