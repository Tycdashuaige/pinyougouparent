package com.pinyougou.page.service;

/**
 * @author tangyucong
 * @Title: ItemPageService
 * @ProjectName pinyougouparent
 * @Description: TODO
 * @date 2018/9/187:57
 */
public interface ItemPageService {

    public boolean genItemHtml(Long goodsId);

    boolean deleteItemHtml(Long[] goodsIds);
}
