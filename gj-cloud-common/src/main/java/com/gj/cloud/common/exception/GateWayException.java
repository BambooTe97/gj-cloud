package com.gj.cloud.common.exception;

import com.gj.cloud.common.api.IErrorCode;
import lombok.Data;

/**
 * 网关异常类处理
 */
@Data
public class GateWayException extends RuntimeException {
    private long code;

    private String message;

    public GateWayException(IErrorCode iErrorCode) {
        this.code = iErrorCode.getCode();
        this.message = iErrorCode.getMessage();
    }
}
