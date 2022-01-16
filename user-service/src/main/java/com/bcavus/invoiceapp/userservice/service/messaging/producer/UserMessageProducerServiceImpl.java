package com.bcavus.invoiceapp.userservice.service.messaging.producer;

import com.bcavus.invoiceapp.userservice.service.messaging.AbstractRabbitMQConnection;
import com.bcavus.invoiceapp.userservice.service.messaging.message.UserExpenseCreationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserMessageProducerServiceImpl extends AbstractRabbitMQConnection implements UserMessageProducerService<UserExpenseCreationMessage> {

    private final Logger logger = LoggerFactory.getLogger(UserMessageProducerServiceImpl.class);

    public UserMessageProducerServiceImpl() {
        super();
    }

    @Override
    public boolean sendMessage(UserExpenseCreationMessage userValidationMessage) {
        logger.info("[UserMessageProducerService/sendMessage]: Sending Message = " + userValidationMessage);

        return super.sendMessage(userValidationMessage);
    }
}