package server.wal.domain.todayWal.repository;

import server.wal.domain.common.enumerate.WalCategoryType;
import server.wal.domain.common.enumerate.WalTimeType;
import server.wal.domain.todayWal.entity.TodayWal;

import java.util.List;
import java.util.Set;

public interface TodayWalRepositoryCustom {
    boolean existsByReservationIdAndUserId(Long reservationId, Long userId);
    TodayWal findByReservationIdAndUserId(Long reservationId, Long userId);
    TodayWal findReservationContentsByUserId(Long userId);
    TodayWal findByTodayWalIdAndUserId(Long todayWalId, Long userId);
    List<TodayWal> findByUserId(Long userId);
    List<TodayWal> findByUserIds(List<Long> userIds, WalTimeType timeType);
    List<TodayWal> findByTimeTypesAndUserId(Set<WalTimeType> timeTypes, Long userId);
    List<TodayWal> findByCategoryTypesAndUserId(Set<WalCategoryType> categoryTypes, Long userId);
}
