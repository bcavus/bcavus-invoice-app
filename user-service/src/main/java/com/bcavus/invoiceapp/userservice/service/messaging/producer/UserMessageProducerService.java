package com.bcavus.invoiceapp.userservice.service.messaging.producer;

public interface UserMessageProducerService<UserExpenseCreationMessage> {
    boolean sendMessage(UserExpenseCreationMessage expenseCreationMessage);
}