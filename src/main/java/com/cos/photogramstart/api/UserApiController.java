package com.cos.photogramstart.api;

import com.cos.photogramstart.web.dto.user.UserUpdateDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {

    @PutMapping("/api/user/{userId}")
    public String update(UserUpdateDto userUpdateDto){
        System.out.println(userUpdateDto);

        return "ok";
    }
}
