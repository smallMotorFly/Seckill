//存放主要交互js代码
//js 模块化
var seckill ={
    //封装秒杀相关ajax的url
    URL:{
        now:function() {
            return '/seckill/time/now';
        }
    },
    validatePhone:function(phone) {
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        }else {
            return false;
        }
    },
    countdown: function (seckillId,nowTime,startTime,endTime) {
        var seckillBox = $('#seckill-box');

        //时间判断
        if (nowTime > endTime) {
            // 秒杀结束
            seckillBox.html('秒杀结束!');
        }else if (nowTime < startTime) {
            //秒杀未开始:倒计时,事件绑定
            var killTime = new Date(startTime + 1000);
            seckillBox.countdown(killTime, function (event) {
                //时间格式
                var format = event.strftime('秒杀倒计时:%D天 %H时 %M分 %S秒');
                seckillBox.html(format);
                //时间倒计时完成回调
            }).on('finish.countdown',function(){
                //获取秒杀地址,控制实现逻辑.执行秒杀
                
            })
        }else  {
            //正在秒杀
        }
    },
    //详情秒杀逻辑
    detail:{
        //详情页初始化
        init:function(params) {
            //手机验证和登录,计时交互
            //规划交互流程
            //1.验证:在cokies中查手机号
            var killPhone = $.cookie('killPhone');

            //alert(killPhone);

            //2.验证手机号
            if (!seckill.validatePhone(killPhone)) {
                // 绑定phone
                //alert("--------");
                var killPhoneModal = $('#killPhoneModal');
                //显示弹出层
                killPhoneModal.modal({
                    show:true,//显示弹出层
                    backdrop:'static',//禁止位置关闭
                    keyboard:false,//关闭键盘事件
                });

                $('#killPhoneBtn').click(function(){
                    var inputPhone = $('#killPhoneKey').val();
                    console.log(inputPhone);
                    if (seckill.validatePhone(inputPhone)) {
                        //电话写入cookies
                        $.cookie('killPhone',inputPhone,{expires:7,path:'/seckill'});
                        //刷新页面
                        window.location.reload();
                    }else {
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误</label>').show(300);
                    }
                });
            }else {
                //已经登录

                //计时交互
                var startTime = params['startTime'];
                var endTime = params['endTime'];
                var seckillId = params['seckillId'];

                $.get(seckill.URL.now(), {},function(result){
                    if (result && result['success']) {
                        var nowTime = result['data'];
                        //计时时间判断
                        seckill.countdown(seckillId,nowTime,startTime,endTime);
                    }else {
                        console.log('result:' + result);
                    }
                });

            }

        }
    }
}