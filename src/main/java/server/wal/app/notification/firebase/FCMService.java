package server.wal.app.notification.firebase;

import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.wal.app.user.service.UserServiceUtils;
import server.wal.domain.reservation.repository.ReservationRepository;
import server.wal.domain.todayWal.entity.TodayWal;
import server.wal.domain.todayWal.repository.TodayWalRepository;
import server.wal.domain.user.repository.UserRepository;

import static server.wal.app.notification.firebase.constants.WalFCMConstants.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FCMService {

    private final UserRepository userRepository;
    private final TodayWalRepository todayWalRepository;
    private final ReservationRepository reservationRepository;

    public void sendDefaultWal(Long userId, String contents) {
        String targetUserToken = UserServiceUtils.findUserById(userRepository, userId).getFcmToken();
        Message message = Message.builder()
                .putData(TITLE_KEY, TITLE_VALUE)
                .putData(CONTENT_KEY, contents)
                .setToken(targetUserToken)
                .build();
        sendMessage(message, SendType.DEFAULT, null);
    }

    public void sendReservationWal(Long userId) {
        String targetUserToken = UserServiceUtils.findUserById(userRepository, userId).getFcmToken();
        TodayWal reservationTodayWal = todayWalRepository.findReservationContentsByUserId(userId);
        String reservationContents = reservationTodayWal.getContents();
        Long reservationId = reservationTodayWal.getReservationId();
        Message message = Message.builder()
                .putData(TITLE_KEY, TITLE_VALUE)
                .putData(CONTENT_KEY, reservationContents)
                .setToken(targetUserToken)
                .build();
        sendMessage(message, SendType.RESERVATION, reservationId);
    }

    private void sendMessage(Message message, SendType sendType, @Nullable Long reservationId) {
        ApiFuture<String> sendMessage = FirebaseMessaging.getInstance().sendAsync(message);
        if (sendMessage.isDone()) {
            log.info("???? ????????? ?????? ?????? ???");
            if (sendType == SendType.RESERVATION) {
                reservationRepository.findByReservationId(reservationId).updateSendStatus();
            }
        } else if (sendMessage.isCancelled()) {
            log.info("??? ????????? ?????? ?????? ???");
        }
    }

}
