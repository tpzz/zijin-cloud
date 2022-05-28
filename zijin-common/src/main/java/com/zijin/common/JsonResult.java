package com.zijin.common;

import com.zijin.common.enums.BaseEnum;
import com.zijin.common.exception.BaseException;

import java.io.Serializable;

/**
 * 自定义响应体结构
 */
public class JsonResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean success;

    private String code;

    private String msg;

    private Object data;

    public JsonResult() {
    }

    public JsonResult(boolean success, String code, String msg, Object data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public JsonResult success() {
        return new JsonResult(true, null, null, null);
    }

    public JsonResult success(String code, String msg, Object data) {
        return new JsonResult(true, code, msg, data);
    }

    public JsonResult fail() {
        return new JsonResult(false, null, null ,null);
    }

    public JsonResult fail(String code, String msg) {
        return new JsonResult(false, code, msg, null);
    }

    public JsonResult fail(BaseEnum baseEnum) {
        return new JsonResult(false, baseEnum.getCode(), baseEnum.getMsg(), null);
    }

    public JsonResult fail(BaseException baseException) {
        return new JsonResult(false, baseException.getCode(), baseException.getMsg(), null);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
