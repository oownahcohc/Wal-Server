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
        List<ReservationResponseDto> notDoneReservationResponse = extractNotDoneReservation(reservations);
        List<ReservationResponseDto> doneReservationResponse = extractDoneReservation(reservations);
        return ReservationHistoryResponse.of(notDoneReservationResponse, doneReservationResponse);
    }

    private List<ReservationResponseDto> extractDoneReservation(List<Reservation> reservations) {
        return reservations.stream()
                .filter(reservation -> reservation.getSendStatus().equals(SendStatus.DONE))
                .map(ReservationResponseDto::of)
                .collect(Collectors.toList());
    }

    private List<ReservationResponseDto> extractNotDoneReservation(List<Reservation> reservations) {
        return reservations.stream()
                .filter(reservation -> reservation.getSendStatus().equals(SendStatus.NOT_DONE))
                .map(ReservationResponseDto::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CalenderDateResponse> getReservationDate(Long userId) {
        return ReservationServiceUtils.findReservationAfterNow(reservationRepository, userId).stream()
                .map(reservation -> CalenderDateResponse.of(reservation.getSendDueDate()))
                .collect(Collectors.toList());
    }
}
