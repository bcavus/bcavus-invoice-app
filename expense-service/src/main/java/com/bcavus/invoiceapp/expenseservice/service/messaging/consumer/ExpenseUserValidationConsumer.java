package com.bcavus.invoiceapp.expenseservice.service.messaging.consumer;

public interface ExpenseUserValidationConsumer<UserExpenseValidationMessage> {
    void receiveMessage(UserExpenseValidationMessage userExpenseCreationMessage);
}