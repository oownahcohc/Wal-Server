package server.wal.common.exception.custom;

import server.wal.common.exception.ResponseResult;

public class InternalServerException extends WalException {

    public InternalServerException(String message, ResponseResult responseResult) {
        super(message, responseResult);
    }

    public InternalServerException(String message) {
        super(message, ResponseResult.INTERNAL_SERVER_EXCEPTION);
    }

}
