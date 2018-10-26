-- 数据库初始化脚本

-- 创建数据库
CREATE DATABASE seckill;

-- 使用数据库
USE seckill;

-- 创建秒杀库存表
CREATE  TABLE seckill(
  `seckill_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
  `name` VARCHAR(120) NOT NULL COMMENT '商品名称',
  `number` INT NOT NULL COMMENT '库存数量',
  `start_time` TIMESTAMP NOT NULL COMMENT '秒杀开始时间',
  `end_time` TIMESTAMP NOT NULL COMMENT '秒杀结束时间',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (seckill_id),
  KEY idx_start_time(start_time),
  KEY idx_end_time(end_time),
  KEY idx_create_time(create_time)
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET = utf8 COMMENT = '秒杀库存表';

-- 初始化数据

INSERT INTO
  seckill(name,number,start_time,end_time)
VALUES
  ('2000元秒杀macPro',100,'2015-11-01 00:00:00','2015-11-02 00:00:00'),
  ('1000元秒杀iphone6s',200,'2015-11-01 00:00:00','2015-11-02 00:00:00'),
  ('500元秒杀iPad',300,'2015-11-01 00:00:00','2015-11-02 00:00:00'),
  ('300元秒杀小米4',400,'2015-11-01 00:00:00','2015-11-02 00:00:00'),
  ('200元秒杀红米note',500,'2015-11-01 00:00:00','2015-11-02 00:00:00'),
  ('100元秒杀诺基亚',600,'2015-11-01 00:00:00','2015-11-02 00:00:00');





-- 秒杀成功明细表
-- 用户登录认证相关的信息
CREATE TABLE success_seckill(
  `seckill_id` BIGINT NOT NULL COMMENT '秒杀商品id',
  `user_phone` BIGINT NOT NULL  COMMENT '用户手机号',
  `state` BIGINT NOT NULL DEFAULT -1 COMMENT '状态标识:-1:无效 0:成功 1:已付款 2:已确认 3:已准备 4:已发货 5:待配送 6:配送中 7:已送达 8:已收货 9:确认收货 10:已评价',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (seckill_id,user_phone), /*联合主键:防止重复秒杀*/
  KEY idx_create_time(create_time)
)ENGINE = InnoDB  DEFAULT CHARSET = utf8 COMMENT = '秒杀成功明细表';



-- 链接数据库控制台
-- mysql -uroot -p

-- 为什么手写DDL

-- 记录每次上线的DDL的修改
-- 上线v1.1
ALTER TABLE  seckill
DROP INDEX idx_create_time,
ADD INDEX idx_c_S(start_time,create_time);

-- 上线v1.2
-- ddl