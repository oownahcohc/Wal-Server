package server.wal.app.notification.activemq.publisher.dto.request;

import lombok.*;
import server.wal.common.util.TimeUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PubReservationDto {

    private static final ZoneId KOREA = TimeUtils.KOREA_TIME_ZONE;

    private Long userId;
    private long delay;

    public static PubReservationDto of(Long userId, LocalDate date, LocalTime time) {
        long milliOfSendDueDate = LocalDateTime.of(date, time)
                .atZone(KOREA)
                .toInstant()
                .toEpochMilli();
        long milliOfNow = TimeUtils.getNow()
                .atZone(KOREA)
                .toInstant()
                .toEpochMilli();
        long delay = milliOfSendDueDate - milliOfNow;
        return new PubReservationDto(userId, delay);
    }

}
