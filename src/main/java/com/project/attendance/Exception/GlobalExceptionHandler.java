package com.project.attendance.Exception;

import com.project.attendance.Payload.ApiResponse;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> ResourceNotFoundExceptionHandler(ResourceNotFoundException e){
        String message = e.getMessage() ;
        ApiResponse apiResponse = new ApiResponse(message , false) ;
        return ResponseEntity.ok(apiResponse) ;
    }

    @ExceptionHandler(SubscriptionExpireException.class)
    public ResponseEntity<ApiResponse> SubscriptionExpireExceptionHandler(SubscriptionExpireException e){
        String message = e.getMessage() ;
        ApiResponse apiResponse = new ApiResponse(message , false) ;
        return ResponseEntity.ok(apiResponse) ;
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ApiResponse> DuplicateKeyExceptionHandler(DuplicateKeyException e){
        String message = e.getMessage() ;
        ApiResponse apiResponse = new ApiResponse(message , false) ;
        return ResponseEntity.ok(apiResponse) ;
    }

}
