package server.wal.app.notification.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalNotificationScheduler {

    private final RabbitListenerEndpointRegistry registry;

    @Scheduled(cron = "0 0 8 * * *")
    public void startMorningQueue() {
        log.info("Morning Queue Start");
        registry.getListenerContainers()
                .forEach(container -> {
                    log.info(String.format("start morning container (%s)", container));
                    container.start();
                });
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void startAfternoonQueue() {
        log.info("Afternoon Queue Start");
        registry.getListenerContainers()
                .forEach(container -> {
                    log.info(String.format("start morning container (%s)", container));
                    container.start();
                });
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void startNightQueue() {
        log.info("Night Queue Start");
        registry.getListenerContainers()
                .forEach(container -> {
                    log.info(String.format("start morning container (%s)", container));
                    container.start();
                });
    }

}
