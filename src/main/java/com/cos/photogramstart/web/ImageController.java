package com.cos.photogramstart.web;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ImageController {

    private final ImageService imageService;

    @GetMapping({"/","image/story"})
    public String story(){
        return "image/story";
    }

    @GetMapping("/image/detail/{imageId}")
    public String storyDetail(@PathVariable int imageId, Model model){

        model.addAttribute("image",imageService.사진한건(imageId));
        return "image/storyDetail";
    }

    @GetMapping("/image/popular")
    public String popular(Model model){
        model.addAttribute("images",imageService.인기사진());
        return "image/popular";
    }

    @GetMapping("/image/upload")
    public String upload (){
        return "image/upload";
    }

    @PostMapping("/image")
    public String imageUpload(@ModelAttribute ImageUploadDto imageUploadDto,
                              @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if(imageUploadDto.getFile().isEmpty()){
            throw new CustomValidationException("이미지가 첨부되지 않았습니다", null);
        }
        imageService.사진업로드(imageUploadDto,principalDetails);

        return "redirect:/user/"+principalDetails.getUser().getId();
    }
}
