package server.wal.domain.common.enumerate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum WalCategoryType {

    COMEDY("드립"),
    FUSS("주접"),
    COMFORT("위로"),
    YELL("꾸중"),
    RESERVATION("예약")
    ;

    private final String value;

}
