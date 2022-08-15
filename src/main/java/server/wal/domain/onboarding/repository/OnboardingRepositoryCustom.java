package server.wal.domain.onboarding.repository;

import server.wal.domain.onboarding.entity.Onboarding;

public interface OnboardingRepositoryCustom {
    Onboarding findOnboardByUserId(Long userId);
}
