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

    private static final WalTimeType MORNING = WalTimeType.MORNING;
    private static final WalTimeType AFTERNOON = WalTimeType.AFTERNOON;
    private static final WalTimeType NIGHT = WalTimeType.NIGHT;

    private final FCMService fcmService;

    private final OnboardingTimeRepository onboardingTimeRepository;
    private final TodayWalRepository todayWalRepository;

    @Scheduled(cron = "0 0 8 * * *")
    public void morningNotification() {
        executePushNotificationByTimeType(MORNING);
    }

    @Scheduled(cron = "0 0 14 * * *")
    public void afternoonNotification() {
        executePushNotificationByTimeType(AFTERNOON);
    }

    @Scheduled(cron = "0 0 20 * * *")
    public void nightNotification() {
        executePushNotificationByTimeType(NIGHT);
    }

    @Transactional
    protected void executePushNotificationByTimeType(WalTimeType timeType) { // TODO 쿼리 수 줄이기 or 비동기 처리 or 멀티스레딩
        List<Long> morningUserIds = onboardingTimeRepository.findAllUserIdByTimeType(timeType);
        List<TodayWal> todayMorningWals = todayWalRepository.findContentsByUserIds(morningUserIds, timeType);
        todayMorningWals.forEach(todayWal -> fcmService.sendDefaultWal(todayWal.getUserId(), todayWal.getContents()));
    }

}
