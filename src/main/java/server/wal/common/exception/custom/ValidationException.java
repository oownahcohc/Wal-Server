package server.wal.common.exception.custom;

import server.wal.common.exception.ResponseResult;

public class ValidationException extends WalException {

    public ValidationException(String message, ResponseResult responseResult) {
        super(message, responseResult);
    }

    public ValidationException(String message) {
        super(message, ResponseResult.VALIDATION_EXCEPTION);
    }

}
