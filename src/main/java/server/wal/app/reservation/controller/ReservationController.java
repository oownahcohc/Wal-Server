package server.wal.app.reservation.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import server.wal.app.reservation.dto.request.AddReservationRequest;
import server.wal.app.reservation.dto.response.ReservationIdResponse;
import server.wal.app.reservation.service.ReservationService;
import server.wal.common.dto.ApiResponse;
import server.wal.common.exception.ResponseResult;
import server.wal.config.interceptor.Auth;
import server.wal.config.resolver.LoginUserId;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @ApiOperation("[인증] 왈소리 만들기 페이지 - 왈소리 예약하기")
    @Auth
    @PostMapping("/v1/reservation")
    public ApiResponse<ReservationIdResponse> registerReservation(@Valid @RequestBody AddReservationRequest request, @ApiIgnore @LoginUserId Long userId) {
        return ApiResponse.success(ResponseResult.SUCCESS_CREATED_RESERVATION, reservationService.registerReservation(request.toServiceDto(), userId));
    }

    @ApiOperation("[인증] 왈소리 히스토리 페이지 - 예약한 왈소리 삭제/취소 하기")
    @Auth
    @DeleteMapping("/v1/reservation/{reservationId}")
    public ApiResponse<ResponseResult> registerReservation(@PathVariable Long reservationId, @ApiIgnore @LoginUserId Long userId) {
        reservationService.deleteReservation(reservationId, userId);
        return ApiResponse.success(ResponseResult.SUCCESS_DELETE_RESERVATION);
    }

}
