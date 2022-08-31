package server.wal.app.user.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import server.wal.app.user.dto.request.ChangeNicknameRequest;
import server.wal.app.user.dto.request.SetOnboardInfoRequest;
import server.wal.app.user.dto.response.NicknameResponse;
import server.wal.app.user.dto.response.OnboardInfoResponse;
import server.wal.app.user.service.UserService;
import server.wal.app.user.service.OnboardingService;
import server.wal.common.dto.ApiResponse;
import server.wal.common.exception.ResponseResult;
import server.wal.config.interceptor.Auth;
import server.wal.config.resolver.LoginUserId;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation("[인증] 마이페이지 - 유저 닉네임 변경")
    @Auth
    @PostMapping("/v1/user/me/nickname")
    public ApiResponse<NicknameResponse> changeNickname(@Valid @RequestBody ChangeNicknameRequest request, @ApiIgnore @LoginUserId Long userId) {
        return ApiResponse.success(ResponseResult.SUCCESS_CREATED_UPDATE_NICKNAME, userService.changeNickname(request.getNickname(), userId));
    }

}
