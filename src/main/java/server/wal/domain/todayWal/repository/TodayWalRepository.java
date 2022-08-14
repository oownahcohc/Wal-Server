package server.wal.domain.todayWal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.wal.domain.todayWal.entity.TodayWal;

public interface TodayWalRepository extends JpaRepository<TodayWal, Long>, TodayWalRepositoryCustom {
}
