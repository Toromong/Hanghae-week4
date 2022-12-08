package com.example.homework.service;

import com.example.homework.dto.LoginRequestDto;
import com.example.homework.dto.SignupRequestDto;
import com.example.homework.entity.User;
import com.example.homework.entity.UserRoleEnum;
import com.example.homework.jwt.JwtUtil;
import com.example.homework.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    // ADMIN_TOKEN
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();
        // JPA 자바 스프링에서 사용하는 ORM 기술의 표준
        // ORM은 userRepository.findByUsername() 이런식으로 메서드 형태로 써주면 -> SQL문(query)를 데이터베이스에 전송해 데이터를 CRUD 해주는 일종의 번역기 같은 존재입니다.
        // 회원 중복 확인

        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }


        String email = signupRequestDto.getEmail();
        System.out.println("email = " + email);
        System.out.println("userbane = " + username);
        System.out.println("password = " + password);
        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }
        System.out.println("role = " + role);
        User user = new User(username, password, email, role);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public void login(LoginRequestDto loginRequestDto) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        // 비밀번호 확인
        if(!(user.getPassword() == password)){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        HttpServletResponse response = null;
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
    }
}
