package com.bcavus.invoiceapp.expenseservice.service.messaging;

import com.bcavus.invoiceapp.expenseservice.config.RabbitMQConfig;
import com.bcavus.invoiceapp.expenseservice.dto.ExpenseDTO;
import com.bcavus.invoiceapp.expenseservice.dto.message.InvoiceExpenseValidationMessage;
import com.bcavus.invoiceapp.expenseservice.dto.message.RabbitMessage;
import com.bcavus.invoiceapp.expenseservice.dto.message.UserExpenseCreationMessage;
import com.bcavus.invoiceapp.expenseservice.dto.message.UserExpenseValidationMessage;
import com.bcavus.invoiceapp.expenseservice.service.ExpenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseMessageServiceImpl implements ExpenseMessageService {

    private final Logger logger = LoggerFactory.getLogger(ExpenseMessageServiceImpl.class);

    @Autowired
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    private final RabbitMQConfig rabbitMQConfig;

    @Autowired
    private final ExpenseService expenseService;

    public ExpenseMessageServiceImpl(RabbitTemplate rabbitTemplate,
                                     RabbitMQConfig rabbitMQConfig,
                                     ExpenseService expenseService) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQConfig = rabbitMQConfig;
        this.expenseService = expenseService;
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

        if(message instanceof InvoiceExpenseValidationMessage) {

            return this.sendMessage(
                    rabbitMQConfig.getInvoiceValidationQueueName(),
                    rabbitMQConfig.getInvoiceValidationExchange(),
                    rabbitMQConfig.getInvoiceValidationKey(),
                    message);
        }

        throw new IllegalArgumentException("Message is not suitable for sending.");

    }

    @RabbitListener(queues = "user-creation-queue")
    private void receiveMessage(UserExpenseCreationMessage message) {

        logger.info("Received message: " + message);

        this.expenseService.createExpense(message.getUserid());
    }

    @RabbitListener(queues = "expense-validation-queue")
    private void receiveMessage(UserExpenseValidationMessage message) {
        logger.info("--" + message);

        boolean isBudgetAvailable = false;

        try {
            ExpenseDTO expenseDTO = this.expenseService.spendAmountFromExpenseBudgetByUserId(message.getUserId(), message.getExpenseAmount());

            isBudgetAvailable = true;
        }catch (Exception ex) {
            logger.warn(ex.getMessage());
        }

        this.sendMessage(InvoiceExpenseValidationMessage.builder()
                .invoiceId(message.getInvoiceId())
                .available(isBudgetAvailable)
                .build());

    }

    private boolean sendMessage(String queue, String exchange, String routingKey, RabbitMessage message) {

        boolean isSuccess = false;

        try{
            rabbitTemplate.convertAndSend(exchange, routingKey, message);

            logger.info("[ExpenseMessageService/sendMessage] Successfully sent message:"
                    + " queue= " + queue
                    + " exchange= " + exchange
                    + " routingKey= " + routingKey
                    + " message= " + message);

            isSuccess = true;
        }catch (AmqpException exception) {
            logger.warn("[ExpenseMessageService/sendMessage] Exception during sending message : " + exception.getMessage());
        }

        return isSuccess;
    }
}