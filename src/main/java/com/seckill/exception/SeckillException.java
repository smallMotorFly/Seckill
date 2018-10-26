package com.seckill.exception;

/**
 * 秒杀相关业务异常
 * Created by zhangbin on 2018/10/21.
 */
public class SeckillException extends RuntimeException {
    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeckillException(String message) {
        super(message);
    }
}
