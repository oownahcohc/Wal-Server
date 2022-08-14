package server.wal.domain.nextWal.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import server.wal.domain.nextWal.NextWal;

import java.util.List;

import static server.wal.domain.nextWal.QNextWal.*;

@RequiredArgsConstructor
public class NextWalRepositoryCustomImpl implements NextWalRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public List<NextWal> findByUserId(Long userId) {
        return query
                .selectFrom(nextWal)
                .where(nextWal.userId.eq(userId))
                .fetch();
    }

}
