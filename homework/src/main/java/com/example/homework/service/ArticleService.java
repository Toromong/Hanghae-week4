package com.example.homework.service;

import com.example.homework.config.ServiceConfig;
import com.example.homework.dto.ArticleDeleteRequestDto;
import com.example.homework.dto.ArticleRequestDto;
import com.example.homework.dto.ArticleResponseDto;
import com.example.homework.dto.ResponseDto;
import com.example.homework.entity.Article;
import com.example.homework.entity.User;
import com.example.homework.jwt.JwtUtil;
import com.example.homework.repository.ArticleRepository;
import com.example.homework.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final ServiceConfig serviceConfig;

    @Transactional
    public ResponseDto saveArticle(ArticleRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        System.out.println("token = " + token);
        Claims claims;
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Article article = articleRepository.saveAndFlush(new Article(requestDto , user.getUsername()));
            articleRepository.save(article);
            return new ResponseDto("글 등록 완료", HttpStatus.OK.value());
        }
        else {
            return new ResponseDto("로그인을하지 않은 사용자입니다.", HttpStatus.OK.value());
        }
    }
    @Transactional(readOnly = true)
    public ResponseDto getArticles() {
        ArticleResponseDto articleResponseDto = new ArticleResponseDto();

        List<ArticleResponseDto> articleListResponseDtos = new ArrayList<>();

        List<Article> articles = articleRepository.findAll();

        for(Article article :articles){
            articleResponseDto.setArticleResponseDto(article);
            articleListResponseDtos.add(articleResponseDto);
        }
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMsg("성공");
        responseDto.setStatusCode(200);
        responseDto.setData(articleListResponseDtos);

        return responseDto;
    }
    @Transactional(readOnly = true)
    public ArticleResponseDto getArticle(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("글이없다")
        );
        return new ArticleResponseDto(article);
    }
    @Transactional
    public ArticleResponseDto updateArticle(Long id, ArticleResponseDto requestDto, HttpServletRequest request) {
        User userFromToken = serviceConfig.findUserFromToken(request);

        Article article = articleRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("글이 없다")
        );

        if (article.getUsername().equals(userFromToken.getUsername())){
            article.update(requestDto);
        }else {
            throw new IllegalArgumentException("자신의 글만 수정할 수 있습니다.");
        }

        return new ArticleResponseDto(article);
    }

    @Transactional
    public boolean deleteArticle(Long id , ArticleDeleteRequestDto requestDto , HttpServletRequest request){
        User userFromToken = serviceConfig.findUserFromToken(request);

        Article article = articleRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("글이 없다")
        );
        if (article.getUsername().equals(userFromToken.getUsername())){
            articleRepository.delete(article);
            return true;
        }else {
            throw new IllegalArgumentException("자신의 글만 삭제할 수 있습니다.");
        }
    }
}


