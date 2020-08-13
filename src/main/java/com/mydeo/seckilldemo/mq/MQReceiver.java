package com.mydeo.seckilldemo.mq;

/**
 * @Author: Chris he
 * @Date: 2020/7/31 17:36
 */
import java.util.List;
import java.util.Map;

import com.mydeo.seckilldemo.config.druid.MQConfig;
import com.mydeo.seckilldemo.service.MiaoshaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//消费者
@Service
public class MQReceiver {

    @Autowired
    private MiaoshaService miaoshaService;

    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);

    @RabbitListener(queues= MQConfig.QUEUE)//指明监听的是哪一个queue
    public void receive(Map<String,Object> msg) {
        //log.info("监听到队列消息,用户id为：{}，商品id为：{},购买数量:{}", msg.get("user_id"),msg.get("goods_id"),msg.get("num"));
        int stock = 0;
        //查数据库中商品库存
        Map<String, Object> m = miaoshaService.queryGoodStockById(msg);
        if(m != null && m.get("goods_stock") != null){
            stock = Integer.parseInt(m.get("goods_stock").toString());
        }
        if(stock <= 0){//库存不足
            log.info("用户：{}秒杀时商品的库存量没有剩余,秒杀结束", msg.get("user_id"));
            return;
        }
        //这里业务是同一用户同一商品只能购买一次,所以判断该商品用户是否下过单
//        List<Map<String, Object>> list = miaoshaService.queryOrderByUserIdAndCoodsId(msg);
//        if(list != null && list.size() > 0){//重复下单
//            return;
//        }
        //减库存,下订单
        log.info("用户：{}秒杀该商品：{}库存有余:{},可以进行下订单操作", msg.get("user_id"),msg.get("goods_id"),stock);
        miaoshaService.miaosha(msg);
    }

}
