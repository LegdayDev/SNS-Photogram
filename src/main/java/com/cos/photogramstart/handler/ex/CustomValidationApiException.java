package com.cos.photogramstart.handler.ex;

import java.util.HashMap;
import java.util.Map;

public class CustomValidationApiException extends RuntimeException{

    // 객체를 구분할 때 !!(JVM 이 구분한다고 한다) 중요하진않음.
    private static final long serialVersionUID = 1L;

    private Map<String,String> errorMap = new HashMap<>();

    public CustomValidationApiException(String message, Map<String, String> errorMap) {
        super(message);
        this.errorMap = errorMap;
    }

    public Map<String, String> getErrorMap() {
        return errorMap;
    }
}
