package com.seckill.dao;

import com.seckill.entity.SuccessSecKill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by zhangbin on 2018/10/21.
 */

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration({"classpath:spring/spring-dao.xml"})


public class SuccessSecKillDaoTest {

    @Resource
    private SuccessSecKillDao successSecKillDao;

    @Test
    public void testInsertSuccesSeckill() throws Exception {
        /**
         *  第一次执行 insertCount=1
         *  第二次执行 inserCount=0 不允许重复秒杀
         */
        long id = 1001L;
        long phone = 17621191600L;
        int insertCount = successSecKillDao.insertSuccesSeckill(id,phone);
        System.out.println("insertCount=" + insertCount);
    }

    @Test
    public void testQueryByIdWithSecKill() throws Exception {
        long id = 1001L;
        long phone = 17621191600L;
        SuccessSecKill successSecKill = successSecKillDao.queryByIdWithSecKill(id,phone);
        System.out.println(successSecKill);
        System.out.println(successSecKill.getSeckill());
    }
}