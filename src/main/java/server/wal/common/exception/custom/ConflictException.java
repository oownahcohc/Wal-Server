package server.wal.common.exception.custom;

import server.wal.common.exception.ResponseResult;

public class ConflictException extends WalException {

    public ConflictException(String message, ResponseResult responseResult) {
        super(message, responseResult);
    }

    public ConflictException(String message) {
        super(message, ResponseResult.CONFLICT_EXCEPTION);
    }

}
