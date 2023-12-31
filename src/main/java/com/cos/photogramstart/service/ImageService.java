package com.cos.photogramstart.service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.dto.image.ImageUpdateDto;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Value("${file.path}")
    private String uploadFolder;

    @Transactional
    public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {

        UUID uuid = UUID.randomUUID();

        // getOriginalFilename()은 실제 파일명을 리턴해준다.
        String imageFileName = uuid + "_" + imageUploadDto.getFile().getOriginalFilename();

        // 실제 경로를 저장 Path(nio 패키지) : 파일이 저장되는 경로 + 파일 이름 = 실제 저장되는 경로
        Path imageFilePath = Paths.get(uploadFolder + imageFileName);

        // 통신 , I/O 가
        try {
            //write() 메서드는 실제 파일 경로와 실제 들어오는 파일을 Byte 로 바꾼것 , option 을 받는다.
            Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // image 테이블에 저장
        imageRepository.save(imageUploadDto.toEntity(imageFileName, principalDetails.getUser()));
//        System.out.println(imageEntity);
    }

    public Page<Image> 이미지스토리(int principalId, Pageable pageable) {
        Page<Image> images = imageRepository.mStory(principalId, pageable);

        // 좋아요 상태 담기
        images.forEach((image) -> {
            image.setLikeCount(image.getLikes().size());
            image.getLikes().forEach((like) -> {
                if (like.getUser().getId() == principalId) {
                    image.setLikeState(true);
                }
            });
        });
        return images;
    }

    public List<Image> 인기사진() {
        return imageRepository.mPopular();
    }

    public Image 사진한건(int imageId) {
        return imageRepository.findById(imageId).orElseThrow(() -> {
            return new CustomApiException("찾을 수 없는 이미지 입니다.");
        });
    }

    @Transactional
    public void 사진수정하기(int imageId, ImageUpdateDto imageUpdateDto) {
        Image imageEntity = imageRepository.findById(imageId).orElseThrow(() -> {
            return new CustomApiException("찾을 수 없는 Id 입니다.");
        });

        imageEntity.setCaption(imageUpdateDto.getCaption());

        if(!imageUpdateDto.getFile().isEmpty()){
            UUID uuid = UUID.randomUUID();
            // getOriginalFilename()은 실제 파일명을 리턴해준다.
            String imageFileName = uuid + "_" + imageUpdateDto.getFile().getOriginalFilename();

            // 실제 경로를 저장 Path(nio 패키지) : 파일이 저장되는 경로 + 파일 이름 = 실제 저장되는 경로
            Path imageFilePath = Paths.get(uploadFolder + imageFileName);
            // 통신 , I/O 가
            try {
                //write() 메서드는 실제 파일 경로와 실제 들어오는 파일을 Byte 로 바꾼것 , option 을 받는다.
                Files.write(imageFilePath, imageUpdateDto.getFile().getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }

            imageEntity.setPostImageUrl(imageFileName);
        }

    }

    @Transactional
    public void 사진삭제하기(int imageId) {
        Image imageEntity = imageRepository.findById(imageId).orElseThrow(() -> {
            return new CustomApiException("찾을 수 없는 Id 입니다.");
        });
        imageRepository.delete(imageEntity);
    }
}
