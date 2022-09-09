package server.wal.app.user.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import server.wal.app.user.dto.request.UpdateType;
import server.wal.common.util.TimeUtils;
import server.wal.domain.common.enumerate.WalCategoryType;
import server.wal.domain.common.enumerate.WalTimeType;
import server.wal.domain.item.Item;
import server.wal.domain.item.repository.ItemRepository;
import server.wal.domain.nextWal.NextWal;
import server.wal.domain.nextWal.repository.NextWalRepository;
import server.wal.domain.onboarding.entity.Onboarding;
import server.wal.domain.onboarding.repository.OnboardingRepository;
import server.wal.domain.reservation.Reservation;
import server.wal.domain.reservation.repository.ReservationRepository;
import server.wal.domain.todayWal.entity.TodayWal;
import server.wal.domain.todayWal.entity.WalStatus;
import server.wal.domain.todayWal.repository.TodayWalRepository;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalSettingService {

    private final TodayWalRepository todayWalRepository;
    private final NextWalRepository nextWalRepository;
    private final ItemRepository itemRepository;
    private final OnboardingRepository onboardingRepository;
    private final ReservationRepository reservationRepository;

    /**
     * 유저가 선택한 알람시간에 따라 유저가 선택한 카테고리의 아이템을
     * 오늘 보내줄 왈소리 테이블에 저장한다
     * @param timeTypes 선택된 알람 시간대
     * @param userId 유저의 ID
     */
    public void setTodayWals(Set<WalTimeType> timeTypes, Long userId) {
        for (WalTimeType timeType : timeTypes) {
            NextWal randomNextWal = getRandomNextWal(userId);
            Item nextItem = getNextItem(randomNextWal);
            todayWalRepository.save(TodayWal.newInstance(userId, null, nextItem.getContents(), nextItem.getCategory().getCategoryType(), timeType, WalStatus.DEFAULT));
            updateNextWalToNextItemId(randomNextWal, nextItem);
        }
    }

    private void updateNextWalToNextItemId(NextWal randomNextWal, Item nextItem) {
        double nextItemId = (nextItem.getCategoryItemNumber() + 1) % itemRepository.findAllByCategoryType(nextItem.getCategory().getCategoryType()).size(); // TODO count 쿼리 or size 비교
        randomNextWal.updateNextItemId(nextItemId);
    }

    private Item getNextItem(NextWal randomNextWal) {
        return itemRepository.findItemByCategoryTypeAndNextItemId(randomNextWal.getCategoryType(), randomNextWal.getNextItemId());
    }

    private NextWal getRandomNextWal(Long userId) {
        List<NextWal> nextWals = nextWalRepository.findByUserId(userId);
        int random = (int) Math.floor(Math.random() * nextWals.size());
        return nextWals.get(random);
    }

    /**
     * 유저가 선택한 카테고리 유형에 따라 해당 카테고리의 아이템을 세팅한다
     * @param categoryTypes 선택한 왈소리 카테고리
     * @param userId 유저의 ID
     */
    public void setNextWals(Set<WalCategoryType> categoryTypes, Long userId) {
        for (WalCategoryType categoryType : categoryTypes) {
            // 유저가 선택한 카테고리 유형의 각 첫번째 아이템을 가져와 세팅해준다
            Item item = itemRepository.findFirstItemByCategoryType(categoryType);
            nextWalRepository.save(NextWal.newInstance(userId, categoryType, item.getCategoryItemNumber()));
        }
    }

    public void updateWals(UpdateType updateType, Onboarding onboarding, @Nullable Set<WalTimeType> newTimeTypes,
                                @Nullable Set<WalCategoryType> newCategoryTypes, Long userId) {
        switch (updateType) {
            case UPDATE_TIME:
                assert newTimeTypes != null;
                Set<WalTimeType> currentTimeTypes = onboarding.getTimeTypes();

                Set<WalTimeType> cancelWalTimeTypes = extractWalTimeTypes(currentTimeTypes, newTimeTypes);
                Set<WalTimeType> addWalTimeTypes = extractWalTimeTypes(newTimeTypes, currentTimeTypes);

                if (!cancelWalTimeTypes.isEmpty()) {
                    todayWalRepository.deleteAllInBatch(todayWalRepository.findByTimeTypesAndUserId(cancelWalTimeTypes, userId));
                }
                if (!addWalTimeTypes.isEmpty()) {
                    setTodayWals(addWalTimeTypes, userId);
                }

                break;
            case UPDATE_CATEGORY:
                assert newCategoryTypes != null;
                Set<WalCategoryType> currentCategoryTypes = onboarding.getCategoryTypes();

                Set<WalCategoryType> addCategoryTypes = extractWalCategoryTypes(newCategoryTypes, currentCategoryTypes);
                if (!addCategoryTypes.isEmpty()) { // 추가된 카테고리가 있으면
                    setNextWals(addCategoryTypes, userId);
                }

                Set<WalCategoryType> cancelCategoryTypes = extractWalCategoryTypes(currentCategoryTypes, newCategoryTypes);
                if (!cancelCategoryTypes.isEmpty()) { // 삭제된 카테고리가 있으면
                    nextWalRepository.deleteAllInBatch(nextWalRepository.findByCategoryTypesAndUserId(cancelCategoryTypes, userId));
                    Set<TodayWal> cancelTodayWals = todayWalRepository.findByCategoryTypesAndUserId(cancelCategoryTypes, userId).stream()
                            .map(todayWal -> todayWal.getTimeType().getTimeValue().isAfter(TimeUtils.getNow()) ? todayWal : null)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toSet());
                    if (!cancelTodayWals.isEmpty()) { // 전송 예정인 삭제된 카테고리의 왈소리가 있는 경우
                        todayWalRepository.deleteAllInBatch(cancelTodayWals);
                        setTodayWals(cancelTodayWals.stream()
                                .map(TodayWal::getTimeType)
                                .collect(Collectors.toSet()), userId);
                    }
                }
                break;
        }
    }

    @NotNull
    private Set<WalCategoryType> extractWalCategoryTypes(Set<WalCategoryType> masterCategoryTypes, @NotNull Set<WalCategoryType> slaveCategoryTypes) {
        return masterCategoryTypes.stream()
                .filter(masterCategoryType -> !slaveCategoryTypes.contains(masterCategoryType))
                .collect(Collectors.toSet());
    }

    /**
     * 원래 설정된 시간대와 바뀐 시간대를 비교해 param 순서에 따라 추가된 시간대와 취소된 시간대를 추출하고<br>
     * 현재 시간과 비교해 현재시간 이후에 해당하는 시간대를 반환한다
     * @param masterTimeTypes 포함 여부를 체크할 주체가 되는 timeTypes
     * @param slaveTimeTypes master 의 타입을 포함하는지 체크할 timeTypes
     * @return slave 시간이 master 시간을 포함하지 않으면서 현재 시간 이후인 timeTypes
     */
    @NotNull
    private Set<WalTimeType> extractWalTimeTypes(Set<WalTimeType> masterTimeTypes, @NotNull Set<WalTimeType> slaveTimeTypes) {
        return masterTimeTypes.stream()
                .filter(masterTimeType -> !slaveTimeTypes.contains(masterTimeType))
                .map(this::extractAfterNow)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private WalTimeType extractAfterNow(WalTimeType timeType) {
        return timeType.getTimeValue().isAfter(TimeUtils.getNow()) ? timeType : null;
    }

    /**
     * 매일 00시 유저에게 보낼 TodayWal 테이블 업데이트를 진행한다
     */
    public void updateAllUserNextWalAndTodayWal() {
        todayWalRepository.deleteAllInBatch();

        List<Onboarding> onboards = onboardingRepository.findAll(Sort.by(Sort.Direction.ASC, "userId"));
        for (Onboarding onboard : onboards) {

            Long userId = onboard.getUserId();
            setTodayWals(onboard.getTimeTypes(), userId);

            Reservation todayReservation = reservationRepository.findTodayReservationByUserId(userId);
            if (todayReservation != null) {
                todayWalRepository.save(TodayWal.newInstance(userId, todayReservation.getId(), todayReservation.getContents(),
                        WalCategoryType.RESERVATION, WalTimeType.RESERVATION, WalStatus.RESERVATION
                ));
            }

        }
    }

}
