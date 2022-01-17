package com.bcavus.invoiceapp.invoiceservice.service.messaging.producer;


public interface InvoiceMessageProducerService<InvoiceUserValidationMessage> {
    boolean sendMessage(InvoiceUserValidationMessage userValidationMessage);
}