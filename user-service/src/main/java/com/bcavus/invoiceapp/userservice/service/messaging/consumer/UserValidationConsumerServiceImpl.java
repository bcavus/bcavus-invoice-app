package com.bcavus.invoiceapp.userservice.service.messaging.consumer;

import com.bcavus.invoiceapp.userservice.service.UserService;
import com.bcavus.invoiceapp.userservice.service.messaging.message.InvoiceUserValidationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserValidationConsumerServiceImpl implements UserValidationConsumerService<InvoiceUserValidationMessage> {

    private final Logger logger = LoggerFactory.getLogger(UserValidationConsumerServiceImpl.class);

    @Autowired
    private final UserService userService;

    public UserValidationConsumerServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @RabbitListener(queues = "user-validation-queue")
    public void receiveMessage(InvoiceUserValidationMessage invoiceUserValidationMessage) {

        try{
            this.userService.validateUserExpense(invoiceUserValidationMessage.getInvoiceId(),
                    invoiceUserValidationMessage.getUserEmail(),
                    invoiceUserValidationMessage.getExpenseAmount());

            logger.info("[UserValidationConsumerService/receiveMessage]: Successfully validated user with given message: " + invoiceUserValidationMessage);
        }catch (Exception ex) {
            logger.warn("[UserValidationConsumerService/receiveMessage]: Failed to validate user " + ex.getMessage());
        }
    }
}