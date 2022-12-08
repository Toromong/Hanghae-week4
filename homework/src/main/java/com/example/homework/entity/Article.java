package com.example.homework.entity;


import com.example.homework.dto.ArticleRequestDto;
import com.example.homework.dto.ArticleResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Article extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String name;
    private String content;
    private String password;

    private String username;

    public Article(ArticleRequestDto requestDto , String username){
        this.title = requestDto.getTitle();
        this.name = requestDto.getName();
        this.content = requestDto.getContent();
        this.password = requestDto.getPassword();
        this.username = username;
    }
    public void update(ArticleResponseDto responseDto){
        this.title = responseDto.getTitle();
        this.name = responseDto.getName();
        this.content = responseDto.getContent();

    }
}
