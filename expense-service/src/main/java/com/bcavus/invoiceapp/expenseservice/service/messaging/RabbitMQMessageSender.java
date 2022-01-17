package com.bcavus.invoiceapp.expenseservice.service.messaging;

import com.bcavus.invoiceapp.expenseservice.service.messaging.message.RabbitMessage;

public interface RabbitMQMessageSender<T extends RabbitMessage> {
    boolean sendMessage(T message) throws IllegalArgumentException;
}