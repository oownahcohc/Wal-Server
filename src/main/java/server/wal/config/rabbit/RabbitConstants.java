package server.wal.config.rabbit;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RabbitConstants {

    public static final String MORNING_QUEUE = "MORNING_QUEUE";
    public static final String AFTERNOON_QUEUE = "AFTERNOON_QUEUE";
    public static final String NIGHT_QUEUE = "NIGHT_QUEUE";
    public static final String RESERVATION_QUEUE = "RESERVATION_QUEUE";

    public static final String EXCHANGE_NAME = "TIME.TOPIC";

}
