package server.wal.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.wal.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
}
