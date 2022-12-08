package com.example.homework.controller;

import com.example.homework.dto.ArticleDeleteRequestDto;
import com.example.homework.dto.ArticleRequestDto;
import com.example.homework.dto.ArticleResponseDto;
import com.example.homework.dto.ResponseDto;
import com.example.homework.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/save/article")
    public ResponseDto saveArticle(@RequestBody ArticleRequestDto requestDto, HttpServletRequest request){
        return articleService.saveArticle(requestDto,request);
    }
    @GetMapping("/get/articles")
    public ResponseDto getArticles(){
        return articleService.getArticles();
    }

    @GetMapping("/get/article")
    public ArticleResponseDto getArticle(@RequestParam Long id){
        return articleService.getArticle(id);
    }

    @PutMapping("/update/article/{id}")
    public ArticleResponseDto updateArticle(@PathVariable Long id, @RequestBody ArticleResponseDto requestDto, HttpServletRequest request){
        return articleService.updateArticle(id,requestDto, request);
    }
    @DeleteMapping("/delete/article/{id}")
    public boolean deleteArticle(@PathVariable Long id, @RequestBody ArticleDeleteRequestDto requestDto , HttpServletRequest request){
        return articleService.deleteArticle(id , requestDto , request);
    }

}
