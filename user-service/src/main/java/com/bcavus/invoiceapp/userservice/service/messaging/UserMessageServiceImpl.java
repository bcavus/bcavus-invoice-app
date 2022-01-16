package com.bcavus.invoiceapp.userservice.service.messaging;

import com.bcavus.invoiceapp.userservice.config.RabbitMQConfig;
import com.bcavus.invoiceapp.userservice.dto.message.InvoiceUserValidationMessage;
import com.bcavus.invoiceapp.userservice.dto.message.UserExpenseCreationMessage;
import com.bcavus.invoiceapp.userservice.dto.message.RabbitMessage;
import com.bcavus.invoiceapp.userservice.dto.message.UserExpenseValidationMessage;
import com.bcavus.invoiceapp.userservice.model.User;
import com.bcavus.invoiceapp.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserMessageServiceImpl implements UserMessageService {

    private final Logger logger = LoggerFactory.getLogger(UserMessageServiceImpl.class);

    @Autowired
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    private final RabbitMQConfig rabbitMQConfig;

    @Autowired
    private final UserRepository userRepository;

    public UserMessageServiceImpl(RabbitTemplate rabbitTemplate,
                                  RabbitMQConfig rabbitMQConfig,
                                  UserRepository userRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQConfig = rabbitMQConfig;
        this.userRepository = userRepository;
    }

    @Override
    public boolean sendMessage(RabbitMessage message) {

        if(message instanceof UserExpenseCreationMessage){

            return this.sendMessage(
                    rabbitMQConfig.getUserCreationQueueName(),
                    rabbitMQConfig.getUserCreationExchange(),
                    rabbitMQConfig.getUserCreationKey(),
                    message);
        }


        if(message instanceof UserExpenseValidationMessage) {

            return this.sendMessage(
                    rabbitMQConfig.getExpenseValidationQueueName(),
                    rabbitMQConfig.getExpenseValidationExchange(),
                    rabbitMQConfig.getExpenseValidationKey(),
                    message);
        }

        throw new IllegalArgumentException("Message is not suitable for sending.");

    }

    private boolean sendMessage(String queue, String exchange, String routingKey, RabbitMessage message) {

        boolean isSuccess = false;

        try{
            rabbitTemplate.convertAndSend(exchange, routingKey, message);

            logger.info("[UserMessageService/sendMessage] Successfully sent message:"
                    + " queue= " + queue
                    + " exchange= " + exchange
                    + " routingKey= " + routingKey
                    + " message= " + message);

            isSuccess = true;
        }catch (AmqpException exception) {
            logger.warn("[UserMessageService/sendMessage] Exception during sending message : " + exception.getMessage());
        }

        return isSuccess;
    }

    @RabbitListener(queues = "user-validation-queue")
    private void receiveMessage(InvoiceUserValidationMessage message) {

        String userEmail = message.getUserEmail();

        boolean isValid = false;

        Optional<User> foundUser = this.userRepository.findByEmail(userEmail);

        logger.info("***" + foundUser);

        if(foundUser.isPresent()) {
            logger.info("***" + foundUser.get().getId());

            this.sendMessage(UserExpenseValidationMessage.builder()
                    .invoiceId(message.getInvoiceId())
                    .userId(foundUser.get().getId())
                    .expenseAmount(message.getExpenseAmount())
                    .build());

            logger.info("User is valid.Sending message to expense-validation-queue" + message);
        }

        logger.info("Received message: " + message);
    }
}