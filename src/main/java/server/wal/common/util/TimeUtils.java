package server.wal.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeUtils {

    private static final ZoneId KOREA_TIME_ZONE = ZoneId.of("Asia/Seoul");
    private static final LocalTime MORNING = LocalTime.of(8, 0, 0);
    private static final LocalTime AFTERNOON = LocalTime.of(14, 0, 0);
    private static final LocalTime NIGHT = LocalTime.of(20, 0, 0);

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public static LocalDate NOW = LocalDate.now(KOREA_TIME_ZONE);

    public static LocalDateTime getNow() {
        return LocalDateTime.parse(LocalDateTime.of(NOW, LocalTime.now(KOREA_TIME_ZONE)).format(FORMATTER));
    }

    public static LocalDateTime getMorning() {
        return LocalDateTime.parse(LocalDateTime.of(NOW, MORNING).format(FORMATTER));
    }

    public static LocalDateTime getAfternoon() {
        return LocalDateTime.parse(LocalDateTime.of(NOW, AFTERNOON).format(FORMATTER));
    }

    public static LocalDateTime getNight() {
        return LocalDateTime.parse(LocalDateTime.of(NOW, NIGHT).format(FORMATTER));
    }

}
