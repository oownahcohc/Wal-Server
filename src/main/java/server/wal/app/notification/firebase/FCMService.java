package server.wal.app.notification.firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import server.wal.app.notification.firebase.dto.FcmMessageResponse;
import server.wal.app.user.service.UserServiceUtils;
import server.wal.domain.reservation.repository.ReservationRepository;
import server.wal.domain.todayWal.repository.TodayWalRepository;
import server.wal.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class FCMService {

    private final UserRepository userRepository;
    private final TodayWalRepository todayWalRepository;
    private final ReservationRepository reservationRepository;

    public void sendDefaultWal(Long userId) {
        String targetUserToken = UserServiceUtils.findUserById(userRepository, userId).getFcmToken();
        Message message = Message.builder()
                .putData("title", "오늘의 왈소리가 도착했어요!")
                .putData("content", "투데이 왈에서 컨텐쓰 가져와야지 ㅇㅇ")
                .setToken(targetUserToken)
                .build();
        sendMessage(message, false);
    }

    public void sendReservationWal(Long userId) {
        String targetUserToken = UserServiceUtils.findUserById(userRepository, userId).getFcmToken();
        Message message = Message.builder()
                .putData("title", "오늘의 왈소리가 도착했어요!")
                .putData("content", "투데이 왈에서 컨텐쓰 가져와야지 ㅇㅇ")
                .setToken(targetUserToken)
                .build();
        sendMessage(message, true);
    }

    private void sendMessage(Message message, boolean isReserve){
        FirebaseMessaging.getInstance().sendAsync(message);
        if (isReserve) {
//            reservationRepository.
        }
    }

}
