package com.bcavus.invoiceapp.userservice.service.messaging.consumer;

import com.bcavus.invoiceapp.userservice.dto.UserDTO;
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

        String userEmail = invoiceUserValidationMessage.getUserEmail();

        try{
            UserDTO foundUser = this.userService.getUserByEmail(userEmail);

            /*
                this.sendMessage(UserExpenseValidationMessage.builder()
                    .invoiceId(message.getInvoiceId())
                    .userId(foundUser.get().getId())
                    .expenseAmount(message.getExpenseAmount())
                    .build());
             */

            logger.info("[UserValidationConsumerService/receiveMessage]: Successfully validated user with given message: " + invoiceUserValidationMessage);
        }catch (Exception ex) {
            logger.warn("[UserValidationConsumerService/receiveMessage]: Failed to validate user " + ex.getMessage());
        }
    }
}