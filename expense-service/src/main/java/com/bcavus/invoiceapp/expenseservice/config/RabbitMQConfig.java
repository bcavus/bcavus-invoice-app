package com.bcavus.invoiceapp.expenseservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
public class RabbitMQConfig {

    @Value("${expense-service.rabbitmq.user.creation-queue}")
    private String userCreationQueue;

    @Value("${expense-service.rabbitmq.user.exchange}")
    private String userCreationExchange;

    @Value("${expense-service.rabbitmq.user.routingkey}")
    private String userCreationKey;

    @Value("${expense-service.rabbitmq.invoice.validation-queue}")
    private String invoiceValidationQueue;

    @Value("${expense-service.rabbitmq.invoice.exchange}")
    private String invoiceValidationExchange;

    @Value("${expense-service.rabbitmq.invoice.routingkey}")
    private String invoiceValidationKey;

    @Bean
    public Queue userCreation() {
        return new Queue(this.getUserCreationQueueName(), true);
    }

    @Bean
    public Queue invoiceValidation() { return new Queue(this.getInvoiceValidationQueueName(), true); }

    @Bean
    public TopicExchange getUserCreationExchangeName() {
        return new TopicExchange(this.getUserCreationExchange());
    }

    @Bean
    public TopicExchange getInvoiceValidationExchangeName() { return new TopicExchange(this.getInvoiceValidationExchange()); }

    @Bean
    public Binding userCreationBinding() {
        return BindingBuilder
                .bind(this.userCreation())
                .to(this.getUserCreationExchangeName())
                .with(this.getUserCreationKey());
    }

    @Bean
    public Binding invoiceValidationBinding() {
        return BindingBuilder
                .bind(this.invoiceValidation())
                .to(this.getInvoiceValidationExchangeName())
                .with(this.getInvoiceValidationKey());
    }

    @Bean
    public Jackson2JsonMessageConverter getMessageConverter() {
        return new Jackson2JsonMessageConverter();

    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory factory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
        rabbitTemplate.setMessageConverter(getMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(consumerJackson2MessageConverter());
        return factory;
    }

    public String getUserCreationQueueName() { return this.userCreationQueue; }

    public String getUserCreationExchange() { return this.userCreationExchange; }

    public String getUserCreationKey() { return this.userCreationKey; }

    public String getInvoiceValidationQueueName() { return this.invoiceValidationQueue; }

    public String getInvoiceValidationExchange() { return this.invoiceValidationExchange; }

    public String getInvoiceValidationKey() { return this.invoiceValidationKey; }

}