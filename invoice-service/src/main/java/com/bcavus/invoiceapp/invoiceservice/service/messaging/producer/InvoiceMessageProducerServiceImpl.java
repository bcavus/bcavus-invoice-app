package com.bcavus.invoiceapp.invoiceservice.service.messaging.producer;

import com.bcavus.invoiceapp.invoiceservice.service.messaging.AbstractRabbitMQConnection;
import com.bcavus.invoiceapp.invoiceservice.service.messaging.message.InvoiceUserValidationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class InvoiceMessageProducerServiceImpl extends AbstractRabbitMQConnection implements InvoiceMessageProducerService<InvoiceUserValidationMessage> {

    private final Logger logger = LoggerFactory.getLogger(InvoiceMessageProducerServiceImpl.class);

    public InvoiceMessageProducerServiceImpl() {
        super();
    }

    @Override
    public boolean sendMessage(InvoiceUserValidationMessage userValidationMessage) {
        logger.info("[InvoiceMessageProducerService/sendMessage]: Sending Message = " + userValidationMessage);

        return super.sendMessage(userValidationMessage);
    }
}