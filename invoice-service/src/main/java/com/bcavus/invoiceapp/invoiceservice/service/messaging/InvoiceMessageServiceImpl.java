package com.bcavus.invoiceapp.invoiceservice.service.messaging;

import com.bcavus.invoiceapp.invoiceservice.component.ModelConverter;
import com.bcavus.invoiceapp.invoiceservice.component.ModelMapper;
import com.bcavus.invoiceapp.invoiceservice.config.RabbitMQConfig;
import com.bcavus.invoiceapp.invoiceservice.dto.InvoiceDTO;
import com.bcavus.invoiceapp.invoiceservice.dto.message.InvoiceExpenseValidationMessage;
import com.bcavus.invoiceapp.invoiceservice.dto.message.InvoiceMessage;
import com.bcavus.invoiceapp.invoiceservice.dto.message.InvoiceUserValidationMessage;
import com.bcavus.invoiceapp.invoiceservice.model.Invoice;
import com.bcavus.invoiceapp.invoiceservice.model.InvoiceStatus;
import com.bcavus.invoiceapp.invoiceservice.repository.InvoiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InvoiceMessageServiceImpl implements InvoiceMessageService {

    private final Logger logger = LoggerFactory.getLogger(InvoiceMessageServiceImpl.class);

    @Autowired
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    private final RabbitMQConfig rabbitMQConfig;

    @Autowired
    private final InvoiceRepository invoiceRepository;

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private final ModelConverter modelConverter;

    public InvoiceMessageServiceImpl(RabbitTemplate rabbitTemplate,
                                     RabbitMQConfig rabbitMQConfig,
                                     InvoiceRepository invoiceRepository,
                                     ModelMapper modelMapper,
                                     ModelConverter modelConverter) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQConfig = rabbitMQConfig;
        this.invoiceRepository = invoiceRepository;
        this.modelMapper = modelMapper;
        this.modelConverter = modelConverter;
    }

    @Override
    public boolean sendMessage(InvoiceMessage message) {

        if(message instanceof InvoiceUserValidationMessage){

            return this.sendMessage(
                    rabbitMQConfig.getUserValidationQueueName(),
                    rabbitMQConfig.getUserValidationExchange(),
                    rabbitMQConfig.getUserValidationKey(),
                    message);
        }
        else if(message instanceof InvoiceExpenseValidationMessage){

            return this.sendMessage(
                    rabbitMQConfig.getExpenseValidationQueueName(),
                    rabbitMQConfig.getExpenseValidationExchange(),
                    rabbitMQConfig.getExpenseValidationKey(),
                    message);
        }
        else {
            throw new IllegalArgumentException("Message is not suitable for sending.");
        }
    }

    private boolean sendMessage(String queue, String exchange, String routingKey, InvoiceMessage message) {

        boolean isSuccess = false;

        try{
            rabbitTemplate.convertAndSend(exchange, routingKey, message);

            logger.info("[InvoiceMessageService/sendMessage] Successfully sent message:"
                    + " queue= " + queue
                    + " exchange= " + exchange
                    + " routingKey= " + routingKey
                    + " message= " + message);

            isSuccess = true;
        }catch (AmqpException exception) {
            logger.warn("[InvoiceMessageService/sendMessage] Exception during sending message : " + exception.getMessage());
        }

        return isSuccess;
    }

    @RabbitListener(queues = "user-validation-queue")
    private void receiveMessage(InvoiceUserValidationMessage message) {

        String invoiceId = message.getInvoiceId();

        Optional<Invoice> invoice = this.invoiceRepository.findById(invoiceId);

        if(invoice.isPresent() && invoice.get().getStatus() == InvoiceStatus.CREATED) {
            InvoiceDTO invoiceDTO = this.modelMapper.mapToInvoiceDTO(invoice.get());

            invoiceDTO.setStatus(InvoiceStatus.VALIDATING.name());

            this.invoiceRepository.save(this.modelConverter.convertToInvoice(invoiceDTO));

            logger.info("Updated invoice with given id : " + invoiceDTO.getId());
        }

        logger.info("Received message: " + message);
    }

    @RabbitListener(queues = "expense-validation-queue")
    private void receiveMessage(InvoiceExpenseValidationMessage message) {
        logger.info("Received message: " + message);

        String invoiceId = message.getInvoiceId();

        Optional<Invoice> invoice = this.invoiceRepository.findById(invoiceId);

        if(invoice.isPresent() && invoice.get().getStatus() == InvoiceStatus.VALIDATING) {
            InvoiceDTO invoiceDTO = this.modelMapper.mapToInvoiceDTO(invoice.get());

            invoiceDTO.setStatus(message.isAvailable() ? InvoiceStatus.ACCEPTED.name() : InvoiceStatus.REJECTED.name());

            this.invoiceRepository.save(this.modelConverter.convertToInvoice(invoiceDTO));

            logger.info("Updated invoice with given id : " + invoiceDTO.getId());
        }

        logger.info("----" + invoice);
        logger.info("Received message: " + message);
    }

}