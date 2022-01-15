package com.bcavus.invoiceapp.userservice.service.messaging;

import com.bcavus.invoiceapp.userservice.component.ModelConverter;
import com.bcavus.invoiceapp.userservice.component.ModelMapper;
import com.bcavus.invoiceapp.userservice.config.RabbitMQConfig;
import com.bcavus.invoiceapp.userservice.dto.message.UserExpenseCreationMessage;
import com.bcavus.invoiceapp.userservice.dto.message.UserMessage;
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

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private final ModelConverter modelConverter;

    public UserMessageServiceImpl(RabbitTemplate rabbitTemplate,
                                  RabbitMQConfig rabbitMQConfig,
                                  UserRepository userRepository,
                                  ModelMapper modelMapper,
                                  ModelConverter modelConverter) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQConfig = rabbitMQConfig;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.modelConverter = modelConverter;
    }

    @Override
    public boolean sendMessage(UserMessage message) {
        if(message instanceof UserExpenseCreationMessage){

            return this.sendMessage(
                    rabbitMQConfig.getUserCreationQueueName(),
                    rabbitMQConfig.getUserCreationExchange(),
                    rabbitMQConfig.getUserCreationKey(),
                    message);
        }
        else {
            throw new IllegalArgumentException("Message is not suitable for sending.");
        }
    }

    @RabbitListener(queues = "user-creation-queue")
    private void receiveMessage(UserExpenseCreationMessage message) {

        logger.info("Received message: " + message);
    }

    private boolean sendMessage(String queue, String exchange, String routingKey, UserMessage message) {

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
}