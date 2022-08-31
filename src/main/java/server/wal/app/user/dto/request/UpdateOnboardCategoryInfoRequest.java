package server.wal.app.user.dto.request;

import lombok.*;
import server.wal.domain.common.enumerate.WalCategoryType;

import javax.validation.constraints.NotNull;
import java.util.Set;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateOnboardCategoryInfoRequest {

    @NotNull(message = "{user.onboard.categoryTypes.notNull}")
    private Set<WalCategoryType> categoryTypes;

}
