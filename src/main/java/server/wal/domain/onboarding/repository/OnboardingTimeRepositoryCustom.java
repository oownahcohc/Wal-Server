package server.wal.domain.onboarding.repository;

import server.wal.domain.common.enumerate.WalTimeType;
import server.wal.domain.onboarding.entity.Onboarding;

import java.util.List;

public interface OnboardingTimeRepositoryCustom {
    List<Long> findAllUserIdByTimeType(WalTimeType timeType);
}
