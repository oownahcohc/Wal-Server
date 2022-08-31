package server.wal.domain.onboarding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.wal.domain.onboarding.entity.OnboardingTime;

public interface OnboardingTimeRepository extends JpaRepository<OnboardingTime, Long>, OnboardingTimeRepositoryCustom {
}
