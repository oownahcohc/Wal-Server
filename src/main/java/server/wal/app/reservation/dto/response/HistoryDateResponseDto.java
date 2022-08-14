package server.wal.app.reservation.dto.response;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HistoryDateResponseDto {

    private String monthDate;
    private String dayOfWeek;
    private String time;

    public static HistoryDateResponseDto of(String monthDate, String dayOfWeek, String time) {
        return new HistoryDateResponseDto(monthDate, dayOfWeek, time);
    }

}
