package server.wal.domain.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.wal.app.reservation.dto.request.AddReservationDto;
import server.wal.common.util.TimeUtils;
import server.wal.domain.common.entity.AuditingTimeEntity;
import server.wal.domain.common.enumerate.ShowStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String contents;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
    private LocalDateTime sendDueDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private ShowStatus showStatus;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private SendStatus sendStatus;

    @Builder(access = AccessLevel.PACKAGE)
    private Reservation(Long userId, String contents, LocalDateTime sendDueDate, ShowStatus showStatus) {
        this.userId = userId;
        this.contents = contents;
        this.sendDueDate = sendDueDate;
        this.showStatus = showStatus;
        this.sendStatus = SendStatus.NOT_DONE;
    }

    public static Reservation newInstance(Long userId, AddReservationDto requestDto) {
        LocalDateTime sendDueDate = LocalDateTime.of(requestDto.getLocalDate(), requestDto.getLocalTime());
        return Reservation.builder()
                .userId(userId)
                .contents(requestDto.getContents())
                .sendDueDate(sendDueDate)
                .showStatus(requestDto.getShowStatus())
                .build();
    }

}
