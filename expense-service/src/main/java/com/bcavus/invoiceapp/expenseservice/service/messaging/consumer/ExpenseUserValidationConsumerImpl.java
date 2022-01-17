package com.bcavus.invoiceapp.expenseservice.service.messaging.consumer;

import com.bcavus.invoiceapp.expenseservice.service.ExpenseService;
import com.bcavus.invoiceapp.expenseservice.service.messaging.message.UserExpenseValidationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseUserValidationConsumerImpl implements ExpenseUserValidationConsumer<UserExpenseValidationMessage> {
    private final Logger logger = LoggerFactory.getLogger(ExpenseUserValidationConsumerImpl.class);

    @Autowired
    private final ExpenseService expenseService;

    public ExpenseUserValidationConsumerImpl(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @Override
    @RabbitListener(queues = "expense-validation-queue")
    public void receiveMessage(UserExpenseValidationMessage userExpenseValidationMessage) {
        logger.info("--" + userExpenseValidationMessage);

        boolean isBudgetAvailable = false;

        if(userExpenseValidationMessage.getUserId() == null
                || userExpenseValidationMessage.getInvoiceId() == null
                || userExpenseValidationMessage.getExpenseAmount() == null) {
            logger.warn("[ExpenseUserValidationConsumer/receiveMessage]: Cannot validate expense, message has no user id: " + userExpenseValidationMessage);
        }

        try {
             this.expenseService.validateExpense(userExpenseValidationMessage.getInvoiceId(),
                    userExpenseValidationMessage.getUserId(),
                    userExpenseValidationMessage.getExpenseAmount());
        }catch (Exception ex) {
            logger.warn("[ExpenseUserValidationConsumer/receiveMessage]: Failed to validate expense " + ex.getMessage());
        }

    }
}