package com.bcavus.invoiceapp.expenseservice.service.messaging;

import com.bcavus.invoiceapp.expenseservice.config.RabbitMQConfig;
import com.bcavus.invoiceapp.expenseservice.service.messaging.message.InvoiceExpenseValidationMessage;
import com.bcavus.invoiceapp.expenseservice.service.messaging.message.RabbitMessage;
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

        if(message instanceof InvoiceExpenseValidationMessage) {

            return this.sendMessageToQueue(
                    rabbitMQConfig.getInvoiceValidationQueueName(),
                    rabbitMQConfig.getInvoiceValidationExchange(),
                    rabbitMQConfig.getInvoiceValidationKey(),
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