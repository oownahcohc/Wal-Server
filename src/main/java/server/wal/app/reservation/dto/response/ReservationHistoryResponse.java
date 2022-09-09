package server.wal.app.reservation.dto.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationHistoryResponse {

    private List<ReservationResponseDto> incompleteData;
    private List<ReservationResponseDto> completeData;

    @Builder(access = AccessLevel.PACKAGE)
    private ReservationHistoryResponse(final List<ReservationResponseDto> incompleteData, final List<ReservationResponseDto> completeData) {
        this.incompleteData = incompleteData;
        this.completeData = completeData;
    }

    public static ReservationHistoryResponse of(List<ReservationResponseDto> notDoneReservation, List<ReservationResponseDto> doneReservation) {
        return ReservationHistoryResponse.builder()
                .incompleteData(notDoneReservation)
                .completeData(doneReservation)
                .build();
    }

}
