package com.bcavus.invoiceapp.expenseservice.service.messaging.producer;

public interface ExpenseMessageProducerService<InvoiceExpenseValidationMessage> {
    boolean sendMessage(InvoiceExpenseValidationMessage userValidationMessage);
}