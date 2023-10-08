package com.cos.photogramstart.web.api;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.service.LikesService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.image.ImageUpdateDto;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ImageApiController {
    private final ImageService imageService;
    private final LikesService likesService;

    @GetMapping("/api/image")
    public ResponseEntity<?> imageStory(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                        @PageableDefault(size = 3) Pageable pageable){
        Page<Image> images = imageService.이미지스토리(principalDetails.getUser().getId(), pageable);

        return new ResponseEntity<>(new CMRespDto<>(1,"이미지 스토리 불러오기 성공",images), HttpStatus.OK);
    }

    @PostMapping("/api/image/{imageId}/likes")
    public ResponseEntity<?> likes(@PathVariable int imageId,
                                   @AuthenticationPrincipal PrincipalDetails principalDetails){
        likesService.좋아요(imageId,principalDetails.getUser().getId());
        return new ResponseEntity<>(new CMRespDto<>(1,"좋아요성공",null),HttpStatus.CREATED);
    }

    @DeleteMapping("/api/image/{imageId}/likes")
    public ResponseEntity<?> unLikes(@PathVariable int imageId,
                                   @AuthenticationPrincipal PrincipalDetails principalDetails){
        likesService.좋아요취소(imageId,principalDetails.getUser().getId());
        return new ResponseEntity<>(new CMRespDto<>(1,"좋아요 취소 성공",null),HttpStatus.OK);
    }

    @PutMapping("/api/image/{imageId}")
    public ResponseEntity<?> imageUpdate(@PathVariable int imageId, MultipartFile userImageFile, String caption){
        ImageUpdateDto imageUpdateDto = new ImageUpdateDto();
        imageUpdateDto.setFile(userImageFile);
        imageUpdateDto.setCaption(caption);

        System.out.println("imageUpdateDto.getFile(). = " + imageUpdateDto.getFile().getName());
        System.out.println("imageUpdateDto.getCaption() = " + imageUpdateDto.getCaption());

        imageService.사진수정하기(imageId, imageUpdateDto);
        return new ResponseEntity<>(new CMRespDto<>(1,"스토리이미지 수정",null),HttpStatus.OK);
    }

    @DeleteMapping("/api/image/{imageId}")
    public ResponseEntity<?> imageDelete(@PathVariable int imageId){
        imageService.사진삭제하기(imageId);

        return new ResponseEntity<>(new CMRespDto<>(1,"스토리이미지 삭제성공",imageId),HttpStatus.OK);
    }

}
