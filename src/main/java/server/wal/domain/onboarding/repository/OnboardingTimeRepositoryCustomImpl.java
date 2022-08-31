package server.wal.domain.onboarding.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import server.wal.domain.common.enumerate.WalTimeType;

import java.util.List;

import static server.wal.domain.onboarding.entity.QOnboardingTime.*;

@RequiredArgsConstructor
public class OnboardingTimeRepositoryCustomImpl implements OnboardingTimeRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public List<Long> findAllUserIdByTimeType(WalTimeType timeType) {
        return query
                .select(onboardingTime.onboarding.userId)
                .from(onboardingTime)
                .where(onboardingTime.time.eq(timeType))
                .fetch();
    }

}
