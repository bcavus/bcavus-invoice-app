package com.bcavus.invoiceapp.userservice.config;

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

    @Value("${user-service.rabbitmq.user.creation-queue}")
    private String userCreationQueue;

    @Value("${user-service.rabbitmq.user.exchange}")
    private String userCreationExchange;

    @Value("${user-service.rabbitmq.user.routingkey}")
    private String userCreationRoutingKey;

    @Value("${user-service.rabbitmq.expense.validation-queue}")
    private String expenseValidationQueue;

    @Value("${user-service.rabbitmq.expense.exchange}")
    private String expenseValidationExchange;

    @Value("${user-service.rabbitmq.expense.routingkey}")
    private String expenseValidationKey;

    @Bean
    public Queue userCreation() {
        return new Queue(this.getUserCreationQueueName(), true);
    }

    @Bean
    public Queue expenseValidation() { return new Queue(this.getExpenseValidationQueueName(), true);}

    @Bean
    public TopicExchange getUserCreationExchangeName() {
        return new TopicExchange(this.getUserCreationExchange());
    }

    @Bean
    public TopicExchange getExpenseValidationExchangeName() {
        return new TopicExchange(this.getExpenseValidationExchange());
    }

    @Bean
    public Binding userCreationBinding() {
        return BindingBuilder
                .bind(this.userCreation())
                .to(this.getUserCreationExchangeName())
                .with(this.getUserCreationKey());
    }

    @Bean
    public Binding expenseValidationBinding(){
        return BindingBuilder
                .bind(this.expenseValidation())
                .to(this.getExpenseValidationExchangeName())
                .with(this.getExpenseValidationKey());
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

    public String getUserCreationKey() { return this.userCreationRoutingKey; }

    public String getExpenseValidationQueueName() { return this.expenseValidationQueue; }

    public String getExpenseValidationExchange() { return this.expenseValidationExchange; }

    public String getExpenseValidationKey() { return this.expenseValidationKey; }

}