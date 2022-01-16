package com.bcavus.invoiceapp.expenseservice.service.messaging;

import com.bcavus.invoiceapp.expenseservice.dto.message.RabbitMessage;

public interface ExpenseMessageService {
    boolean sendMessage(RabbitMessage message);
}