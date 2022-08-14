package server.wal.domain.nextWal.repository;

import server.wal.domain.nextWal.NextWal;

import java.util.List;

public interface NextWalRepositoryCustom {
    List<NextWal> findByUserId(Long userId);
}
