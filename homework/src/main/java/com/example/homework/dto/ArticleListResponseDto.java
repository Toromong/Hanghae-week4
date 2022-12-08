package com.example.homework.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ArticleListResponseDto extends ResponseDto {
    List<ArticleResponseDto> articleList = new ArrayList<>();

    public ArticleListResponseDto(){
        super("조회 성공", HttpStatus.OK.value());

    }
    public void addArticle(ArticleResponseDto responseDto){
        articleList.add(responseDto);
    }
}
