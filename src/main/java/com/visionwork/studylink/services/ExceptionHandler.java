package com.visionwork.studylink.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handlerArg(IllegalArgumentException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
