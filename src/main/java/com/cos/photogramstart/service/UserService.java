package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public User 회원수정(int userId, User user){
        // 1. 영속화
        User userEntity = userRepository.findById(userId).get();

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
