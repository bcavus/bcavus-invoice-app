package com.bcavus.invoiceapp.userservice.exception;

public class NoSuchUserFound extends RuntimeException{
    public NoSuchUserFound(){super();}
    public NoSuchUserFound(String message) {super(message);}
}