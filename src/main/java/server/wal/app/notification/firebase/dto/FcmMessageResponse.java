package server.wal.app.notification.firebase.dto;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FcmMessageResponse {

    private boolean validate_only;
    private Message message;

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Message {
        private Notification notification;
        private String token;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Notification {
        private String title;
        private String body;
        private String image;
    }

}
