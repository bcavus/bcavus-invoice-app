package com.bcavus.invoiceapp.invoiceservice.service.messaging;

import com.bcavus.invoiceapp.invoiceservice.dto.message.InvoiceMessage;

public interface InvoiceMessageService {
    boolean sendMessage(InvoiceMessage message);
}