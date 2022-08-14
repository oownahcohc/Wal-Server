package server.wal.domain.user.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.wal.app.user.dto.request.CreateUserDto;
import server.wal.domain.common.entity.AuditingTimeEntity;
import server.wal.domain.onboarding.entity.Onboarding;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private SocialInfo socialInfo;

    @Column(length = 50)
    private String nickname;

    @Column(nullable = false)
    private String fcmToken;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private UserStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "onboarding_id")
    private Onboarding onboarding;

    @Builder(access = AccessLevel.PACKAGE)
    private User(String socialId, SocialType socialType, String nickname, String fcmToken) {
        this.socialInfo = SocialInfo.of(socialId, socialType);
        this.nickname = nickname;
        this.fcmToken = fcmToken;
        this.status = UserStatus.ACTIVE;
    }

    public static User newInstance(CreateUserDto request) {
        return User.builder()
                .socialId(request.getSocialId())
                .socialType(request.getSocialType())
                .nickname(request.getNickname())
                .fcmToken(request.getFcmToken())
                .build();
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setOnboardInfo(Onboarding onboarding) {
        this.onboarding = onboarding;
    }

}
