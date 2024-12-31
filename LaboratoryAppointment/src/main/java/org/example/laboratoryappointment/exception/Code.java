package org.example.laboratoryappointment.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Code {
    LOGIN_ERROR(401, "登录错误"),
    TOKEN_EXPIRED(402, "验证过期"),
    NO_PERMISSION(403, "没有权限"),
    MESSAGE_ERROR(404, "信息错误"),
    NO_USER(405, "用户不存在"),
    BAD_REQUEST(406, "请求错误");
    public static final int ERROR = 400;
    public static final int SUCCESS = 200;
    private final int number;
    private final String message;
}
