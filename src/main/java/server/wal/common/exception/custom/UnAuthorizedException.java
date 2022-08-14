package server.wal.common.exception.custom;

import server.wal.common.exception.ResponseResult;

public class UnAuthorizedException extends WalException {

    public UnAuthorizedException(String message, ResponseResult responseResult) {
        super(message, responseResult);
    }

    public UnAuthorizedException(String message) {
        super(message, ResponseResult.UNAUTHORIZED_EXCEPTION);
    }

}
