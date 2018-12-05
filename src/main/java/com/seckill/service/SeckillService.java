package com.seckill.service;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.exception.RepeatSeckillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;

import java.util.List;

/**
 * 业务接口:站在使用者的角度设计接口
 * 三个方面:方法定义粒度,参数,返回类型(return 类型/异常)
 * Created by zhangbin on 2018/10/21.
 */
public interface SeckillService {

    /**
     * 查询所有秒杀记录
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个秒杀记录
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 秒杀开启时输出秒杀接口地址,否则输出系统时间和秒杀时间
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀,返回事件结果集
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException,RepeatSeckillException,SeckillCloseException;

    /**
     * 执行秒杀,返回事件结果集
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckillProduce(long seckillId, long userPhone, String md5);
}
