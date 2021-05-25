package com.example.webflux.beans;

import java.io.Serializable;

public class Result<T> implements Serializable {

    private static final String DEFAULT_SUCCESS_MSG = "操作成功";
    private static final String DEFAULT_FAIL_MSG = "操作失败";

    public static final Integer OK_STATUS = 0;
    public static final Integer FAIL_STATUS = 1;

    public static final int ERROR_NOT_LOGIN = 2;
    public static final int ERROR_NO_AUTH = 3;

    /**
     * 返回结果状态
     */
    private int status = OK_STATUS;
    /**
     * 失败码code
     */
    private int code = 0;
    /**
     * 返回结果描述信息
     */
    private String message;

    /**
     * 记录操作参数，写操作日志
     */
    private String detailMsg;

    /**
     * 扩展数据
     */
    private T data;

    public Result(){
        this.status = OK_STATUS;
        this.code = 0;
        this.message = DEFAULT_SUCCESS_MSG;
    }

    public Result(int status, String message, T data) {
        this.status = status;
        this.code = 0;
        this.message = message;
        this.data = data;
    }

    public Result(int status, int code, String message, T data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> ok( ) {
        return ok(null);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(OK_STATUS, DEFAULT_SUCCESS_MSG, data);
    }

    public static <T> Result<T> fail( ) {
        return fail(null);
    }

    public static <T> Result<T> fail(String errMsg){
        return new Result<>(FAIL_STATUS,errMsg,null);
    }

    public static <T> Result<T> fail(int errCode, String errMsg) {
        return new Result<>(FAIL_STATUS, errCode, errMsg, null);
    }

    public static <T> Result<T> failForNotLogin(){
        return new Result<>(FAIL_STATUS,ERROR_NOT_LOGIN,"您未登陆，请登录后再操作！",null);
    }

    public static <T> Result<T> failForNoAuth(){
        return new Result<>(FAIL_STATUS,ERROR_NO_AUTH,"您没有分配权限，请查实！",null);
    }

    public static <T> Result<T> fail(T data) {
        return new Result<>(FAIL_STATUS, DEFAULT_FAIL_MSG, data);
    }

    public Result code(int code){
        this.code = code;
        return this;
    }

    public Result msg(String message) {
        this.message = message;
        return this;
    }

    public Result detailMsg(String message) {
        this.detailMsg = message;
        return this;
    }

    public Result data(T data) {
        this.data = data;
        return this;
    }

    public int getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getDetailMsg() {
        return detailMsg;
    }

    public T getData() {
        return data;
    }

    public Boolean isOk(){
        return OK_STATUS == status;
    }
}
