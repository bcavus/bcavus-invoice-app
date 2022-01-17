package com.bcavus.invoiceapp.userservice.service.messaging.producer;

import com.bcavus.invoiceapp.userservice.service.messaging.RabbitMQMessageSender;
import com.bcavus.invoiceapp.userservice.service.messaging.message.RabbitMessage;

public interface UserExpenseCreationProducerService<UserExpenseCreationMessage> extends RabbitMQMessageSender<RabbitMessage> {
    boolean sendMessage(UserExpenseCreationMessage expenseCreationMessage);
}