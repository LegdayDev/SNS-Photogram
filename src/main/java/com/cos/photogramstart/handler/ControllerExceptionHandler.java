package com.cos.photogramstart.handler;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@ControllerAdvice //Exception 이 발생하면 어노테이션이 붙은 클래스가 가로챈다.
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class) //CustomValidationException 이 발생하는 모든 오류를 이 메서드가 가로챈다.
    public Map<String,String> validationException(CustomValidationException e){
        return e.getErrorMap();
    }
}
