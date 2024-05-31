package com.project.attendance.Exception;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternalServerException extends RuntimeException {

    Boolean success ;
    String message;
    String className ;

    public InternalServerException(String message){
        super(message);
        this.message = message ;
    }
}
