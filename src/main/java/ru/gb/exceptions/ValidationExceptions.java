package ru.gb.exceptions;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ValidationExceptions extends RuntimeException{

    private List<String> errorFieldsMessages;

    public ValidationExceptions(List<String> errorFieldsMessages){
        super(errorFieldsMessages.stream().collect(Collectors.joining(", ")));
        this.errorFieldsMessages = errorFieldsMessages;
    }
}
