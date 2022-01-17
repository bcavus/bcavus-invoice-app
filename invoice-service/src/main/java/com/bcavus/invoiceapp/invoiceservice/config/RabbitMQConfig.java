package com.bcavus.invoiceapp.invoiceservice.config;

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

    @Value("${invoice-service.rabbitmq.producer.user.validation-queue}")
    private String userValidationQueue;

    @Value("${invoice-service.rabbitmq.producer.user.exchange}")
    private String userValidationExchange;

    @Value("${invoice-service.rabbitmq.producer.user.routingkey}")
    private String userValidationKey;

    @Value("${invoice-service.rabbitmq.consumer.expense.validation-queue}")
    private String expenseValidationQueue;

    @Value("${invoice-service.rabbitmq.consumer.expense.exchange}")
    private String expenseValidationExchange;

    @Value("${invoice-service.rabbitmq.consumer.expense.routingkey}")
    private String expenseValidationKey;

    @Bean
    public Queue userValidation() {
        return new Queue(this.getUserValidationQueueName(), true);
    }

    @Bean
    public Queue expenseValidation() {
        return new Queue(this.getExpenseValidationQueueName(), true);
    }

    @Bean
    public TopicExchange getUserValidationExchangeName() {
        return new TopicExchange(this.getUserValidationExchange());
    }

    @Bean
    public TopicExchange getExpenseValidationExchangeName() {
        return new TopicExchange(this.getExpenseValidationExchange());
    }

    @Bean
    public Binding userValidationBinding() {
        return BindingBuilder
                .bind(this.userValidation())
                .to(this.getUserValidationExchangeName())
                .with(this.getUserValidationKey());
    }

    @Bean
    public Binding expenseValidationBinding() {
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

    public String getUserValidationQueueName() { return this.userValidationQueue; }

    public String getExpenseValidationQueueName() { return this.expenseValidationQueue; }

    public String getUserValidationExchange() { return this.userValidationExchange; }

    public String getExpenseValidationExchange() { return  this.expenseValidationExchange; }

    public String getUserValidationKey() { return this.userValidationKey; }

    public String getExpenseValidationKey() { return this.expenseValidationKey; }

}