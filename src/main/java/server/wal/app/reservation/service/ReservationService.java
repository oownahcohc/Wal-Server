package server.wal.app.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.wal.app.reservation.dto.request.AddReservationDto;
import server.wal.app.reservation.dto.response.ReservationIdResponse;
import server.wal.common.util.TimeUtils;
import server.wal.domain.common.enumerate.WalCategoryType;
import server.wal.domain.common.enumerate.WalTimeType;
import server.wal.domain.reservation.Reservation;
import server.wal.domain.reservation.repository.ReservationRepository;
import server.wal.domain.todayWal.entity.TodayWal;
import server.wal.domain.todayWal.entity.WalStatus;
import server.wal.domain.todayWal.repository.TodayWalRepository;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TodayWalRepository todayWalRepository;

    @Transactional
    public ReservationIdResponse registerReservation(AddReservationDto requestDto, Long userId) {
        ReservationServiceUtils.checkExistsReservationByDate(reservationRepository, requestDto.getLocalDate(), userId);
        Reservation reservation = reservationRepository.save(Reservation.newInstance(userId, requestDto));

        if (requestDto.getLocalDate().isEqual(TimeUtils.NOW)) {
            todayWalRepository.save(TodayWal.newInstance(
                    userId, reservation.getId(),
                    requestDto.getContents(),
                    WalCategoryType.RESERVATION,
                    WalTimeType.RESERVATION,
                    WalStatus.RESERVATION
            ));
        }
        // TODO 메세지 큐 작업
        return ReservationIdResponse.from(reservation.getId());
    }

    @Transactional
    public void deleteReservation(Long reservationId, Long userId) {
        // TODO 예약된 큐에서 제거
        deleteReservationHistory(reservationId, userId);
    }

    @Transactional
    public void deleteReservationHistory(Long reservationId, Long userId) {
        ReservationServiceUtils.checkExistsByReservationId(reservationRepository, reservationId);
        reservationRepository.deleteById(reservationId);
        // today wal 에 있으면 제거
        TodayWal todayWal = todayWalRepository.findByReservationIdAndUserId(reservationId, userId);
        if (todayWal != null) {
            todayWalRepository.delete(todayWal);
        }
    }

}
