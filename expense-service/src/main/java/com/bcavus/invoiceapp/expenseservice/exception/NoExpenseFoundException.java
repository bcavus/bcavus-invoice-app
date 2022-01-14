package com.bcavus.invoiceapp.expenseservice.exception;

public class NoExpenseFoundException extends RuntimeException{
    public NoExpenseFoundException() {super();}
    public NoExpenseFoundException(String message) {super(message);}
}