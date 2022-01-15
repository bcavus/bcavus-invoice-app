package com.bcavus.invoiceapp.userservice.service.messaging;

import com.bcavus.invoiceapp.userservice.dto.message.UserMessage;

public interface UserMessageService {
    boolean sendMessage(UserMessage message);
}