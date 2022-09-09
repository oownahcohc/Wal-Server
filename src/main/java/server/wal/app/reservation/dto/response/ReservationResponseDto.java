package server.wal.app.reservation.dto.response;

import lombok.*;
import server.wal.common.dto.AuditingTimeResponse;
import server.wal.domain.common.enumerate.ShowStatus;
import server.wal.domain.reservation.Reservation;
import server.wal.domain.reservation.SendStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationResponseDto extends AuditingTimeResponse {

    private Long reservationId;
    private String contents;
    private String sendMessage;
    private ShowStatus showStatus;

    @Builder(access = AccessLevel.PACKAGE)
    private ReservationResponseDto(Long reservationId, String contents, String sendMessage, ShowStatus showStatus) {
        this.reservationId = reservationId;
        this.contents = contents;
        this.sendMessage = sendMessage;
        this.showStatus = showStatus;
    }

    public static ReservationResponseDto from(Reservation reservation) {
        String detailMessage = getDetailMessage(
                getDetailDateInfo(reservation.getSendDueDate()),
                reservation.getSendStatus()
        );
        ReservationResponseDto responseDto = ReservationResponseDto.builder()
                .reservationId(reservation.getId())
                .contents(reservation.getContents())
                .sendMessage(detailMessage)
                .showStatus(reservation.getShowStatus())
                .build();
        responseDto.setBaseTime(reservation);
        return responseDto;
    }

    private static HistoryDateResponseDto getDetailDateInfo(LocalDateTime sendDueDate) {
        String monthDate = sendDueDate.format(DateTimeFormatter.ofPattern("MM. dd"));
        String time = sendDueDate.format(DateTimeFormatter.ofPattern(":mm"));

        int hour = sendDueDate.getHour();
        if (hour > 12) {
            time = "오후 " + (hour - 12) + time;
        } else {
            time = "오전 " + hour + time;
        }

        String dayOfWeek = sendDueDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);

        return HistoryDateResponseDto.of(monthDate, dayOfWeek, time);
    }

    private static String getDetailMessage(HistoryDateResponseDto request, SendStatus sendStatus) {
        return request.getMonthDate() + " " +
                request.getDayOfWeek() + " " +
                request.getTime() +
                (
                        sendStatus.equals(SendStatus.NOT_DONE)
                        ? " • 전송 예정"
                        : " • 전송 완료"
                );
    }

}
