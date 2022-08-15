package server.wal.app.user.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import server.wal.common.exception.ResponseResult;
import server.wal.common.exception.custom.NotFoundException;
import server.wal.domain.onboarding.entity.Onboarding;
import server.wal.domain.onboarding.repository.OnboardingRepository;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OnboardingServiceUtils {

    public static Onboarding findOnboardingByUserId(OnboardingRepository onboardingRepository, Long userId) {
        Onboarding onboarding = onboardingRepository.findOnboardByUserId(userId);
        if (onboarding == null) {
            throw new NotFoundException(String.format("유저 (%s)의 온보딩 정보가 존재하지 않습니다", userId), ResponseResult.NOT_FOUND_ONBOARDING_EXCEPTION);
        }
        return onboarding;
     }

}
