package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserProfileDto 회원프로필(int pageUserId, int principalId){
        UserProfileDto dto = new UserProfileDto();

        User userEntity = userRepository.findById(pageUserId).orElseThrow(()->{
            throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
        }); //null 일 경우 Optional
        dto.setUser(userEntity);
        dto.setPageOwnerState(pageUserId==principalId);
        dto.setImageCount(userEntity.getImages().size());
        dto.setSubscribesCount(userEntity.getSubscribes().size());
        return dto;
    }


    @Transactional
    public User 회원수정(int userId, User user){
        // 1. 영속화
        //User userEntity = userRepository.findById(10).get();
        User userEntity = userRepository.findById(userId).orElseThrow(()->{
            return new CustomValidationApiException("찾을 수 없는 Id 입니다.");
        });

        /**
         * findById()
         * 스프링 데이터 JPA 가 제공하는 findById는 return 값이 Optional<T> 이다.
         * 무조건 찾는것은 .get()으로 해서 반환한다.
         * 찾지 못하는것은 Exception 을 발동시켜서 orElseThrow() 를 반환
         */

        // 2. 영속화된 오브젝트를 수정 -> 더티 체킹(업데이트 완료) : JPA 방식
        userEntity.setName(user.getName());
        userEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); // 암호화
        userEntity.setWebsite(user.getWebsite());
        userEntity.setBio(user.getBio());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());

        return userEntity;
    }
}
