package com.zijin.common.exception;

import com.zijin.common.enums.ResultEnum;

/**
 * 基础异常类
 */
public class BaseException extends RuntimeException{

    private String code;

    private String msg;

    public BaseException() {
        super();
    }

    public BaseException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseException(ResultEnum baseEnum) {
        this.code = baseEnum.getCode();
        this.msg = baseEnum.getMsg();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
