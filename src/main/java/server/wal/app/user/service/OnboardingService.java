package server.wal.app.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.wal.app.user.dto.request.UpdateOnboardCategoryInfoRequest;
import server.wal.app.user.dto.request.UpdateOnboardTimeInfoRequest;
import server.wal.app.user.dto.request.SetOnboardInfoDto;
import server.wal.app.user.dto.request.UpdateType;
import server.wal.app.user.dto.response.OnboardInfoResponse;
import server.wal.domain.onboarding.entity.Onboarding;
import server.wal.domain.onboarding.repository.OnboardingRepository;
import server.wal.domain.user.entity.User;
import server.wal.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class OnboardingService {

    private final WalSettingService walSettingService;

    private final UserRepository userRepository;
    private final OnboardingRepository onboardingRepository;

    @Transactional
    public OnboardInfoResponse setOnboardInfo(SetOnboardInfoDto requestDto, Long userId) {
        User user = UserServiceUtils.findUserById(userRepository, userId);
        user.changeNickname(requestDto.getNickname());

        Onboarding onboarding = onboardingRepository.save(requestDto.toOnboardingEntity(userId));
        user.setOnboardInfo(onboarding);

        walSettingService.setNextWals(onboarding.getCategoryTypes(), userId);
        walSettingService.setTodayWals(onboarding.getTimeTypes(), userId);

        return OnboardInfoResponse.from(user.getNickname());
    }

    @Transactional
    public void updateOnboardTimeInfo(UpdateOnboardTimeInfoRequest request, Long userId) {
        Onboarding onboard = OnboardingServiceUtils.findOnboardingByUserId(onboardingRepository, userId);
        walSettingService.updateWals(UpdateType.UPDATE_TIME, onboard, request.getTimeTypes(), null, userId);
        onboard.updateTimes(request.getTimeTypes());
    }

    @Transactional
    public void updateOnboardCategoryInfo(UpdateOnboardCategoryInfoRequest request, Long userId) {
        Onboarding onboard = OnboardingServiceUtils.findOnboardingByUserId(onboardingRepository, userId);
        walSettingService.updateWals(UpdateType.UPDATE_CATEGORY, onboard, null, request.getCategoryTypes(), userId);
        onboard.updateCategories(request.getCategoryTypes());
    }

}
