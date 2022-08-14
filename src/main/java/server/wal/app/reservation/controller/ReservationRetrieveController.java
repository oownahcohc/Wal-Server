package server.wal.app.reservation.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import server.wal.app.reservation.dto.response.CalenderDateResponse;
import server.wal.app.reservation.dto.response.ReservationHistoryResponse;
import server.wal.app.reservation.service.ReservationRetrieveService;
import server.wal.common.dto.ApiResponse;
import server.wal.common.exception.ResponseResult;
import server.wal.config.interceptor.Auth;
import server.wal.config.resolver.LoginUserId;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationRetrieveController {

    private final ReservationRetrieveService reservationRetrieveService;

    @ApiOperation("[인증] 왈소리 히스토리 페이지 - 예약한 왈소리 히스토리 조회")
    @Auth
    @GetMapping("/v1/reservation")
    public ApiResponse<ReservationHistoryResponse> getReservationHistory(@ApiIgnore @LoginUserId Long userId) {
        ReservationHistoryResponse history = reservationRetrieveService.getReservationHistory(userId);
        return ApiResponse.success(ResponseResult.SUCCESS_RETRIEVE_RESERVATION_HISTORY, history);
    }

    @ApiOperation("[인증] 캘린더 페이지 - 예약한 왈소리 날짜 조회")
    @Auth
    @GetMapping("/v1/reservation/calender")
    public ApiResponse<List<CalenderDateResponse>> getReservationDate(@ApiIgnore @LoginUserId Long userId) {
        List<CalenderDateResponse> reservationDates = reservationRetrieveService.getReservationDate(userId);
        return ApiResponse.success(ResponseResult.SUCCESS_RETRIEVE_RESERVATION_DATE_ON_CALENDER, reservationDates);
    }

}
