package server.wal.domain.common.enumerate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import server.wal.common.model.EnumModel;
import server.wal.common.util.TimeUtils;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum WalTimeType implements EnumModel {

    MORNING("아침", TimeUtils.getMorning()),
    AFTERNOON("점심", TimeUtils.getAfternoon()),
    NIGHT("저녁", TimeUtils.getNight()),
    RESERVATION("예약", null),
    ;

    private final String value;
    private final LocalDateTime sendDueDate;

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getValue() {
        return value;
    }

    public LocalDateTime getTimeValue() {
        return sendDueDate;
    }

}
