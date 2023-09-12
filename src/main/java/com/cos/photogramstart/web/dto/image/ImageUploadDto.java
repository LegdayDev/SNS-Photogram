package com.cos.photogramstart.web.dto.image;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
public class ImageUploadDto {
    // multipart 타입은 @NotBlank 지원안함
    private MultipartFile file;
    private String caption;

    public Image toEntity(String postImageUrl, User user){
        return Image.builder()
                .caption(this.caption)
                .postImageUrl(postImageUrl)
                .user(user)
                .build();
    }
}
