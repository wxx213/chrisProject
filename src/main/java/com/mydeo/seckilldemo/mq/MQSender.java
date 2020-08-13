package com.mydeo.seckilldemo.mq;

/**
 * @Author: Chris he
 * @Date: 2020/7/31 17:35
 */
import java.util.Map;

import com.mydeo.seckilldemo.config.druid.MQConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//生产者
@Service
public class MQSender {

    @Autowired
    AmqpTemplate amqpTemplate;

    //Direct模式
    public void send(Map<String,Object> msg) {
        //第一个参数队列的名字，第二个参数发出的信息
        amqpTemplate.convertAndSend(MQConfig.QUEUE, msg);
    }

}
