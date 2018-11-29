package com.seckill.web;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.dto.SeckillResault;
import com.seckill.entity.Seckill;
import com.seckill.enums.SeckillStateEnum;
import com.seckill.exception.RepeatSeckillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by zhangbin on 2018/11/18.
 */

@Controller //@Service @Component
@RequestMapping("/seckill")//url:/模块/资源/{id}/功能
public class SeckillController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model) {
        //获取列表页
        List<Seckill> list = seckillService.getSeckillList();

        model.addAttribute("list",list);

        return "list";//WEB-INF/jsp/"list".jsp
    }

    @RequestMapping(value = "/{seckillId}/detail",method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {

        if (seckillId == null) {
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getById(seckillId);

        if (seckill == null) {
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill",seckill);
        return "detail";
    }
    //ajax json
    @RequestMapping(value = "/seckillId/exposer",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResault<Exposer> exposer(Long seckillId) {
        SeckillResault<Exposer> resault;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            resault = new SeckillResault<Exposer>(true,exposer);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            resault = new SeckillResault<Exposer>(false,e.getMessage());
        }
        return resault;
    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResault<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,@PathVariable("md5") String md5,@CookieValue(value = "killPhone",required = false) Long phone) {
       if (phone == null){
           return new SeckillResault<SeckillExecution>(false,"未注册");
       }

        SeckillResault<SeckillExecution> resault;
        try {
            SeckillExecution executionException = seckillService.executeSeckill(seckillId,phone,md5);

            return new SeckillResault<SeckillExecution>(true,executionException);
        } catch (RepeatSeckillException e){
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_SECKILL);

            return new SeckillResault<SeckillExecution>(false,execution);
        } catch (SeckillCloseException e){
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.END);

            return new SeckillResault<SeckillExecution>(false,execution);
        } catch (Exception e){
            logger.error(e.getMessage(),e);

            SeckillExecution execution = new SeckillExecution(seckillId,SeckillStateEnum.INNER_ERROR);
            return new SeckillResault<SeckillExecution>(false,execution);
        }
    }

    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    @ResponseBody
    public SeckillResault<Long> time() {
        Date now = new Date();

        return new SeckillResault(true,now.getTime());

    }


}
