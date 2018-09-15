package com.pinyougou.sellergoods.service;

import java.util.List;

import com.pinyougou.pojo.TbGoods;

import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojogroup.Goods;
import entity.PageResult;

/**
 * 服务层接口
 *
 * @author Administrator
 */
public interface GoodsService {

    /**
     * @return java.util.List<com.pinyougou.pojo.TbItem>
     * @Description //TODO tangyucong
     * @Date 14:49 2018/9/15  根据id跟状态查询审核的商品
     * @Param [goodsIds, status]
     */
    public List<TbItem> findItemListByGoodsIdandStatus(Long[] goodsIds, String status);

    /**
     * @return void
     * @Description //TODO tangyucong
     * @Date 15:48 2018/9/11 商品上下架
     * @Param [ids, marketable]
     */
    public void updateMarketable(Long[] ids, String marketable);

    /**
     * @return void
     * @Description //TODO tangyucong
     * @Date 15:48 2018/9/11 商品审核
     * @Param [ids, status]
     */
    public void updateStatus(Long[] ids, String status);

    /**
     * 返回全部列表
     *
     * @return
     */
    public List<TbGoods> findAll();


    /**
     * 返回分页列表
     *
     * @return
     */
    public PageResult findPage(int pageNum, int pageSize);


    /**
     * 增加
     */
    public void add(Goods goods);


    /**
     * 修改
     */
    public void update(Goods goods);


    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    public Goods findOne(Long id);


    /**
     * 批量删除
     *
     * @param ids
     */
    public void delete(Long[] ids);

    /**
     * 分页
     *
     * @param pageNum  当前页 码
     * @param pageSize 每页记录数
     * @return
     */
    public PageResult findPage(TbGoods goods, int pageNum, int pageSize);

}
