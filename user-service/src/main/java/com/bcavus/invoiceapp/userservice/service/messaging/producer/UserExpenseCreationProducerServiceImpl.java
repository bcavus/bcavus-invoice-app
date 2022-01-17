package com.bcavus.invoiceapp.userservice.service.messaging.producer;

import com.bcavus.invoiceapp.userservice.service.messaging.AbstractRabbitMQConnection;
import com.bcavus.invoiceapp.userservice.service.messaging.message.UserExpenseCreationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserExpenseCreationProducerServiceImpl extends AbstractRabbitMQConnection implements UserExpenseCreationProducerService<UserExpenseCreationMessage> {

    private final Logger logger = LoggerFactory.getLogger(UserExpenseCreationProducerServiceImpl.class);

    public UserExpenseCreationProducerServiceImpl() {
        super();
    }

    @Override
    public boolean sendMessage(UserExpenseCreationMessage userValidationMessage) {
        logger.info("[UserExpenseCreationProducerService/sendMessage]: Sending Message = " + userValidationMessage);

        return super.sendMessage(userValidationMessage);
    }
}