package server.wal.domain.onboarding.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import server.wal.domain.onboarding.entity.Onboarding;

import static server.wal.domain.onboarding.entity.QOnboarding.*;

@RequiredArgsConstructor
public class OnboardingRepositoryCustomImpl implements OnboardingRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public Onboarding findOnboardByUserId(Long userId) {
        return query
                .selectFrom(onboarding)
                .where(onboarding.userId.eq(userId))
                .fetchOne();
    }

}
