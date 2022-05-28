package com.zijin.common.enums;

/**
 * 响应结果枚举
 */
public enum ResultEnum implements BaseEnum{

    SUCCESS("2000", "请求成功"),
    FAIL("4000", "请求失败"),
    AUTH_ERROR("4001", "用户认证失败");

    private final String code;

    private final String msg;

    ResultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
