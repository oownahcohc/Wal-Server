package server.wal.app.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.wal.app.auth.dto.request.LoginDto;
import server.wal.app.auth.service.AuthService;
import server.wal.app.user.service.UserService;
import server.wal.app.user.service.UserServiceUtils;
import server.wal.common.util.HttpHeaderUtils;
import server.wal.common.util.RandomNicknameProviderUtils;
import server.wal.domain.user.entity.User;
import server.wal.domain.user.entity.SocialType;
import server.wal.domain.user.repository.UserRepository;
import server.wal.external.client.kakao.KakaoApiClient;
import server.wal.external.client.kakao.dto.KakaoAuthResponse;

@Service
@RequiredArgsConstructor
public class KakaoAuthService implements AuthService {

    private static final SocialType SOCIAL_TYPE = SocialType.KAKAO;

    private final KakaoApiClient kakaoApiCaller;
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public User login(LoginDto request) {
        KakaoAuthResponse response = kakaoApiCaller.getKakaoUserProfile(HttpHeaderUtils.withBearerToken(request.getSocialAccessToken()));
        User user = UserServiceUtils.findUserBySocialIdAndSocialType(userRepository, response.getId(), SOCIAL_TYPE);
        if (user == null) { // 없으면 회원가입
            return userService.registerUser(request.toCreateUserDto(response.getId(), RandomNicknameProviderUtils.getRandomNickname(), request.getFcmToken()));
        }
        return user;
    }

}
