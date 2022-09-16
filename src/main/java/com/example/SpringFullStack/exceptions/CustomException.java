package com.example.SpringFullStack.exceptions;

public class CustomException extends IllegalArgumentException{
    public CustomException (String msg){
        super(msg);
    }

}
