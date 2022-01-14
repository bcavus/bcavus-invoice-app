package com.bcavus.invoiceapp.expenseservice.exception;

public class NoExpenseFound extends RuntimeException{
    public NoExpenseFound() {super();}
    public NoExpenseFound(String message) {super(message);}
}