package server.wal.app.notification.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static server.wal.app.notification.config.RabbitConstants.*;

@Service
@RequiredArgsConstructor
public class RabbitPublisher {

    private static final String TOPIC_EXCHANGE = EXCHANGE_NAME;

    private final RabbitTemplate rabbitTemplate;

    public void publishMorningWal() {
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, MORNING_ROUTING_KEY, "유저 정보랑 설정된 투데이왈 정도?");
    }

    public void publishAfternoonWal() {
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, AFTERNOON_ROUTING_KEY, "유저 정보랑 설정된 투데이왈 정도?");
    }

    public void publishNightWal() {
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, NIGHT_ROUTING_KEY, "유저 정보랑 설정된 투데이왈 정도?");
    }

}
