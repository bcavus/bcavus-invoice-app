package com.bcavus.invoiceapp.invoiceservice.service.messaging.consumer;

import com.bcavus.invoiceapp.invoiceservice.model.InvoiceStatus;
import com.bcavus.invoiceapp.invoiceservice.service.InvoiceService;
import com.bcavus.invoiceapp.invoiceservice.service.messaging.message.InvoiceExpenseValidationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceMessageConsumerServiceImpl implements InvoiceMessageConsumerService<InvoiceExpenseValidationMessage> {

    private final Logger logger = LoggerFactory.getLogger(InvoiceMessageConsumerServiceImpl.class);

    @Autowired
    private final InvoiceService invoiceService;

    public InvoiceMessageConsumerServiceImpl(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Override
    @RabbitListener(queues = "invoice-validation-queue")
    public void receiveMessage(InvoiceExpenseValidationMessage message) {

        if(message.getInvoiceId() == null) {
            logger.warn("[InvoiceMessageConsumerService/receiveMessage]: Cannot update invoice, validation message has no invoice id: " + message);
        }

        try{
            InvoiceStatus status = message.isAvailable() ? InvoiceStatus.ACCEPTED : InvoiceStatus.REJECTED;

            this.invoiceService.updateInvoiceStatusById(message.getInvoiceId(), status);

            logger.warn("[InvoiceMessageConsumerService/receiveMessage]: Successfully updated invoice with given message: " + message);
        }catch (Exception ex) {
            logger.warn("[InvoiceMessageConsumerService/receiveMessage]: Failed to update invoice " + ex.getMessage());
        }
    }
}