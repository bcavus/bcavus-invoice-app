package com.bcavus.invoiceapp.userservice.service.messaging;

import com.bcavus.invoiceapp.userservice.service.messaging.message.RabbitMessage;

public interface RabbitMQMessageSender<T extends RabbitMessage> {
    boolean sendMessage(T message) throws IllegalArgumentException;
}