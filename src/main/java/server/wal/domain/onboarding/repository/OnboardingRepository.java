package server.wal.domain.onboarding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.wal.domain.onboarding.entity.Onboarding;

public interface OnboardingRepository extends JpaRepository<Onboarding, Long> {
}
