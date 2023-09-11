package com.cos.photogramstart.service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Value("${file.path}")
    private String uploadFolder;

    @Transactional
    public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails){
        /**
         * UUID 를 사용하는 이유
         * 사용자는 서버에 이미지가 어떤이름으로 저장되는지 모르기에 같은 파일을 또 저장할 수 있다.
         * DB 에서는 구분해야 하기 때문에 UUID 식별자를 붙혀서 똑같은 이름의 파일을 구분한다.
         */
        UUID uuid = UUID.randomUUID();

        // getOriginalFilename()은 실제 파일명을 리턴해준다.
        String imageFileName = uuid + "_" + imageUploadDto.getFile().getOriginalFilename();

        // 실제 경로를 저장 Path(nio 패키지) : 파일이 저장되는 경로 + 파일 이름 = 실제 저장되는 경로
        Path imageFilePath = Paths.get(uploadFolder + imageFileName);

        // 통신 , I/O 가
        try{
            //write() 메서드는 실제 파일 경로와 실제 들어오는 파일을 Byte 로 바꾼것 , option 을 받는다.
            Files.write( imageFilePath,imageUploadDto.getFile().getBytes());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
