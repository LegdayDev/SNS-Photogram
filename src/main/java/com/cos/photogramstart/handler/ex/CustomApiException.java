package com.cos.photogramstart.handler.ex;

import java.util.HashMap;
import java.util.Map;

public class CustomApiException extends RuntimeException{
    // 객체를 구분할 때 !!(JVM 이 구분한다고 한다) 중요하진않음.
    private static final long serialVersionUID = 1L;

    public CustomApiException(String message) {
        super(message);
    }
}
