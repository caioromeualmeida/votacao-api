package com.teste.votacao.api.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguracao {
    private String queueName = "mq";
    private String exchange = "ex";
    private String routingKey = "rk";

    @Bean
    Queue queue() {
        return new Queue(queueName, true, true, true);
    }

    @Bean
    TopicExchange topicExchange() {
        TopicExchange topic = new TopicExchange(exchange, true, true);
        topic.setDelayed(true); //ajuste no exchange para que o plugin aceite o delay da mensagem
        return topic;
    }

    @Bean
    Binding binding(final Queue queue, final TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with(routingKey);
    }
}
