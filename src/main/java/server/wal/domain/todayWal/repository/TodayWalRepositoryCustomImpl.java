package server.wal.domain.todayWal.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import server.wal.domain.common.enumerate.WalCategoryType;
import server.wal.domain.common.enumerate.WalTimeType;
import server.wal.domain.todayWal.entity.TodayWal;
import server.wal.domain.todayWal.entity.WalStatus;

import java.util.List;
import java.util.Set;

import static server.wal.domain.todayWal.entity.QTodayWal.*;

@RequiredArgsConstructor
public class TodayWalRepositoryCustomImpl implements TodayWalRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public boolean existsByReservationIdAndUserId(Long reservationId, Long userId) {
        return query
                .selectOne()
                .from(todayWal)
                .where(
                        todayWal.userId.eq(userId),
                        todayWal.reservationId.eq(reservationId),
                        todayWal.walStatus.eq(WalStatus.RESERVATION)
                ).fetchFirst() != null;
    }

    @Override
    public TodayWal findByReservationIdAndUserId(Long reservationId, Long userId) {
        return query
                .selectFrom(todayWal)
                .where(
                        todayWal.userId.eq(userId),
                        todayWal.reservationId.eq(reservationId),
                        todayWal.walStatus.eq(WalStatus.RESERVATION)
                ).fetchOne();
    }

    @Override
    public TodayWal findReservationContentsByUserId(Long userId) {
        return query
                .selectFrom(todayWal)
                .where(
                        todayWal.userId.eq(userId),
                        todayWal.timeType.eq(WalTimeType.RESERVATION)
                ).fetchOne();
    }

    @Override
    public TodayWal findByTodayWalIdAndUserId(Long todayWalId, Long userId) {
        return query
                .selectFrom(todayWal)
                .where(
                        todayWal.id.eq(todayWalId),
                        todayWal.userId.eq(userId)
                ).fetchOne();
    }

    @Override
    public List<TodayWal> findByUserId(Long userId) {
        return query
                .selectFrom(todayWal)
                .where(todayWal.userId.eq(userId))
                .fetch();
    }

    @Override
    public List<TodayWal> findByUserIds(List<Long> userIds, WalTimeType timeType) {
        return query
                .selectFrom(todayWal)
                .where(
                        todayWal.userId.in(userIds),
                        todayWal.timeType.eq(timeType)
                ).fetch();
    }

    @Override
    public List<TodayWal> findByTimeTypesAndUserId(Set<WalTimeType> timeTypes, Long userId) {
        return query
                .selectFrom(todayWal)
                .where(
                        todayWal.timeType.in(timeTypes),
                        todayWal.userId.eq(userId)
                ).fetch();
    }

    @Override
    public List<TodayWal> findByCategoryTypesAndUserId(Set<WalCategoryType> categoryTypes, Long userId) {
        return query
                .selectFrom(todayWal)
                .where(
                        todayWal.categoryType.in(categoryTypes),
                        todayWal.userId.eq(userId)
                ).fetch();
    }

}
