package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;
    private final SubscribeRepository subscribeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${file.path}")
    private String uploadFolder;

    public UserProfileDto 회원프로필(int pageUserId, int principalId){
        UserProfileDto dto = new UserProfileDto();

        User userEntity = userRepository.findById(pageUserId).orElseThrow(()->{
            throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
        }); //null 일 경우 Optional
        dto.setUser(userEntity);
        dto.setPageOwnerState(pageUserId==principalId);
        dto.setImageCount(userEntity.getImages().size());

        dto.setSubscribeCount(subscribeRepository.mSubscribeCount(pageUserId));
        dto.setSubscribeState(subscribeRepository.mSubscribeState(principalId, pageUserId) ==1);

        // 좋아요 카운트 추가
        userEntity.getImages().forEach((image -> {
            image.setLikeCount(image.getLikes().size());
        }));

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

    @Transactional
    public User 회원프로필사진변경(int principalId, MultipartFile profileImageFile) {

        UUID uuid = UUID.randomUUID();

        // getOriginalFilename()은 실제 파일명을 리턴해준다.
        String imageFileName = uuid + "_" + profileImageFile.getOriginalFilename();

        // 실제 경로를 저장 Path(nio 패키지) : 파일이 저장되는 경로 + 파일 이름 = 실제 저장되는 경로
        Path imageFilePath = Paths.get(uploadFolder + imageFileName);

        // 통신 , I/O 가
        try{
            //write() 메서드는 실제 파일 경로와 실제 들어오는 파일을 Byte 로 바꾼것 , option 을 받는다.
            Files.write( imageFilePath,profileImageFile.getBytes());
        } catch (Exception e){
            e.printStackTrace();
        }

        User userEntity = userRepository.findById(principalId).orElseThrow(() -> {
            throw new CustomApiException("유저를 찾을 수 없습니다.");
        });
        userEntity.setProfileImageUrl(imageFileName);

        return userEntity;
    }
}
