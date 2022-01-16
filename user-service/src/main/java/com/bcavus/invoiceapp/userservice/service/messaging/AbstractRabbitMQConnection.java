package com.bcavus.invoiceapp.userservice.service.messaging;

import com.bcavus.invoiceapp.userservice.config.RabbitMQConfig;
import com.bcavus.invoiceapp.userservice.service.messaging.message.RabbitMessage;
import com.bcavus.invoiceapp.userservice.service.messaging.message.UserExpenseCreationMessage;
import com.bcavus.invoiceapp.userservice.service.messaging.message.UserExpenseValidationMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;


@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractRabbitMQConnection implements RabbitMQMessageSender<RabbitMessage> {

    private final Logger logger = LoggerFactory.getLogger(AbstractRabbitMQConnection.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitMQConfig rabbitMQConfig;

    @Override
    public boolean sendMessage(RabbitMessage message) throws IllegalArgumentException {

        if(message instanceof UserExpenseCreationMessage){

            return this.sendMessageToQueue(
                    rabbitMQConfig.getUserCreationQueueName(),
                    rabbitMQConfig.getUserCreationExchange(),
                    rabbitMQConfig.getUserCreationKey(),
                    message);
        }


        if(message instanceof UserExpenseValidationMessage) {

            return this.sendMessageToQueue(
                    rabbitMQConfig.getExpenseValidationQueueName(),
                    rabbitMQConfig.getExpenseValidationExchange(),
                    rabbitMQConfig.getExpenseValidationKey(),
                    message);
        }

        throw new IllegalArgumentException("Message is not suitable for sending.");

    }

    private boolean sendMessageToQueue(String queue, String exchange, String routingKey, RabbitMessage message) {

        boolean isSuccess = false;

        try{
            rabbitTemplate.convertAndSend(exchange, routingKey, message);

            logger.info("[AbstractRabbitMQConnection] Successfully sent message:"
                    + " queue= " + queue
                    + " exchange= " + exchange
                    + " routingKey= " + routingKey
                    + " message= " + message);

            isSuccess = true;
        }catch (AmqpException exception) {
            logger.warn("[AbstractRabbitMQConnection] Exception during sending message : " + exception.getMessage());
        }

        return isSuccess;
    }
}