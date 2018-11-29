package com.seckill.dto;

/**
 * Created by zhangbin on 2018/11/20.
 */
//封装json结果
public class SeckillResault<T> {

    private boolean success;

    private T data;

    private String error;


    public SeckillResault(boolean success, T data) {
        this.success = success;
        this.data = data;
    }


    public SeckillResault(boolean success, String error) {
        this.error = error;
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
