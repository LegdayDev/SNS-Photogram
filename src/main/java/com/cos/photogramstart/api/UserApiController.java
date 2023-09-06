package com.cos.photogramstart.api;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;


    @PutMapping("/api/user/{userId}")
    public CMRespDto<?> update(@PathVariable int userId,
                               UserUpdateDto userUpdateDto,
                               @AuthenticationPrincipal PrincipalDetails principalDetails){

        User userEntity = userService.회원수정(userId, userUpdateDto.toEntity());

        principalDetails.setUser(userEntity); // 세션 정보 업데이트

        return new CMRespDto<>(1,"회원수정완료",userEntity);
    }
}
