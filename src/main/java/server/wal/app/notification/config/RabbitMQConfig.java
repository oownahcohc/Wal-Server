package server.wal.app.notification.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static server.wal.app.notification.config.RabbitConstants.*;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    @Bean
    public Queue morningQueue() {
        return new Queue(MORNING_QUEUE, true);
    }

    @Bean
    public Queue afternoonQueue() {
        return new Queue(AFTERNOON_QUEUE, true);
    }

    @Bean
    public Queue nightQueue() {
        return new Queue(NIGHT_QUEUE, true);
    }

    @Bean
    public Queue reservationQueue() {
        return new Queue(RESERVATION_QUEUE, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding morningBinding() {
        return BindingBuilder
                .bind(morningQueue())
                .to(exchange())
                .with(MORNING_ROUTING_KEY);
    }

    @Bean
    public Binding afternoonBinding() {
        return BindingBuilder
                .bind(afternoonQueue())
                .to(exchange())
                .with(AFTERNOON_ROUTING_KEY);
    }

    @Bean
    public Binding nightBinding() {
        return BindingBuilder
                .bind(nightQueue())
                .to(exchange())
                .with(NIGHT_ROUTING_KEY);
    }

    @Bean
    public Binding reservationBinding() {
        return BindingBuilder
                .bind(reservationQueue())
                .to(exchange())
                .with(RESERVATION_ROUTING_KEY);
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

}
