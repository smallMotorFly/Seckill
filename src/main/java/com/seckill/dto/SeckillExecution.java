package com.seckill.dto;

import com.seckill.entity.SuccessSecKill;
import com.seckill.enums.SeckillStateEnum;

/**
 * 秒杀执行后的数据
 * Created by zhangbin on 2018/10/21.
 */
public class SeckillExecution {

    private long seckillId;

    //秒杀状态
    private int state;

    //状态标识
    private String stateInfo;

    //秒杀成功对象
    private SuccessSecKill successSecKill;

    public SeckillExecution(long seckillId, SeckillStateEnum stateEnum, SuccessSecKill successSecKill) {
        this.seckillId = seckillId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.successSecKill = successSecKill;
    }

    public SeckillExecution(long seckillId, SeckillStateEnum stateEnum) {
        this.seckillId = seckillId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessSecKill getSuccessSecKill() {
        return successSecKill;
    }

    public void setSuccessSecKill(SuccessSecKill successSecKill) {
        this.successSecKill = successSecKill;
    }
}
