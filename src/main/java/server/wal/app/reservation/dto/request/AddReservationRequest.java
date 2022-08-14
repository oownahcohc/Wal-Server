package server.wal.app.reservation.dto.request;

import lombok.*;
import server.wal.domain.common.enumerate.ShowStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AddReservationRequest {

    @NotBlank(message = "{reservation.contents.notBlank}")
    private String contents;

    @NotBlank(message = "{reservation.localDate.notBlank}")
    private String localDate;

    @NotBlank(message = "{reservation.localTime.notBlank}")
    private String localTime;

    @NotNull(message = "{reservation.showStatus.notNull}")
    private ShowStatus showStatus;

    public AddReservationDto toServiceDto() {
        return AddReservationDto.of(contents, localDate, localTime, showStatus);
    }

}
