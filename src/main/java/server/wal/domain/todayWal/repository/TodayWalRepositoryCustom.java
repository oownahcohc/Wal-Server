package server.wal.domain.todayWal.repository;

import server.wal.domain.common.enumerate.WalTimeType;
import server.wal.domain.todayWal.entity.TodayWal;

import java.util.List;

public interface TodayWalRepositoryCustom {
    boolean existsByReservationIdAndUserId(Long reservationId, Long userId);
    TodayWal findByReservationIdAndUserId(Long reservationId, Long userId);
    TodayWal findByTodayWalIdAndUserId(Long todayWalId, Long userId);
    List<TodayWal> findByUserId(Long userId);
    List<TodayWal> findContentsByUserIds(List<Long> userIds, WalTimeType timeType);
    TodayWal findReservationContentsByUserId(Long userId);
}
