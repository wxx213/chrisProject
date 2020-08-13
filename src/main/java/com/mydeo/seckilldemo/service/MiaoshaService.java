package com.mydeo.seckilldemo.service;

/**
 * @Author: Chris he
 * @Date: 2020/7/31 17:41
 */
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.mydeo.seckilldemo.dao.MiaoshaDao;
import com.mydeo.seckilldemo.util.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;



@Service
public class MiaoshaService {

    @Autowired
    private MiaoshaDao miaoshaDao;
    @Autowired
    private RedisService redisService;

    @Autowired
    private  RedisUtil redisUtil;

    //查询全部商品库存数量
    public List<Map<String, Object>> queryAllGoodStock(){
        return  miaoshaDao.queryAllGoodStock();
    };

    //通过商品ID查询库存数量
    public Map<String, Object> queryGoodStockById(Map<String, Object> m){
        return  miaoshaDao.queryGoodStockById(m);
    };

    //根据用户ID和商品ID查询是否下过单
    public List<Map<String, Object>> queryOrderByUserIdAndCoodsId(Map<String, Object> m){
        return  miaoshaDao.queryOrderByUserIdAndCoodsId(m);
    };

    //减少库存,下订单,是一个事务
    @Transactional
    public void miaosha(Map<String, Object> m){
        //减少库存
        int count = miaoshaDao.updateGoodStock(m);
        if(count > 0){
            try {
                //减少库存成功后下订单,由于一件商品同一用户只能购买一次,所以需要建立用户ID和商品ID的联合索引
                m.put("id", UUID.randomUUID().toString().replaceAll("-", ""));
                miaoshaDao.insertOrder(m);
                //将生成的订单放入缓存
                redisService.set("order"+m.get("user_id")+"_"+m.get("goods_id"), m);
            } catch (Exception e) {
                //出现异常手动回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                redisService.incr("goods"+m.get("goods_id"));
            }
        }else {
            //减少库存失败做一个标记,代表商品已经卖完了
            redisService.set("goodsover"+m.get("goods_id"), true);
        }
    }

    //获取秒杀结果
    @SuppressWarnings("unchecked")
    public String getMiaoshaResult(String userId, String goodsId) {

        Map<String, Object> orderMap = redisService.get("order"+userId+"_"+goodsId, Map.class);
        if(orderMap != null) {//秒杀成功
            return orderMap.get("id").toString();
        }else {
            boolean isOver = getGoodsOver(goodsId);
            if(isOver) {
                return "-1";
            }else {
                return "0";
            }
        }
    }

    //查询是否卖完了
    private boolean getGoodsOver(String goodsId) {
        return redisService.exists("goodsover"+goodsId);
    }

}
