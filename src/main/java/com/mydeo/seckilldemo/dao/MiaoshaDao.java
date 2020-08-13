package com.mydeo.seckilldemo.dao;

/**
 * @Author: Chris he
 * @Date: 2020/7/31 17:41
 */
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MiaoshaDao {

    List<Map<String, Object>> queryAllGoodStock();

    Map<String, Object> queryGoodStockById(Map<String, Object> m);

    List<Map<String, Object>> queryOrderByUserIdAndCoodsId(Map<String, Object> m);

    Integer updateGoodStock(Map<String, Object> m);

    Integer insertOrder(Map<String, Object> m);
}
