package server.wal.app.reservation.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import server.wal.common.exception.custom.ConflictException;
import server.wal.common.exception.custom.NotFoundException;
import server.wal.domain.reservation.Reservation;
import server.wal.domain.reservation.repository.ReservationRepository;

import java.time.LocalDate;
import java.util.List;

import static server.wal.common.exception.ResponseResult.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationServiceUtils {

    static void checkExistsReservationByDate(ReservationRepository reservationRepository, LocalDate requestDate, Long userId) {
        if (reservationRepository.existsByDate(requestDate, userId)) {
            throw new ConflictException(String.format("이미 존재하는 예약 날짜 (%s) 입니다", requestDate), CONFLICT_RESERVATION_DATE_EXCEPTION);
        }
    }

    static void checkExistsByReservationId(ReservationRepository reservationRepository, Long reservationId) {
        if (!reservationRepository.existsByReservationId(reservationId)) {
            throw new NotFoundException(String.format("존재하지 않는 예약 왈소리 (%s) 입니다", reservationId), NOT_FOUND_RESERVATION_EXCEPTION);
        }
    }

    static void checkExistsByUserId(ReservationRepository reservationRepository, Long userId) {
        if (!reservationRepository.existsByUserId(userId)) {
            throw new NotFoundException("예약된 왈소리가 존재하지 않습니다", NOT_FOUND_RESERVATION_EXCEPTION);
        }
    }

    @NotNull
    public static Reservation findByReservationId(ReservationRepository reservationRepository, Long reservationId) {
        Reservation reservation = reservationRepository.findByReservationId(reservationId);
        if (reservation == null) {
            throw new NotFoundException(String.format("존재하지 않는 예약 왈소리 (%s) 입니다", reservationId), NOT_FOUND_RESERVATION_EXCEPTION);
        }
        return reservation;
    }
//
//    @NotNull
//    public static Reservation findTodayReservationByUserId(ReservationRepository reservationRepository, Long userId) {
//        Reservation reservation = reservationRepository.findTodayReservationByUserId(userId);
//        if (reservation == null) {
//            throw new NotFoundException(String.format("유저 (%s)가 오늘 예약한 왈소리는 없습니다", userId), NOT_FOUND_TODAY_RESERVATION_EXCEPTION);
//        }
//        return reservation;
//    }

    @NotNull
    public static List<Reservation> findAllByUserId(ReservationRepository reservationRepository, Long userId) {
        List<Reservation> reservations = reservationRepository.findByUserId(userId);
        if (reservations == null) {
            throw new NotFoundException("예약된 왈소리가 존재하지 않습니다", NOT_FOUND_RESERVATION_EXCEPTION);
        }
        return reservations;
    }

    @NotNull
    public static List<Reservation> findReservationAfterNow(ReservationRepository reservationRepository, Long userId) {
        List<Reservation> reservations = reservationRepository.findReservationAfterNow(userId);
        if (reservations == null) {
            throw new NotFoundException("예약된 왈소리가 존재하지 않습니다", NOT_FOUND_RESERVATION_AFTER_NOW_EXCEPTION);
        }
        return reservations;
    }

}
