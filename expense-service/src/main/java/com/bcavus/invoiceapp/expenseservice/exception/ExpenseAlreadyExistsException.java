package com.bcavus.invoiceapp.expenseservice.exception;

public class ExpenseAlreadyExistsException extends RuntimeException{
    public ExpenseAlreadyExistsException() {super();}
    public ExpenseAlreadyExistsException(String message) {super(message);}
}