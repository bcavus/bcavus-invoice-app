package com.bcavus.invoiceapp.expenseservice.exception;

public class NotEnoughExpenseBudgetException extends RuntimeException{
    public NotEnoughExpenseBudgetException(){super();}
    public NotEnoughExpenseBudgetException(String message) {super(message);}
}