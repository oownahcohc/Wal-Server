package server.wal.app.reservation.dto.request;

import lombok.*;
import server.wal.app.notification.activemq.publisher.dto.request.PubReservationDto;
import server.wal.common.util.TimeUtils;
import server.wal.domain.common.enumerate.ShowStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AddReservationDto {

    private String contents;
    private LocalDate localDate;
    private LocalTime localTime;
    private ShowStatus showStatus;

    public static AddReservationDto of(String contents, String localDate, String localTime, ShowStatus showStatus) {
        return new AddReservationDto(contents, LocalDate.parse(localDate), LocalTime.parse(localTime), showStatus);
    }

    public PubReservationDto toPubReservationDto(Long userId) {
        return PubReservationDto.of(userId, localDate, localTime);
    }

}
