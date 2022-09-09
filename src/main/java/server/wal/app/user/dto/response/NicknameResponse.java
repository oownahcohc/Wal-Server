package server.wal.app.user.dto.response;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class NicknameResponse {

    private String nickname;

    private NicknameResponse() {}

    private NicknameResponse(String nickname) {
        this.nickname = nickname;
    }

    public static NicknameResponse from(String nickname) {
        return new NicknameResponse(nickname);
    }

}
