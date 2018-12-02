package com.seckill.dao.cache;

import com.seckill.dao.SeckillDao;
import com.seckill.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by zhangbin on 2018/12/2.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {

    private long id = 1006;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void testGetSeckill() throws Exception {
        Seckill seckill = redisDao.getSeckill(id);

        if (seckill == null) {
            seckill = seckillDao.queryById(id);
            if (seckill != null) {

                String result = redisDao.putSeckill(seckill);
                System.out.print(result);

                seckill = redisDao.getSeckill(id);
                System.out.print(seckill);

            }else {
                System.out.print(seckill);
            }
        }
    }

    @Test
    public void testPutSeckill() throws Exception {

    }
}