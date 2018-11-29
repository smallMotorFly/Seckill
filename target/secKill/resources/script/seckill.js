//存放主要交互js代码
//js 模块化
var seckill ={
    //封装秒杀相关ajax的url
    URL:{

    },
    validatePhone:function(phone) {
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        }else {
            return false;
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
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
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

            }

        }
    }
}