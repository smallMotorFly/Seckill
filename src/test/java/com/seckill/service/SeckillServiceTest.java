package com.seckill.service;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.exception.RepeatSeckillException;
import com.seckill.exception.SeckillCloseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zhangbin on 2018/11/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"
})
public class SeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());



    @Autowired
    private SeckillService seckillService;

    @Test
    public void testGetSeckillList() throws Exception {
        List<Seckill>list = seckillService.getSeckillList();
        logger.info("list={}",list);
    }

    @Test
    public void testGetById() throws Exception {
        long id = 1011;
        Seckill seckill = seckillService.getById(id);

        logger.info("seckill={}",seckill);
    }

    @Test
    public void testExportSeckillUrl() throws Exception {
        long id = 1011;

        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.info("exposer={}",exposer);

    }

    @Test
    public void testExecuteSeckill() throws Exception {
        long id = 1011;
        long phone = 17621191601L;

        String md5 = "803b8bf1357650f27fdb2225295c28a6";

        try {
            SeckillExecution execution = seckillService.executeSeckill(id,phone,md5);

            logger.info("result={}",execution);
        } catch (RepeatSeckillException e) {
            logger.error(e.getMessage());
        } catch (SeckillCloseException e) {
            logger.error(e.getMessage());
        }
    }

    //综合秒杀流程
    @Test
    public void testLogic() throws Exception {
        long id = 1006;
        Exposer exposer = seckillService.exportSeckillUrl(id);

        if (exposer.isExposed()) {
            logger.info("exposer={}",exposer);
            long phone = 17621191601L;

            String md5 = exposer.getMd5();

            try {
                SeckillExecution execution = seckillService.executeSeckill(id,phone,md5);

                logger.info("result={}",execution);
            } catch (RepeatSeckillException e) {
                logger.error(e.getMessage());
            } catch (SeckillCloseException e) {
                logger.error(e.getMessage());
            }
        }else  {//秒杀未开启
            logger.warn("exposer={}",exposer);

        }
    }
    //测试存储过程 秒杀
    @Test
    public void executeSeckillProcedure() {
        long seckillId = 1015;
        long phone = 17621191603L;

        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if (exposer.isExposed()) {
            String md5 = exposer.getMd5();
            SeckillExecution execution = seckillService.executeSeckillProduce(seckillId,phone,md5);
            logger.info(execution.getStateInfo());
        }
    }

}