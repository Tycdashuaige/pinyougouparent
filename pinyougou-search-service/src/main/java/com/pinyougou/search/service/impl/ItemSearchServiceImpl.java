package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.ItemSearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tangyucong
 * @Title: ItemSearchServiceImpl
 * @ProjectName pinyougouparent
 * @Description: TODO
 * @date 2018/9/1310:02
 */
@Service(timeout = 3000)
public class ItemSearchServiceImpl implements ItemSearchService {

    @Autowired
    private SolrTemplate solrTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    private Map searchBrandAndSpecList(String category) {

        Map map = new HashMap<>();
        Long typeId = (Long) redisTemplate.boundHashOps("itemCat").get(category);
        if (typeId != null) {
            List randList = (List) redisTemplate.boundHashOps("brandList").get(typeId);
            map.put("brandList", randList);

            List specList = (List) redisTemplate.boundHashOps("specList").get(typeId);
            map.put("specList", specList);
        }
        return map;
    }

    @Override
    public Map<String, Object> search(Map searchMap) {
        Map<String, Object> map = new HashMap<>();
        String keywords = (String) searchMap.get("keywords");
        searchMap.put("keywords", keywords.replace(" ", ""));
        map.putAll(searchList(searchMap));
        List categoryList = this.searchCategoryList(searchMap);
        map.put("categoryList", categoryList);
        String category = (String) searchMap.get("category");
        if (!"".equals(category)) {
            map.putAll(searchBrandAndSpecList(category));
        } else {
            if (categoryList.size() > 0) {
                map.putAll(searchBrandAndSpecList((String) categoryList.get(0)));
            }
        }
        return map;
    }

    /**
     * @Description //TODO tangyucong
     * @Date 14:55 2018/9/15 导入数据
     * @Param [list]
     * @return void
     */
    @Override
    public void importList(List list) {
        solrTemplate.saveBeans(list);
        solrTemplate.commit();
    }

    /**
     * @Description //TODO tangyucong
     * @Date 15:08 2018/9/15 删除索引
     * @Param [goodsIdList]
     * @return void
     */
    @Override
    public void deleteByGoodsIds(List goodsIdList) {
        System.out.println("删除商品" + goodsIdList);
        SimpleQuery query = new SimpleQuery();
        Criteria criteria = new Criteria("item_goodsid").in(goodsIdList);
        query.addCriteria(criteria);
        solrTemplate.delete(query);
        solrTemplate.commit();
    }

