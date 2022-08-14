package server.wal.app.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.wal.app.user.dto.request.SetOnboardInfoDto;
import server.wal.app.user.dto.response.OnboardInfoResponse;
import server.wal.domain.common.enumerate.WalCategoryType;
import server.wal.domain.common.enumerate.WalTimeType;
import server.wal.domain.item.Item;
import server.wal.domain.item.repository.ItemRepository;
import server.wal.domain.nextWal.NextWal;
import server.wal.domain.nextWal.repository.NextWalRepository;
import server.wal.domain.onboarding.entity.Onboarding;
import server.wal.domain.onboarding.repository.OnboardingRepository;
import server.wal.domain.todayWal.entity.TodayWal;
import server.wal.domain.todayWal.entity.WalStatus;
import server.wal.domain.todayWal.repository.TodayWalRepository;
import server.wal.domain.user.entity.User;
import server.wal.domain.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSettingService {

    private final UserRepository userRepository;
    private final OnboardingRepository onboardingRepository;
    private final ItemRepository itemRepository;
    private final NextWalRepository nextWalRepository;
    private final TodayWalRepository todayWalRepository;

    @Transactional
    public OnboardInfoResponse setOnboardInfo(SetOnboardInfoDto requestDto, Long userId) {
        User user = UserServiceUtils.findUserById(userRepository, userId);
        user.changeNickname(requestDto.getNickname());

        Onboarding onboarding = onboardingRepository.save(requestDto.toOnboardingEntity(userId));
        user.setOnboardInfo(onboarding);

        setNextWals(onboarding, userId);
        setTodayWals(onboarding, userId);

        return OnboardInfoResponse.from(user.getNickname());
    }

    /**
     * 유저가 선택한 알람시간에 따라 유저가 선택한 카테고리의 아이템을
     * 오늘 보내줄 왈소리 테이블에 저장한다
     * @param onboarding
     * @param userId
     */
    private void setTodayWals(Onboarding onboarding, Long userId) {
        for (WalTimeType timeType : onboarding.getTimeTypes()) {
            List<NextWal> nextWals = nextWalRepository.findByUserId(userId);
            int random = (int) Math.floor(Math.random() * nextWals.size());
            NextWal randomNextWal = nextWals.get(random);

            Item nextItem = itemRepository.findItemByCategoryTypeAndNextItemId(randomNextWal.getCategoryType(), randomNextWal.getNextItemId());
            todayWalRepository.save(TodayWal.newInstance(userId, null, nextItem.getContents(), nextItem.getCategory().getCategoryType(), timeType, WalStatus.DEFAULT));
            // TODO count 쿼리 or size 비교
            double nextItemId = (nextItem.getCategoryItemNumber() + 1) % itemRepository.findAllByCategoryType(nextItem.getCategory().getCategoryType()).size();
            randomNextWal.updateNextItemId(nextItemId);
        }
    }

    /**
     * 유저가 선택한 카테고리 유형에 따라 해당 카테고리의 아이템을 세팅한다
     * @param onboarding
     * @param userId
     */
    private void setNextWals(Onboarding onboarding, Long userId) {
        for (WalCategoryType categoryType : onboarding.getCategoryTypes()) {
            // 유저가 선택한 카테고리 유형의 각 첫번째 아이템을 가져와 세팅해준다
            Item item = itemRepository.findFirstItemByCategoryType(categoryType);
            nextWalRepository.save(NextWal.newInstance(userId, categoryType, item.getCategoryItemNumber()));
        }
    }

}
