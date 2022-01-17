package com.bcavus.invoiceapp.expenseservice.service.messaging.consumer;

public interface ExpenseUserCreationConsumer<UserExpenseCreationMessage> {
    void receiveMessage(UserExpenseCreationMessage userExpenseCreationMessage);
}