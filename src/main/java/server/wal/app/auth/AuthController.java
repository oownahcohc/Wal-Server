package server.wal.app.auth;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import server.wal.app.auth.dto.request.LoginRequest;
import server.wal.app.auth.dto.request.TokenRequest;
import server.wal.app.auth.dto.response.LoginResponse;
import server.wal.app.auth.dto.response.TokenResponse;
import server.wal.app.auth.service.AuthService;
import server.wal.app.auth.provider.AuthServiceProvider;
import server.wal.app.auth.service.CreateTokenService;
import server.wal.common.dto.ApiResponse;
import server.wal.common.exception.ResponseResult;
import server.wal.domain.user.entity.User;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceProvider authServiceProvider;
    private final CreateTokenService createTokenService;

    @ApiOperation("회원가입 및 로그인 페이지 - 회원가입 및 로그인")
    @PostMapping("/v1/auth/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthService authService = authServiceProvider.getAuthService(request.getSocialType());
        User user = authService.login(request.toServiceDto());
        TokenResponse tokenInfo = createTokenService.createTokenInfo(user.getId());
        return ApiResponse.success(LoginResponse.of(user, tokenInfo));
    }

    @ApiOperation("토큰 만료 시 엑세스 토큰 재발급 요청")
    @PostMapping("/v1/auth/reissue")
    public ApiResponse<TokenResponse> reissueToken(@Valid @RequestBody TokenRequest request) {
        return ApiResponse.success(ResponseResult.SUCCESS_CREATED_REISSUE_TOKEN, createTokenService.reissueToken(request));
    }

}
