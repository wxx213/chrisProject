package com.mydeo.seckilldemo.controller;

/**
 * @Author: Chris he
 * @Date: 2020/7/31 17:42
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import com.mydeo.seckilldemo.mq.MQSender;
import com.mydeo.seckilldemo.result.ResultVo;
import com.mydeo.seckilldemo.service.MiaoshaService;
import com.mydeo.seckilldemo.service.RedisService;
import com.mydeo.seckilldemo.util.ToolUtil;
import com.mydeo.seckilldemo.util.param.ParameterUtil;
import com.mydeo.seckilldemo.util.redis.RedisUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


@RequestMapping("seckill")
@RestController
@Component
@DependsOn("springContextHolder")
public class RedisRabbitmqController implements InitializingBean {

    @Autowired
    private MiaoshaService miaoshaService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private MQSender mQSender;

    @Autowired
    private  RedisUtil redisUtil;

    //内存标记，减少redis访问
    private HashMap<String, Boolean> localOverMap =  new HashMap<String, Boolean>();


    /**
     * 系统初始化时把商品库存加入到缓存中
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //查询库存数量
        List<Map<String, Object>> stockList = miaoshaService.queryAllGoodStock();
        System.out.println("系统初始化："+stockList);
        if(stockList == null){
            return;
        }
        for(Map<String, Object> m : stockList) {
            //将库存加载到redis中
            redisUtil.getRedisTemplate().opsForValue().set(m.get("goodsId")+"", m.get("goods_stock").toString());
            //添加内存标记
            localOverMap.put(m.get("goodsId").toString(), false);
        }
    }

    /**
     * 请求秒杀,redis+rabbitmq方式
     * 1.根据需求   校验是否频繁请求
     * 2.           校验是否重复下单
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value="/go")
    @ResponseBody
    public ResultVo miaosharabbitmq(HttpServletRequest request){

        ConcurrentHashMap<String, String> parameterMap = ParameterUtil.getParameterMap(request);
        if(ToolUtil.isEmpty( parameterMap.get("userId")) || ToolUtil.isEmpty( parameterMap.get("goodsId"))
                || ToolUtil.isEmpty( parameterMap.get("num"))){
            return ResultVo.error("参数不全");
        }
        String userid = parameterMap.get("userId").toString();
        String goodsId = parameterMap.get("goodsId").toString();
        long num = Long.parseLong(parameterMap.get("num").toString());

//        Map<String,Object> map = redisService.get("order"+userid+"_"+goodsId,Map.class);
//        if(map != null) {
//            return "重复下单";
//        }

        boolean over = localOverMap.get(goodsId);
        if(over) {
            return ResultVo.error("秒杀结束");
        }

        long stock = redisUtil.decr(goodsId,num);

        if(stock < 0) {
            localOverMap.put(goodsId, true);
            return ResultVo.error("库存不足");
        }
        System.out.println("剩余库存：" + stock);
        //加入到队列中,返回0:排队中,客户端轮询或延迟几秒后查看结果
        Map<String,Object> msg = new HashMap<>();
        msg.put("user_id", userid);
        msg.put("goods_id", goodsId);
        msg.put("num", num);
        mQSender.send(msg);
        return ResultVo.success("排队中!");
    }

    //查询秒杀结果(orderId：成功,-1：秒杀失败,0： 排队中)
    @RequestMapping(value="/result", method=RequestMethod.GET)
    @ResponseBody
    public ResultVo miaoshaResult(HttpServletRequest request) {

        ConcurrentHashMap<String, String> parameterMap = ParameterUtil.getParameterMap(request);
        String userid = parameterMap.get("userId").toString();
        String goodsId = parameterMap.get("goodsId").toString();

        String result = miaoshaService.getMiaoshaResult(userid, goodsId);
        if(!result.equals("0") && !result.equals("-1")){
            return ResultVo.success("秒杀成功!  "+result);
        }else{
            return ResultVo.error("秒杀失败!");
        }
    }
}
