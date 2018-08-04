package com.ford.statistics.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class CustomValidationException extends Exception {
    private List<String> errorDescription;
    public CustomValidationException(List<String> errorDescription){
        super(errorDescription.toString());
        this.errorDescription = errorDescription;
    }
}
