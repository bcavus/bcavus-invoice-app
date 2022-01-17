package com.bcavus.invoiceapp.invoiceservice.service.messaging.consumer;

public interface InvoiceMessageConsumerService<InvoiceExpenseValidationMessage> {

    void receiveMessage(InvoiceExpenseValidationMessage invoiceExpenseValidationMessage);
}