package server.wal.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import server.wal.domain.user.entity.User;
import server.wal.domain.user.entity.SocialType;

import static server.wal.domain.user.entity.QUser.*;

@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public User findUserById(Long userId) {
        return query
                .selectFrom(user)
                .where(user.id.eq(userId))
                .fetchOne();
    }

    @Override
    public User findUserBySocialIdAndSocialType(String socialId, SocialType socialType) {
        return query
                .selectFrom(user)
                .where(
                        user.socialInfo.socialId.eq(socialId),
                        user.socialInfo.socialType.eq(socialType)
                ).fetchOne();
    }

    @Override
    public boolean existsBySocialIdAndSocialType(String socialId, SocialType socialType) {
        return query
                .selectOne()
                .from(user)
                .where(
                        user.socialInfo.socialId.eq(socialId),
                        user.socialInfo.socialType.eq(socialType)
                ).fetchFirst() != null;
    }

}
