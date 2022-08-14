package server.wal.domain.todayWal.repository;

import server.wal.domain.todayWal.entity.TodayWal;

import java.util.List;

public interface TodayWalRepositoryCustom {
    boolean existsByReservationIdAndUserId(Long reservationId, Long userId);
    TodayWal findByReservationIdAndUserId(Long reservationId, Long userId);
    TodayWal findByTodayWalIdAndUserId(Long todayWalId, Long userId);
    List<TodayWal> findByUserId(Long userId);
}
