package com.PromotionEngineService.promotionService.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(value=ResourceNotFoundException.class)
    ResponseEntity<Object> resourceNotfound(ResourceNotFoundException resourceNotFoundException){
        return new ResponseEntity<>(resourceNotFoundException, HttpStatus.NOT_FOUND);
    }
}
