package server.wal.app.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.wal.app.reservation.dto.response.CalenderDateResponse;
import server.wal.app.reservation.dto.response.ReservationHistoryResponse;
import server.wal.app.reservation.dto.response.ReservationResponseDto;
import server.wal.domain.reservation.Reservation;
import server.wal.domain.reservation.SendStatus;
import server.wal.domain.reservation.repository.ReservationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationRetrieveService {

    private final ReservationRepository reservationRepository;

    @Transactional(readOnly = true)
    public ReservationHistoryResponse getReservationHistory(Long userId) {
        List<Reservation> reservations = reservationRepository.findByUserId(userId);
        List<ReservationResponseDto> notDoneReservationResponse = extractReservationBySendStatus(reservations, SendStatus.NOT_DONE);
        List<ReservationResponseDto> doneReservationResponse = extractReservationBySendStatus(reservations, SendStatus.DONE);
        return ReservationHistoryResponse.of(notDoneReservationResponse, doneReservationResponse);
    }

    private List<ReservationResponseDto> extractReservationBySendStatus(List<Reservation> reservations, SendStatus sendStatus) {
        return reservations.stream()
                .filter(reservation -> reservation.getSendStatus().equals(sendStatus))
                .map(ReservationResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CalenderDateResponse> getReservationDate(Long userId) {
        return ReservationServiceUtils.findReservationAfterNow(reservationRepository, userId).stream()
                .map(reservation -> CalenderDateResponse.from(reservation.getSendDueDate()))
                .collect(Collectors.toList());
    }

}
