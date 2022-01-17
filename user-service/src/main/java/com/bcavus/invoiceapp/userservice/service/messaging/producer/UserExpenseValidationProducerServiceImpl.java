package com.bcavus.invoiceapp.userservice.service.messaging.producer;

import com.bcavus.invoiceapp.userservice.service.messaging.AbstractRabbitMQConnection;
import com.bcavus.invoiceapp.userservice.service.messaging.message.UserExpenseValidationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserExpenseValidationProducerServiceImpl extends AbstractRabbitMQConnection implements UserExpenseValidationProducerService<UserExpenseValidationMessage> {
    private final Logger logger = LoggerFactory.getLogger(UserExpenseValidationProducerServiceImpl.class);

    public UserExpenseValidationProducerServiceImpl() {
        super();
    }

    @Override
    public boolean sendMessage(UserExpenseValidationMessage userExpenseValidationMessage) {
        logger.info("[UserExpenseValidationProducerService/sendMessage]: Sending Message = " + userExpenseValidationMessage);

        return super.sendMessage(userExpenseValidationMessage);
    }
}