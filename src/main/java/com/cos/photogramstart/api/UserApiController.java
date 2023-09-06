package com.cos.photogramstart.api;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;


    @PutMapping("/api/user/{userId}")
    public CMRespDto<?> update(@PathVariable int userId,
                               @Valid UserUpdateDto userUpdateDto,
                               BindingResult bindingResult,// 반드시 @Valid 가 붙은 파라미터 뒤에 와야한다.
                               @AuthenticationPrincipal PrincipalDetails principalDetails){

        if(bindingResult.hasErrors()){ // Validation 후 error 가 있다면
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(),error.getDefaultMessage());
                log.error(error.getDefaultMessage());
            }
            throw new CustomValidationApiException("유효성 검사 실패함",errorMap);
        }else{
            User userEntity = userService.회원수정(userId, userUpdateDto.toEntity());

            principalDetails.setUser(userEntity); // 세션 정보 업데이트

            return new CMRespDto<>(1,"회원수정완료",userEntity);
        }
    }
}
