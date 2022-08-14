package server.wal.domain.reservation.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import server.wal.common.util.TimeUtils;
import server.wal.domain.reservation.Reservation;
import server.wal.domain.reservation.SendStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static server.wal.domain.reservation.QReservation.*;

@RequiredArgsConstructor
public class ReservationRepositoryCustomImpl implements ReservationRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public boolean existsByDate(LocalDate requestDate, Long userId) {
        return query
                .selectOne()
                .from(reservation)
                .where(
                        reservation.userId.eq(userId),
                        reservation.sendDueDate.eq(LocalDateTime.from(requestDate))
                ).fetchFirst() != null;
    }

    @Override
    public boolean existsByReservationId(Long reservationId) {
        return query
                .selectOne()
                .from(reservation)
                .where(reservation.id.eq(reservationId))
                .fetchFirst() != null;
    }

    @Override
    public boolean existsByUserId(Long userId) {
        return query
                .selectOne()
                .from(reservation)
                .where(reservation.userId.eq(userId))
                .fetchFirst() != null;
    }

    @Override
    public Reservation findByReservationId(Long reservationId) {
        return query
                .selectFrom(reservation)
                .where(reservation.id.eq(reservationId)).
                fetchOne();
    }

    @Override
    public List<Reservation> findByUserId(Long userId) {
        return query
                .selectFrom(reservation)
                .where(reservation.userId.eq(userId))
                .fetch();
    }

    @Override
    public List<Reservation> findIncompleteReservationByUserId(Long userId) {
        return query
                .selectFrom(reservation)
                .where(
                        reservation.userId.eq(userId),
                        reservation.sendStatus.eq(SendStatus.NOT_DONE)
                ).fetch();
    }

    @Override
    public List<Reservation> findCompleteReservationByUserId(Long userId) {
        return query
                .selectFrom(reservation)
                .where(
                        reservation.userId.eq(userId),
                        reservation.sendStatus.eq(SendStatus.DONE)
                ).fetch();
    }

    @Override
    public List<Reservation> findReservationAfterNow(Long userId) {
        return query
                .selectFrom(reservation)
                .where(
                        reservation.userId.eq(userId),
                        reservation.sendDueDate.after(TimeUtils.getNow())
                )
                .orderBy(reservation.sendDueDate.desc())
                .fetch();
    }

}
