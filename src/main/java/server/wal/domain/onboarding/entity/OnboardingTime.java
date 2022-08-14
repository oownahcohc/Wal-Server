package server.wal.domain.onboarding.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.wal.domain.common.enumerate.WalTimeType;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OnboardingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "onboarding_id", nullable = false)
    private Onboarding onboarding;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WalTimeType time;

    private OnboardingTime(Onboarding onboarding, WalTimeType time) {
        this.onboarding = onboarding;
        this.time = time;
    }

    public static OnboardingTime of(Onboarding onboarding, WalTimeType time) {
        return new OnboardingTime(onboarding, time);
    }

}
