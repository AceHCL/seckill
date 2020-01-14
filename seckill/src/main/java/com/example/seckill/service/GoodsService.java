package com.example.seckill.service;

import com.example.seckill.dao.GoodsDao;
import com.example.seckill.dao.UserDao;
import com.example.seckill.domain.Goods;
import com.example.seckill.domain.SeckillGoods;
import com.example.seckill.domain.User;
import com.example.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-22 4:35 PM
 */
@Service
public class GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    public List<GoodsVo> getGoodsVoList(){
        return goodsDao.getGoodsVoList();
    }

    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    public boolean reduceStock(GoodsVo goods) {
        SeckillGoods g = new SeckillGoods();
        g.setGoodsId(goods.getId());
        g.setStockCount(goods.getStockCount() - 1);
        int ret = goodsDao.reduceStock(g);
        return ret > 0;
    }
}