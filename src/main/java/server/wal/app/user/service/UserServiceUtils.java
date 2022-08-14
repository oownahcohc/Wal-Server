package server.wal.app.user.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import server.wal.common.exception.ResponseResult;
import server.wal.common.exception.custom.ConflictException;
import server.wal.common.exception.custom.NotFoundException;
import server.wal.domain.user.entity.User;
import server.wal.domain.user.entity.SocialType;
import server.wal.domain.user.repository.UserRepository;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserServiceUtils {

    static void validateNotExistsUser(UserRepository userRepository, String socialId, SocialType socialType) {
        if (userRepository.existsBySocialIdAndSocialType(socialId, socialType)) {
            throw new ConflictException(String.format("이미 존재하는 유저 (%s - %s) 입니다", socialId, socialType), ResponseResult.CONFLICT_USER_EXCEPTION);
        }
    }

    @NotNull
    public static User findUserById(UserRepository userRepository, Long userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new NotFoundException(String.format("존재하지 않는 유저 (%s) 입니다", userId), ResponseResult.NOT_FOUND_EXCEPTION);
        }
        return user;
    }

    public static User findUserBySocialIdAndSocialType(UserRepository userRepository, String socialId, SocialType socialType) {
        return userRepository.findUserBySocialIdAndSocialType(socialId, socialType);
    }

}
