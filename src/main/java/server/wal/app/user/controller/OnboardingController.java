package server.wal.app.user.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import server.wal.app.user.dto.request.UpdateOnboardCategoryInfoRequest;
import server.wal.app.user.dto.request.UpdateOnboardTimeInfoRequest;
import server.wal.app.user.dto.request.SetOnboardInfoRequest;
import server.wal.app.user.dto.response.OnboardInfoResponse;
import server.wal.app.user.service.OnboardingService;
import server.wal.common.dto.ApiResponse;
import server.wal.common.exception.ResponseResult;
import server.wal.config.interceptor.Auth;
import server.wal.config.resolver.LoginUserId;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

import static server.wal.common.exception.ResponseResult.*;

@RestController
@RequiredArgsConstructor
public class OnboardingController {

    private final OnboardingService onboardingService;

    @ApiOperation("[인증] 온보딩 페이지 - 유저의 온보딩 정보 설정")
    @Auth
    @PostMapping("/v1/user/onboarding")
    public ApiResponse<OnboardInfoResponse> setOnboardInfo(@Valid @RequestBody SetOnboardInfoRequest request, @ApiIgnore @LoginUserId Long userId) {
        return ApiResponse.success(SUCCESS_CREATED_ONBOARD, onboardingService.setOnboardInfo(request.toServiceDto(), userId));
    }

    @ApiOperation("[인증] 설정 페이지 - 유저의 알람시간 온보딩 정보 수정")
    @Auth
    @PostMapping("/v1/user/onboarding/time")
    public ApiResponse<ResponseResult> updateOnboardTimeInfo(@Valid @RequestBody UpdateOnboardTimeInfoRequest request, @ApiIgnore @LoginUserId Long userId) {
        onboardingService.updateOnboardTimeInfo(request, userId);
        return ApiResponse.success(SUCCESS_CREATED_UPDATE_ONBOARD);
    }

    @ApiOperation("[인증] 설정 페이지 - 유저의 왈소리 유형 온보딩 정보 수정")
    @Auth
    @PostMapping("/v1/user/onboarding/category")
    public ApiResponse<ResponseResult> updateOnboardCategoryInfo(@Valid @RequestBody UpdateOnboardCategoryInfoRequest request, @ApiIgnore @LoginUserId Long userId) {
        onboardingService.updateOnboardCategoryInfo(request, userId);
        return ApiResponse.success(SUCCESS_CREATED_UPDATE_ONBOARD);
    }

}
