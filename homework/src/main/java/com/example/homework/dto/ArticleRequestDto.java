package com.example.homework.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ArticleRequestDto {
    private String title;
    private String name;
    private String content;
    private String password;

}
