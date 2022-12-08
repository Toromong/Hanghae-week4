package com.example.homework.dto;

import lombok.Getter;
@Getter
public class ArticleDeleteResponseDto {
    private boolean success;

    public ArticleDeleteResponseDto (boolean result) {
        this.success = result;
    }
}

