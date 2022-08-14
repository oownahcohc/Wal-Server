package server.wal.domain.user.entity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Embeddable
public class SocialInfo {

    @Column(name = "social_id", nullable = false, length = 200)
    private String socialId;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_type", nullable = false, length = 15)
    private SocialType socialType;

    private SocialInfo(String socialId, SocialType socialType) {
        this.socialId = socialId;
        this.socialType = socialType;
    }

    public static SocialInfo of(String socialId, SocialType socialType) {
        return new SocialInfo(socialId, socialType);
    }

}
