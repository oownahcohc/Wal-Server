package server.wal.app.user.dto.request;

import lombok.*;
import server.wal.domain.common.enumerate.WalCategoryType;
import server.wal.domain.common.enumerate.WalTimeType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SetOnboardInfoRequest {

    @NotBlank(message = "{user.nickname.notBlank}")
    private String nickname;

    @NotNull(message = "{user.onboard.categoryTypes.notNull}")
    private Set<WalCategoryType> categoryTypes;

    @NotNull(message = "{user.onboard.timeTypes.notNull}")
    private Set<WalTimeType> timeTypes;

    @Builder(builderMethodName = "testBuilder", access = AccessLevel.PUBLIC)
    private SetOnboardInfoRequest(String nickname, Set<WalCategoryType> categoryTypes, Set<WalTimeType> timeTypes) {
        this.nickname = nickname;
        this.categoryTypes = categoryTypes;
        this.timeTypes = timeTypes;
    }

    public SetOnboardInfoDto toServiceDto() {
        return SetOnboardInfoDto.of(nickname, categoryTypes, timeTypes);
    }

}
