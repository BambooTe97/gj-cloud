package com.gj.cloud.base.work.user;

import lombok.Getter;

@Getter
public enum UserStatusEnum {
    USER_STATUS_ACTIVATED("1", "activated"),
    USER_STATUS_LOCKED("2", "locked"),
    USER_STATUS_DEPRECATED("3", "deprecated");

    private String code;
    private String statusDesc;

    UserStatusEnum(String code, String statusDesc) {
        this.code = code;
        this.statusDesc = statusDesc;
    }
}
