package com.bcavus.invoiceapp.invoiceservice.service.messaging.consumer;

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
            logger.warn("Cannot update invoice, validation message has no invoice id: " + message);
        }

        this.invoiceService.updateInvoiceStatusById(message.getInvoiceId(), message.isAvailable());

        logger.info("Updated invoice: " + message);
    }
}