    /**
     * @return java.util.Map
     * @Description //TODO tangyucong
     * @Date 21:48 2018/9/13 关键词高亮查询
     * @Param [searchMap]
     */
    private Map searchList(Map searchMap) {
        Map map = new HashMap<>();
        SimpleHighlightQuery query = new SimpleHighlightQuery();
        HighlightOptions highlightOptions = new HighlightOptions().addField("item_title");

        highlightOptions.setSimplePrefix("<em style='color:red'>");
        highlightOptions.setSimplePostfix("</em>");
        query.setHighlightOptions(highlightOptions);

        String keywords = (String) searchMap.get("keywords");
        if (StringUtils.isNotBlank(keywords)) {
            Criteria criteria = new Criteria("item_keywords").is(keywords);
            query.addCriteria(criteria);
        } else {
            Criteria criteria = new Criteria("item_keywords").is("*");
            query.addCriteria(criteria);
        }

        if (!"".equals(searchMap.get("category"))) {
            Criteria filterCriteria = new Criteria("item_category").is(searchMap.get("category"));
            SimpleFilterQuery filterQuery = new SimpleFilterQuery(filterCriteria);
            query.addFilterQuery(filterQuery);
        }

        if (!"".equals(searchMap.get("brand"))) {
            Criteria brandCriteria = new Criteria("item_brand").is(searchMap.get("brand"));
            SimpleFilterQuery filterQuery = new SimpleFilterQuery(brandCriteria);
            query.addFilterQuery(filterQuery);
        }

        if (searchMap.get("spec") != null) {
            Map<String, String> specMap = (Map<String, String>) searchMap.get("spec");
            for (String key : specMap.keySet()) {
                Criteria criteria1 = new Criteria("item_spec_" + key).is(specMap.get(key));
                SimpleFilterQuery filterQuery = new SimpleFilterQuery(criteria1);
                query.addFilterQuery(filterQuery);
            }
        }

        if (!"".equals(searchMap.get("price"))) {
            String[] prices = ((String) searchMap.get("price")).split("-");
            if (!prices[0].equals("0")) {
                Criteria filterCriteria = new Criteria("item_price").greaterThanEqual(prices[0]);
                SimpleFilterQuery filterQuery = new SimpleFilterQuery(filterCriteria);
                query.addFilterQuery(filterQuery);
            }

            if (!prices[1].equals("*")) {
                Criteria filterCriteria = new Criteria("item_price").lessThanEqual(prices[1]);
                SimpleFilterQuery filterQuery = new SimpleFilterQuery(filterCriteria);
                query.addFilterQuery(filterQuery);
            }
        }

        Integer pageNo = (Integer) searchMap.get("pageNo");
        if (pageNo == null) {
            pageNo = 1;
        }
        Integer pageSize = (Integer) searchMap.get("pageSize");
        if (pageSize == null) {
            pageSize = 20;
        }
        query.setOffset((pageNo - 1) * pageSize);
        query.setRows(pageSize);

        String sort = (String) searchMap.get("sort");
        String sortField = (String) searchMap.get("sortField");
        if (sort!=null&&!sort.equals("")){
            if (sort.equals("ASC")){
                Sort sort1 = new Sort(Sort.Direction.ASC, "item_" + sortField);
                query.addSort(sort1);
            }
            if (sort.equals("DESC")){
                Sort sort1 = new Sort(Sort.Direction.DESC, "item_" + sortField);
                query.addSort(sort1);
            }
        }

        HighlightPage<TbItem> tbItems = solrTemplate.queryForHighlightPage(query, TbItem.class);

        for (HighlightEntry<TbItem> h : tbItems.getHighlighted()) {
            if (h.getHighlights() != null && h.getHighlights().size() > 0 && h.getHighlights().get(0).getSnipplets() != null && h.getHighlights().get(0).getSnipplets().size() > 0) {
                TbItem tbItem = h.getEntity();
                tbItem.setTitle(h.getHighlights().get(0).getSnipplets().get(0));
            }
        }
        map.put("rows", tbItems.getContent());
        map.put("totalPages", tbItems.getTotalPages());
        map.put("totalElements", tbItems.getTotalElements());
        return map;
    }

    /**
     * @return java.util.List
     * @Description //TODO tangyucong
     * @Date 21:56 2018/9/13 根据关键词查询分类列表
     * @Param [searchMap]
     */
    private List searchCategoryList(Map searchMap) {
        List<String> list = new ArrayList<>();
        SimpleQuery simpleQuery = new SimpleQuery();

        String keywords = (String) searchMap.get("keywords");
        if (StringUtils.isNotBlank(keywords)) {
            Criteria criteria = new Criteria("item_keywords").is(keywords);
            simpleQuery.addCriteria(criteria);
        } else {
            Criteria criteria = new Criteria("item_keywords").is("*");
            simpleQuery.addCriteria(criteria);
        }

        GroupOptions groupOptions = new GroupOptions().addGroupByField("item_category");
        simpleQuery.setGroupOptions(groupOptions);

        GroupPage<TbItem> tbItems = solrTemplate.queryForGroupPage(simpleQuery, TbItem.class);
        GroupResult<TbItem> groupResult = tbItems.getGroupResult("item_category");
        Page<GroupEntry<TbItem>> groupEntries = groupResult.getGroupEntries();
        List<GroupEntry<TbItem>> content = groupEntries.getContent();

        for (GroupEntry<TbItem> itemGroupEntry : content) {
            list.add(itemGroupEntry.getGroupValue());
        }
        return list;
    }
}
