package server.wal.domain.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.wal.domain.item.Item;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {
}
