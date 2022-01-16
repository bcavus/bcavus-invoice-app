package com.bcavus.invoiceapp.userservice.service.messaging.consumer;

public interface UserValidationConsumerService<InvoiceUserValidationMessage> {
    void receiveMessage(InvoiceUserValidationMessage invoiceUserValidationMessage);
}