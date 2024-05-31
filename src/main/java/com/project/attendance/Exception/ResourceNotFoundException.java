package com.project.attendance.Exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResourceNotFoundException extends RuntimeException{
    String resourceName ;
    String fieldName ;
    Integer fieldValue ;

    public ResourceNotFoundException(String resourceName, String fieldName, Integer fieldValue) {
        super(resourceName + " with " + fieldName + " as " + fieldValue + " is not found");
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

}

