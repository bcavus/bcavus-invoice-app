package com.bcavus.invoiceapp.invoiceservice.exception;

public class NoInvoiceFoundException extends RuntimeException{
    public NoInvoiceFoundException(){super();}
    public NoInvoiceFoundException(String message) {super(message);}
}