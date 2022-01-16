package com.bcavus.invoiceapp.invoiceservice.service.messaging;

import com.bcavus.invoiceapp.invoiceservice.service.messaging.message.RabbitMessage;

public interface RabbitMQMessageSender<T extends RabbitMessage> {
    boolean sendMessage(T message) throws IllegalArgumentException;
}