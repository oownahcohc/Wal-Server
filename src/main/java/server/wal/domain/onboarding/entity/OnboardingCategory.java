package server.wal.domain.onboarding.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.wal.domain.common.entity.AuditingTimeEntity;
import server.wal.domain.common.enumerate.WalCategoryType;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OnboardingCategory extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "onboarding_id", nullable = false)
    private Onboarding onboarding;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WalCategoryType category;

    private OnboardingCategory(Onboarding onboarding, WalCategoryType category) {
        this.onboarding = onboarding;
        this.category = category;
    }

    public static OnboardingCategory of(Onboarding onboarding, WalCategoryType category) {
        return new OnboardingCategory(onboarding, category);
    }

}
