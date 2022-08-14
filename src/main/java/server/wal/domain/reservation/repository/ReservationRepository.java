package server.wal.domain.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.wal.domain.reservation.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepositoryCustom {
}
