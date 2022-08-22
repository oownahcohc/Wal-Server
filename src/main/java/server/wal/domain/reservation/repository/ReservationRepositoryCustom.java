package server.wal.domain.reservation.repository;

import server.wal.domain.reservation.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepositoryCustom {
    boolean existsByDate(LocalDate requestDate, Long userId);
    boolean existsByReservationId(Long reservationId);
    boolean existsByUserId(Long userId);
    Reservation findTodayReservationByUserId(Long userId);
    Reservation findByReservationId(Long reservationId);
    List<Reservation> findByUserId(Long userId);
    List<Reservation> findIncompleteReservationByUserId(Long userId);
    List<Reservation> findCompleteReservationByUserId(Long userId);
    List<Reservation> findReservationAfterNow(Long userId);
}
