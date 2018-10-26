package com.seckill.dao;

import com.seckill.entity.SuccessSecKill;
import org.apache.ibatis.annotations.Param;

/**
 * Created by zhangbin on 2018/10/15.
 */
public interface SuccessSecKillDao {

    /**
     * 插入购买明细,可过滤重复
     * @param seckillId
     * @param userPhone
     * @return
     */
    int insertSuccesSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    /**
     * 根据id查询 successKill并携带秒杀产品对象实体
     * @param seckillId
     * @param userPhone
     * @return
     */
    SuccessSecKill queryByIdWithSecKill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
}
