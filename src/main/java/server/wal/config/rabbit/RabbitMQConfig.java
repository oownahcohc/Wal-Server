package server.wal.config.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

import static server.wal.config.rabbit.RabbitConstants.*;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    @Bean
    List<Queue> queue() {
        Queue morningQueue = new Queue(MORNING_QUEUE, false);
        Queue afternoonQueue = new Queue(AFTERNOON_QUEUE, false);
        Queue nightQueue = new Queue(NIGHT_QUEUE, false);
        Queue reservationQueue = new Queue(RESERVATION_QUEUE, false);
        return List.of(morningQueue, afternoonQueue, nightQueue, reservationQueue);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    List<Binding> bindings(List<Queue> queues, TopicExchange exchange) {
        return queues.stream()
                .map(queue -> BindingBuilder.bind(queue).to(exchange).with(""))
                .collect(Collectors.toList());
    }

}
