package server.wal.domain.nextWal.repository;

import server.wal.domain.common.enumerate.WalCategoryType;
import server.wal.domain.nextWal.NextWal;

import java.util.List;
import java.util.Set;

public interface NextWalRepositoryCustom {
    List<NextWal> findByUserId(Long userId);
    List<NextWal> findByCategoryTypesAndUserId(Set<WalCategoryType> categoryTypes, Long userId);
}
