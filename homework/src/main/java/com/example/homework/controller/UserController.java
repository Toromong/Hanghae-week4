package com.example.homework.controller;

import com.example.homework.dto.LoginRequestDto;
import com.example.homework.dto.ResponseDto;
import com.example.homework.dto.SignupRequestDto;
import com.example.homework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        System.out.println(signupRequestDto.getUsername());
        return new ResponseDto("회원가입성공!", HttpStatus.OK.value());
    }
    @ResponseBody
    @PostMapping("/login")
    public ResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        userService.login(loginRequestDto);
        return new ResponseDto("로그인성공!", HttpStatus.OK.value());
    }
    @GetMapping("/test")
    public ResponseDto login2(HttpServletRequest request) {
        request.getHeader("Authorization");
        return new ResponseDto("로그인성공!", HttpStatus.OK.value());
    }

}


