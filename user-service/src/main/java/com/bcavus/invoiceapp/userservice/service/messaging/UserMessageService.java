package com.bcavus.invoiceapp.userservice.service.messaging;

import com.bcavus.invoiceapp.userservice.dto.message.RabbitMessage;

public interface UserMessageService {
    boolean sendMessage(RabbitMessage message);
}