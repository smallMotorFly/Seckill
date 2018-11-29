package com.seckill.service.impl;

import com.seckill.dao.SeckillDao;
import com.seckill.dao.SuccessSecKillDao;
import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.entity.SuccessSecKill;
import com.seckill.enums.SeckillStateEnum;
import com.seckill.exception.RepeatSeckillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;
import com.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangbin on 2018/10/22.
 */

//@Component @Service @Dao @Controller
@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //注入service依赖
    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessSecKillDao successSecKillDao;

    //混淆的字符.添加到加密字段,并MD5加密
    private final String slat = "SJKHFu9hshd98uBUHJKSHD*(**(*(#)@)#@LALDSM><><<>ZMXIOWQ";

    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,5);
    }

    @Override
    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = seckillDao.queryById(seckillId);
        if (seckill == null) {
            return new Exposer(false,seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();

        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false,nowTime.getTime(),startTime.getTime(),endTime.getTime());
        }
        //转化特定字符串过程,不可逆
        String md5 = getMd5(seckillId);

        return new Exposer(true,md5,seckillId);
    }

    private String getMd5(long seckillId) {
        String base = seckillId + "/" + slat;

        return DigestUtils.md5DigestAsHex(base.getBytes());
    }

    @Override
    @Transactional
    /**
     * 使用注解控制事物方法的优点
     * 1.开发团队达成一致约定,明确标注事务方法的编程
     * 2.保证事务方法的执行时间尽可能短,不要穿插其他的网络操作,RPC/HTTP请求或者剥离到事务方法外部
     * 3.不是所有的方法都需要事务,如只有一条修改操作,或者只读操作不需要事务控制.
     */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatSeckillException, SeckillCloseException {

        if (md5 == null || !md5.equals(getMd5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }
        //执行秒杀逻辑:减库存 + 记录购买行为



        try {

            Date nowTime = new Date();
            //减库存
            int updateCount = seckillDao.reduceNumber(seckillId,nowTime);

            if (updateCount <= 0) {
                //没更新到记录
                throw new SeckillCloseException("seckill is closed");
            } else {
                //记录购买行为
                int insertCount = successSecKillDao.insertSuccesSeckill(seckillId,userPhone);
                //唯一:seckillId,userPhone

                if (insertCount <= 0) {
                    //重复秒杀
                    throw new RepeatSeckillException("seckill repeated");
                }else {
                    //秒杀成功
                    SuccessSecKill successSecKill = successSecKillDao.queryByIdWithSecKill(seckillId,userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS,successSecKill);
                }
            }
        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatSeckillException e2) {
            throw e2;
        }catch (Exception e) {
            logger.error(e.getMessage(), e);

            throw new SeckillException("seckill inner error:" + e.getMessage());
        }

    }
}
