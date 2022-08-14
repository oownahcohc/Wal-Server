package server.wal.common.exception.custom;

import lombok.Getter;
import server.wal.common.exception.ResponseResult;

@Getter
public abstract class WalException extends RuntimeException {

    private final ResponseResult responseResult;

    public WalException(String message, ResponseResult responseResult) {
        super(message);
        this.responseResult = responseResult;
    }

    public int getStatus() {
        return responseResult.getStatus();
    }

}
