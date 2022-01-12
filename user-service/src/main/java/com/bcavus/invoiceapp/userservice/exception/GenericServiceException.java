package com.bcavus.invoiceapp.userservice.exception;

public class GenericServiceException extends RuntimeException{
    public GenericServiceException(){
        super();
    }

    public GenericServiceException(String message) {
        super(message);
    }

}