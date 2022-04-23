package com.articles.service;

import com.articles.dto.ArticleCreateDTO;
import com.articles.dto.ArticleDTO;
import com.articles.exception.DataNotFoundException;

public interface ArticleServiceInterface {

    ArticleDTO create(ArticleCreateDTO articleDTO);

    ArticleDTO update(Long id, ArticleDTO articleDTO) throws DataNotFoundException;

    ArticleDTO findById(Long id) throws DataNotFoundException;

    void delete(Long id) throws DataNotFoundException;
}
