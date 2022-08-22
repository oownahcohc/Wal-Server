package server.wal.domain.user.repository;

import server.wal.domain.user.entity.User;
import server.wal.domain.user.entity.SocialType;

public interface UserRepositoryCustom {
    User findUserById(Long userId);
    User findUserBySocialIdAndSocialType(String socialId, SocialType socialType);
    boolean existsBySocialIdAndSocialType(String socialId, SocialType socialType);
}
