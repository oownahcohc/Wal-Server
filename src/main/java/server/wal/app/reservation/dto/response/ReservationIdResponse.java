package server.wal.app.reservation.dto.response;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ReservationIdResponse {

    private Long reservationId;

    private ReservationIdResponse() {}

    private ReservationIdResponse(final Long reservationId) {
        this.reservationId = reservationId;
    }

    public static ReservationIdResponse from(Long reservationId) {
        return new ReservationIdResponse(reservationId);
    }

}
