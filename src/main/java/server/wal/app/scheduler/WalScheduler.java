package server.wal.app.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import server.wal.app.user.service.UserSettingService;
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
public class WalScheduler {

    private final UserSettingService userSettingService;

    private final OnboardingRepository onboardingRepository;
    private final ReservationRepository reservationRepository;
    private final TodayWalRepository todayWalRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void updateWalAtNoonEveryday() {
        todayWalRepository.deleteAll();
        updateAllUserNextWalAndTodayWal();
    }

    private void updateAllUserNextWalAndTodayWal() {
        List<Onboarding> onboards = onboardingRepository.findAll(Sort.by(Sort.Direction.ASC, "userId"));
        for (Onboarding onboard : onboards) {

            Long userId = onboard.getUserId();
            userSettingService.setTodayWals(onboard, userId);

            Reservation todayReservation = reservationRepository.findTodayReservationByUserId(userId);
            if (todayReservation != null) {
                todayWalRepository.save(TodayWal.newInstance(
                        userId, todayReservation.getId(),
                        todayReservation.getContents(),
                        WalCategoryType.RESERVATION,
                        WalTimeType.RESERVATION,
                        WalStatus.RESERVATION
                ));
            }

        }
    }

}
