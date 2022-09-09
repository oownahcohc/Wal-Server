package server.wal.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import server.wal.app.user.dto.request.CreateUserDto;
import server.wal.app.user.dto.request.SetOnboardInfoRequest;
import server.wal.app.user.service.OnboardingService;
import server.wal.app.user.service.UserService;
import server.wal.domain.common.enumerate.WalCategoryType;
import server.wal.domain.common.enumerate.WalTimeType;
import server.wal.domain.nextWal.repository.NextWalRepository;
import server.wal.domain.onboarding.entity.Onboarding;
import server.wal.domain.onboarding.repository.OnboardingCategoryRepository;
import server.wal.domain.onboarding.repository.OnboardingRepository;
import server.wal.domain.onboarding.repository.OnboardingTimeRepository;
import server.wal.domain.todayWal.repository.TodayWalRepository;
import server.wal.domain.user.entity.SocialType;
import server.wal.domain.user.entity.User;
import server.wal.domain.user.repository.UserRepository;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static server.wal.domain.common.enumerate.WalCategoryType.*;
import static server.wal.domain.common.enumerate.WalTimeType.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private OnboardingService onboardingService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OnboardingRepository onboardingRepository;

    @Autowired
    private TodayWalRepository todayWalRepository;

    @Autowired
    private NextWalRepository nextWalRepository;

    @Autowired
    private OnboardingTimeRepository onboardingTimeRepository;

    @Autowired
    private OnboardingCategoryRepository onboardingCategoryRepository;

    @AfterEach
    void cleanUp() {
        nextWalRepository.deleteAllInBatch();
        todayWalRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
        onboardingTimeRepository.deleteAllInBatch();
        onboardingCategoryRepository.deleteAllInBatch();
        onboardingRepository.deleteAllInBatch();
    }

    @Test
    @Transactional
    void 새로운_온보딩_정보_정상_등록() {
        // given
        Set<WalCategoryType> categories = Set.of(COMEDY, FUSS, COMFORT, YELL);
        Set<WalTimeType> times = Set.of(MORNING, AFTERNOON, NIGHT);

        CreateUserDto createUserDto = CreateUserDto.of("왈뿡이", "SocialId", SocialType.KAKAO, "FcmToken");
        User save = userRepository.save(User.newInstance(createUserDto));
        SetOnboardInfoRequest request = SetOnboardInfoRequest.testBuilder()
                .nickname("새로운 닉네임")
                .categoryTypes(categories)
                .timeTypes(times)
                .build();

        // when
        onboardingService.setOnboardInfo(request.toServiceDto(), save.getId());

        // then
        Onboarding onboard = onboardingRepository.findOnboardByUserId(save.getId());
        assertAll(
                () -> assertThat(onboard).isNotNull(),
                () -> assertEquals(onboard.getUserId(), save.getId()),
                () -> assertEquals(request.getCategoryTypes(), onboard.getCategoryTypes()),
                () -> assertEquals(request.getTimeTypes(), onboard.getTimeTypes())
        );
    }

}
