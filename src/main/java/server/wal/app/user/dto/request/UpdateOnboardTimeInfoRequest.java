package server.wal.app.user.dto.request;

import lombok.*;
import server.wal.domain.common.enumerate.WalTimeType;

import javax.validation.constraints.NotNull;
import java.util.Set;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateOnboardTimeInfoRequest {

    @NotNull(message = "{user.onboard.timeTypes.notNull}")
    private Set<WalTimeType> timeTypes;

}
