package com.cos.photogramstart.web;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AuthController { // 인증을 위한 컨트롤러

    private final AuthService authService;

    @GetMapping("/auth/signin")
    public String signInForm(){
        return "auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signUpForm(){
        return "auth/signup";
    }

    @PostMapping("/auth/signup")
    public String signUp(@ModelAttribute SignupDto signupDto){
        User user = signupDto.toEntity();

        User userEntity = authService.회원가입(user);
        System.out.println(userEntity);
        return "auth/signin"; //로그인 페이지로 이동
    }

}
