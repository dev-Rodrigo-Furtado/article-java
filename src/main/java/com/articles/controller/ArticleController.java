package com.articles.controller;

import com.articles.dto.ArticleCreateDTO;
import com.articles.dto.ArticleDTO;
import com.articles.exception.DataNotFoundException;
import com.articles.response.Error;
import com.articles.response.Response;
import com.articles.service.ArticleServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleController {

    @Autowired
    private ArticleServiceInterface articleService;

    @PostMapping("articles")
    public ResponseEntity<Response<ArticleDTO>> createArticle(@RequestBody ArticleCreateDTO articleDTO) {
        Response<ArticleDTO> response = new Response<>();
        response.setData(articleService.create(articleDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("articles/{id}")
    public ResponseEntity<Response<ArticleDTO>> updateArticle(@PathVariable Long id,
            @RequestBody ArticleDTO articleDTO) {
        Response<ArticleDTO> response = new Response<>();
        HttpStatus status = HttpStatus.OK;
        try {
            ArticleDTO data = articleService.update(id, articleDTO);
            response.setData(data);
        } catch (DataNotFoundException e) {
            Error error = new Error("404", e.getMessage());
            response.setError(error);
            status = HttpStatus.NOT_FOUND;
        }

        return ResponseEntity.status(status).body(response);
    }

    @GetMapping("articles/{id}")
    public ResponseEntity<Response<ArticleDTO>> findArticleById(@PathVariable Long id) {
        Response<ArticleDTO> response = new Response<>();
        HttpStatus status = HttpStatus.OK;
        try {
            response.setData(articleService.findById(id));
        } catch (DataNotFoundException e) {
            Error error = new Error("404", e.getMessage());
            response.setError(error);
            status = HttpStatus.NOT_FOUND;
        }
        return ResponseEntity.status(status).body(response);
    }

    @DeleteMapping("articles/{id}")
    public ResponseEntity<Response<Void>> deleteArticle(@PathVariable Long id) {
        Response<Void> response = new Response<>();
        HttpStatus status = HttpStatus.OK;

        try {
            articleService.delete(id);
        } catch (DataNotFoundException e) {
            status = HttpStatus.NOT_FOUND;
        }

        return ResponseEntity.status(status).body(response);
    }
}
