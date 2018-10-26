package com.seckill.exception;

/**
 * 重复秒杀异常(运行期异常)
 * Created by zhangbin on 2018/10/21.
 */
public class RepeatSeckillException extends  SeckillException {

    public RepeatSeckillException(String message) {
        super(message);
    }

    public RepeatSeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
