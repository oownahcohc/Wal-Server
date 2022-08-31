package server.wal.app.notification.activemq.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import server.wal.app.notification.activemq.constant.ActiveMQConstants;
import server.wal.app.notification.firebase.FCMService;

@Component
@RequiredArgsConstructor
public class WalConsumer {

    private final FCMService fcmService;

    @JmsListener(destination = ActiveMQConstants.RESERVATION_QUEUE)
    public void reservationConsumer(@Payload Long userId) {
        fcmService.sendReservationWal(userId);
    }

}
