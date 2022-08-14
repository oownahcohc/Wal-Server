package server.wal.app.auth.dto.request;

import lombok.*;
import server.wal.app.user.dto.request.CreateUserDto;
import server.wal.domain.user.entity.SocialType;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginDto {

    private String socialAccessToken;
    private SocialType socialType;
    private String fcmToken;

    public static LoginDto of(String socialAccessToken, SocialType socialType, String fcmToken) {
        return new LoginDto(socialAccessToken, socialType, fcmToken);
    }

    public CreateUserDto toCreateUserDto(String socialId, String nickname, String fcmToken) {
        return CreateUserDto.of(nickname, socialId, socialType, fcmToken);
    }

}