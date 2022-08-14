package server.wal.common.dto;

import lombok.*;
import server.wal.common.exception.ResponseResult;
import server.wal.common.exception.StatusCode;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private StatusCode statusCode;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(StatusCode.OK, "성공", data);
    }

    public static <T> ApiResponse<T> success(ResponseResult responseResult) {
        return new ApiResponse<>(responseResult.getStatusCode(), responseResult.getMessage(), null);
    }

    public static <T> ApiResponse<T> success(ResponseResult responseResult, T data) {
        return new ApiResponse<>(responseResult.getStatusCode(), responseResult.getMessage(), data);
    }

    public static <T> ApiResponse<T> error(ResponseResult responseResult) {
        return new ApiResponse<>(responseResult.getStatusCode(), responseResult.getMessage(), null);
    }

    public static <T> ApiResponse<T> error(ResponseResult responseResult, String message) {
        return new ApiResponse<>(responseResult.getStatusCode(), message, null);
    }

}
