package server.wal.app.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.wal.app.auth.dto.request.LoginDto;
import server.wal.app.auth.service.AuthService;
import server.wal.app.user.service.UserService;
import server.wal.app.user.service.UserServiceUtils;
import server.wal.common.util.RandomNicknameProviderUtils;
import server.wal.domain.user.entity.User;
import server.wal.domain.user.entity.SocialType;
import server.wal.domain.user.repository.UserRepository;
import server.wal.external.client.apple.AppleTokenDecoder;

@Service
@RequiredArgsConstructor
public class AppleAuthService implements AuthService {

    private static final SocialType SOCIAL_TYPE = SocialType.APPLE;

    private final AppleTokenDecoder appleTokenDecoder;
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public User login(LoginDto request) {
        String socialId = appleTokenDecoder.getSocialIdFromIdToken(request.getSocialAccessToken());
        User user = UserServiceUtils.findUserBySocialIdAndSocialType(userRepository, socialId, SOCIAL_TYPE);
        if (user == null) {
            return userService.registerUser(request.toCreateUserDto(socialId, RandomNicknameProviderUtils.getRandomNickname(), request.getFcmToken()));
        }
        return user;
    }

}