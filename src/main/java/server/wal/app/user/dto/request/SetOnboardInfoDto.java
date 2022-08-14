package server.wal.app.user.dto.request;

import lombok.*;
import server.wal.domain.common.enumerate.WalCategoryType;
import server.wal.domain.common.enumerate.WalTimeType;
import server.wal.domain.onboarding.entity.Onboarding;

import java.util.Set;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SetOnboardInfoDto {

    private String nickname;
    private Set<WalCategoryType> categoryTypes;
    private Set<WalTimeType> timeTypes;

    public static SetOnboardInfoDto of(String nickname, Set<WalCategoryType> categoryTypes, Set<WalTimeType> timeTypes) {
        return new SetOnboardInfoDto(nickname, categoryTypes, timeTypes);
    }

    public Onboarding toOnboardingEntity(Long userId) {
        Onboarding onboarding = Onboarding.newInstance(userId);
        onboarding.addCategories(categoryTypes);
        onboarding.addTimes(timeTypes);
        return onboarding;
    }

}
