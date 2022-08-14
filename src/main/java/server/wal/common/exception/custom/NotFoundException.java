package server.wal.common.exception.custom;

import server.wal.common.exception.ResponseResult;

public class NotFoundException extends WalException {

    public NotFoundException(String message, ResponseResult responseResult) {
        super(message, responseResult);
    }

    public NotFoundException(String message) {
        super(message, ResponseResult.NOT_FOUND_EXCEPTION);
    }

}
