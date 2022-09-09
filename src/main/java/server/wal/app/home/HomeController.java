package server.wal.app.home;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import server.wal.app.home.dto.response.HomeResponse;
import server.wal.common.dto.ApiResponse;
import server.wal.common.exception.ResponseResult;
import server.wal.config.interceptor.Auth;
import server.wal.config.resolver.LoginUserId;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

import static server.wal.common.exception.ResponseResult.*;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @ApiOperation("[인증] 홈 페이지 - 오늘 받을 왈소리 조회")
    @Auth
    @GetMapping("/v1/home")
    public ApiResponse<List<HomeResponse>> getMainHome(@ApiIgnore @LoginUserId Long userId) {
        return ApiResponse.success(SUCCESS_RETRIEVE_HOME, homeService.getMainHome(userId));
    }

    @ApiOperation("[인증] 홈 페이지 - 유저가 오늘의 왈소리 확인 후 show status 변경")
    @Auth
    @PatchMapping("/v1/home/{todayWalId}")
    public ApiResponse<ResponseResult> updateShowStatus(@PathVariable Long todayWalId, @ApiIgnore @LoginUserId Long userId) {
        homeService.updateShowStatus(todayWalId, userId);
        return ApiResponse.success(SUCCESS_CREATED_UPDATE_HOME_SHOW_STATUS);
    }

}
