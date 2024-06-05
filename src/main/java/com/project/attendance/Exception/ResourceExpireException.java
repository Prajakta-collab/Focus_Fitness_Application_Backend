package com.project.attendance.Exception;

public class ResourceExpireException extends RuntimeException{
    String errorMessage ;

    public ResourceExpireException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
