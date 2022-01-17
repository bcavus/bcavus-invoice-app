package com.bcavus.invoiceapp.userservice.service.messaging.producer;

import com.bcavus.invoiceapp.userservice.service.messaging.RabbitMQMessageSender;
import com.bcavus.invoiceapp.userservice.service.messaging.message.RabbitMessage;

public interface UserExpenseValidationProducerService<UserExpenseValidationMessage> extends RabbitMQMessageSender<RabbitMessage> {
    boolean sendMessage(UserExpenseValidationMessage userExpenseValidationMessage);
}