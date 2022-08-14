package server.wal.app.user.dto.response;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class OnboardInfoResponse {

    private String nickname;

    private OnboardInfoResponse() {}

    private OnboardInfoResponse(String nickname) {
        this.nickname = nickname;
    }

    public static OnboardInfoResponse from(String nickname) {
        return new OnboardInfoResponse(nickname);
    }

}
