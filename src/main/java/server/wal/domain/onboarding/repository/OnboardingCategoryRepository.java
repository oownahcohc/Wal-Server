package server.wal.domain.onboarding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.wal.domain.onboarding.entity.OnboardingCategory;

public interface OnboardingCategoryRepository extends JpaRepository<OnboardingCategory, Long> {
}
