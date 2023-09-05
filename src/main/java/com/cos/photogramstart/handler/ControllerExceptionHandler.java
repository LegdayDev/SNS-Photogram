package com.cos.photogramstart.handler;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@ControllerAdvice //Exception 이 발생하면 어노테이션이 붙은 클래스가 가로챈다.
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class) //CustomValidationException 이 발생하는 모든 오류를 이 메서드가 가로챈다.
    public String  validationException(CustomValidationException e){
        // CMRespDto, Script 비교
        // 1. 클라이언트에게 응답할 때는 Script 좋음.
        // 2. Ajax 통신을 할 때에는 CMRespDto 가 좋다.
        // 3. Android 통신 - CMRespDto 가 좋다.
        return Script.back(e.getErrorMap().toString());
        //return e.getErrorMap();
    }
}
