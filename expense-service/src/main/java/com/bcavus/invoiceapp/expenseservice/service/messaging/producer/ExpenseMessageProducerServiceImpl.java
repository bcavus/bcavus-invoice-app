package com.bcavus.invoiceapp.expenseservice.service.messaging.producer;

import com.bcavus.invoiceapp.expenseservice.service.messaging.AbstractRabbitMQConnection;
import com.bcavus.invoiceapp.expenseservice.service.messaging.message.InvoiceExpenseValidationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExpenseMessageProducerServiceImpl extends AbstractRabbitMQConnection implements ExpenseMessageProducerService<InvoiceExpenseValidationMessage> {
    private final Logger logger = LoggerFactory.getLogger(ExpenseMessageProducerServiceImpl.class);

    public ExpenseMessageProducerServiceImpl() {
        super();
    }

    @Override
    public boolean sendMessage(InvoiceExpenseValidationMessage invoiceExpenseValidationMessage) {
        logger.info("[ExpenseMessageProducerServiceImpl/sendMessage]: Sending Message = " + invoiceExpenseValidationMessage);

        return super.sendMessage(invoiceExpenseValidationMessage);
    }
}