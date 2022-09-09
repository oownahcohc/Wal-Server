package server.wal.app.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import server.wal.app.user.service.OnboardingService;
import server.wal.app.user.service.WalSettingService;
import server.wal.domain.common.enumerate.WalCategoryType;
import server.wal.domain.common.enumerate.WalTimeType;
import server.wal.domain.onboarding.entity.Onboarding;
import server.wal.domain.onboarding.repository.OnboardingRepository;
import server.wal.domain.reservation.Reservation;
import server.wal.domain.reservation.repository.ReservationRepository;
import server.wal.domain.todayWal.entity.TodayWal;
import server.wal.domain.todayWal.entity.WalStatus;
import server.wal.domain.todayWal.repository.TodayWalRepository;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class WalScheduler {

    private final WalSettingService walSettingService;

    @Scheduled(cron = "0 0 0 * * *")
    public void updateWalAtNoonEveryday() {
        walSettingService.updateAllUserNextWalAndTodayWal();
    }

}
