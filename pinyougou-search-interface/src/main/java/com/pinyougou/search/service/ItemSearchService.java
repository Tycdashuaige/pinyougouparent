package com.pinyougou.search.service;

import java.util.List;
import java.util.Map;

/**
 * @author tangyucong
 * @Title: ItemSearchService
 * @ProjectName pinyougouparent
 * @Description: TODO
 * @date 2018/9/139:49
 */
public interface ItemSearchService {

    public Map<String,Object> search(Map searchMap);

    public void importList(List list);

    public void deleteByGoodsIds(List goodsIdList);
}
