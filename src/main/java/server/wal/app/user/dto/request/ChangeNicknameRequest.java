package server.wal.app.user.dto.request;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ToString
@Getter
public class ChangeNicknameRequest {

    @NotBlank(message = "{user.nickname.notBlank}")
    private String nickname;

    private ChangeNicknameRequest() {}

    private ChangeNicknameRequest(String nickname) {
        this.nickname = nickname;
    }

}
