package server.wal.domain.user.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SocialType {

    APPLE("애플"),
    KAKAO("카카오")
    ;

    private final String value;

}
