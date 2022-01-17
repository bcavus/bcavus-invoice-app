package com.bcavus.invoiceapp.expenseservice.service.messaging.consumer;

import com.bcavus.invoiceapp.expenseservice.service.ExpenseService;
import com.bcavus.invoiceapp.expenseservice.service.messaging.message.UserExpenseCreationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseUserCreationConsumerImpl implements ExpenseUserCreationConsumer<UserExpenseCreationMessage> {
    private final Logger logger = LoggerFactory.getLogger(ExpenseUserCreationConsumerImpl.class);

    @Autowired
    private final ExpenseService expenseService;

    public ExpenseUserCreationConsumerImpl(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @RabbitListener(queues = "user-creation-queue")
    public void receiveMessage(UserExpenseCreationMessage message) {

        if(message.getUserid() == null) {
            logger.warn("[ExpenseUserCreationConsumer/receiveMessage]: Cannot create expense, message has no user id: " + message);
        }

        try{
            this.expenseService.createExpense(message.getUserid());

            logger.warn("[ExpenseUserCreationConsumer/receiveMessage]: Successfully created expense with given message: " + message);
        }catch (Exception ex) {
            logger.warn("[ExpenseUserCreationConsumer/receiveMessage]: Failed to create expense " + ex.getMessage());
        }
    }
}