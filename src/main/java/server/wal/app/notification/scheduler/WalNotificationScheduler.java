package server.wal.app.notification.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import server.wal.app.notification.firebase.FCMService;
import server.wal.domain.common.enumerate.WalTimeType;
import server.wal.domain.onboarding.repository.OnboardingTimeRepository;
import server.wal.domain.todayWal.entity.TodayWal;
import server.wal.domain.todayWal.repository.TodayWalRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WalNotificationScheduler {

    private static final WalTimeType MORNING_TYPE = WalTimeType.MORNING;
    private static final WalTimeType AFTERNOON_TYPE = WalTimeType.AFTERNOON;
    private static final WalTimeType NIGHT_TYPE = WalTimeType.NIGHT;

    private final FCMService fcmService;

    private final OnboardingTimeRepository onboardingTimeRepository;
    private final TodayWalRepository todayWalRepository;

    @Scheduled(cron = "0 0 8 * * *")
    public void morningNotification() {
        executePushNotificationByTimeType(MORNING_TYPE);
    }

    @Scheduled(cron = "0 0 14 * * *")
    public void afternoonNotification() {
        executePushNotificationByTimeType(AFTERNOON_TYPE);
    }

    @Scheduled(cron = "0 0 20 * * *")
    public void nightNotification() {
        executePushNotificationByTimeType(NIGHT_TYPE);
    }

    @Transactional
    protected void executePushNotificationByTimeType(WalTimeType timeType) { // TODO 쿼리 수 줄이기 or 비동기 처리 or 멀티스레딩
        List<Long> userIds = onboardingTimeRepository.findAllUserIdByTimeType(timeType);
        List<TodayWal> todayWals = todayWalRepository.findByUserIds(userIds, timeType);
        todayWals.forEach(todayWal -> fcmService.sendDefaultWal(todayWal.getUserId(), todayWal.getContents()));
    }

}
