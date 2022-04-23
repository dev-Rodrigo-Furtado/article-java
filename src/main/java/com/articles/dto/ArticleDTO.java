package com.articles.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {

    private Long id;
    private String title;
    private String content;

    public ArticleDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
