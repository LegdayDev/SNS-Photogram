package com.cos.photogramstart.web;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/user/{pageUserId}")
    public String profile(@PathVariable int pageUserId,
                          Model model,
                          @AuthenticationPrincipal PrincipalDetails principalDetails){
        model.addAttribute("dto",userService.회원프로필(pageUserId,principalDetails.getUser().getId()));
        return "user/profile";
    }

    @GetMapping("/user/{id}/update")
    public String update(@PathVariable int id,
                         @AuthenticationPrincipal PrincipalDetails principalDetails){
        /** Security Session 찾기
         * 1. @AuthenticationPrincipal 어노테이션을 이용(권장)
             System.out.println("세션 정보 : " + principalDetails.getUser());
         * 2. 직접 역추적해서 찾는 방법(비추)
             Authentication auth = SecurityContextHolder.getContext().getAuthentication();
             PrincipalDetails mPrincipalDetails = (PrincipalDetails)auth.getPrincipal();
             System.out.println("직접 찾은 세션 정보 : " + mPrincipalDetails.getUser());
         */

        return "user/update";
    }

}
