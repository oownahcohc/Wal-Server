package server.wal.domain.nextWal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.wal.domain.nextWal.NextWal;

public interface NextWalRepository extends JpaRepository<NextWal, Long>, NextWalRepositoryCustom {
}
