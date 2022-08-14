package server.wal.domain.onboarding.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.wal.domain.common.entity.AuditingTimeEntity;
import server.wal.domain.common.enumerate.WalCategoryType;
import server.wal.domain.common.enumerate.WalTimeType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Onboarding extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @OneToMany(mappedBy = "onboarding", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OnboardingTime> times = new ArrayList<>();

    @OneToMany(mappedBy = "onboarding", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OnboardingCategory> categories = new ArrayList<>();

    private Onboarding(Long userId) {
        this.userId = userId;
    }

    public static Onboarding newInstance(Long userId) {
        return new Onboarding(userId);
    }

    /**
     * OnboardingTime 연관관계 메서드 & 비즈니스 로직
     */
    public void addTimes(Set<WalTimeType> timeTypes) {
        for (WalTimeType timeType : timeTypes) {
            this.addTime(timeType);
        }
    }

    private void addTime(WalTimeType timeType) {
        OnboardingTime onboardingTime = OnboardingTime.of(this, timeType);
        this.times.add(onboardingTime);
    }

    public void updateTimes(Set<WalTimeType> newTimeTypes) {
        this.times.removeIf(currentTimeType -> !newTimeTypes.contains(currentTimeType.getTime()));
        Set<WalTimeType> hasTimeTypes = getTimeTypes();
        addTimes(newTimeTypes.stream()
                .filter(time -> !hasTimeTypes.contains(time))
                .collect(Collectors.toSet()));
    }

    public Set<WalTimeType> getTimeTypes() {
        return this.times.stream()
                .map(OnboardingTime::getTime)
                .collect(Collectors.toSet());
    }

    /**
     * OnboardingCategory 연관관계 메서드 & 비즈니스 로직
     */
    public void addCategories(Set<WalCategoryType> categoryTypes) {
        for (WalCategoryType timeType : categoryTypes) {
            this.addTime(timeType);
        }
    }

    private void addTime(WalCategoryType categoryType) {
        OnboardingCategory onboardingCategory = OnboardingCategory.of(this, categoryType);
        this.categories.add(onboardingCategory);
    }

    public void updateCategories(Set<WalCategoryType> newCategoryTypes) {
        this.categories.removeIf(currentCategoryType -> !newCategoryTypes.contains(currentCategoryType.getCategory()));
        Set<WalCategoryType> hasCategoryTypes = getCategoryTypes();
        addCategories(newCategoryTypes.stream()
                .filter(category -> !hasCategoryTypes.contains(category))
                .collect(Collectors.toSet()));
    }

    public Set<WalCategoryType> getCategoryTypes() {
        return this.categories.stream()
                .map(OnboardingCategory::getCategory)
                .collect(Collectors.toSet());
    }

}
