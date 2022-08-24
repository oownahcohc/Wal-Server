package server.wal.config.rabbit;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
//@PropertySource(value = "classpath:application-rabbit.yml", factory = YamlPropertySourceFactory.class, ignoreResourceNotFound = true)
public final class RabbitConstants {

    public static final String MORNING_QUEUE = "MORNING_QUEUE";
    public static final String AFTERNOON_QUEUE = "AFTERNOON_QUEUE";
    public static final String NIGHT_QUEUE = "NIGHT_QUEUE";
    public static final String RESERVATION_QUEUE = "RESERVATION_QUEUE";

    public static final String EXCHANGE_NAME = "TIME.TOPIC";

    public static final String MORNING_ROUTING_KEY = "MORNING";
    public static final String AFTERNOON_ROUTING_KEY = "AFTERNOON";
    public static final String NIGHT_ROUTING_KEY = "NIGHT";
    public static final String RESERVATION_ROUTING_KEY = "RESERVATION";

}
