package com.pinyougou.solrutil;

import com.alibaba.fastjson.JSON;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tangyucong
 * @Title: SolrUtil
 * @ProjectName pinyougouparent
 * @Description: TODO
 * @date 2018/9/139:09
 */
@Component
public class SolrUtil {

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private SolrTemplate solrTemplate;

    public void importItemData() {

        TbItemExample itemExample = new TbItemExample();
        TbItemExample.Criteria criteria = itemExample.createCriteria();
        criteria.andStatusEqualTo("1");
        List<TbItem> tbItems = itemMapper.selectByExample(itemExample);
        for (TbItem tbItem : tbItems) {
            Map specMap = JSON.parseObject(tbItem.getSpec());
            tbItem.setSpecMap(specMap);
            System.out.println(tbItem.getTitle());
        }
        solrTemplate.saveBeans(tbItems);
        solrTemplate.commit();
        System.out.println("结束");
    }

}
