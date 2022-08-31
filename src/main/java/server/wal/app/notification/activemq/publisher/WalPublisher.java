package server.wal.app.notification.activemq.publisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ScheduledMessage;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import server.wal.app.notification.activemq.constant.ActiveMQConstants;
import server.wal.app.notification.activemq.publisher.dto.request.PubReservationDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalPublisher {

    private final JmsTemplate jmsTemplate;

    public void publishReservation(PubReservationDto requestDto) {
        jmsTemplate.convertAndSend(ActiveMQConstants.RESERVATION_QUEUE, requestDto.getUserId(), message -> {
            message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, requestDto.getDelay());
            log.info(String.format("유저 (%s)의 예약 딜레이 시간은 (%s) 입니다", requestDto.getUserId(), message.getLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY)));
            return message;
        });
    }

}
