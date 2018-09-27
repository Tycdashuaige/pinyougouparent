package com.pinyougou.search.listener;

import com.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.Arrays;
import java.util.List;

/**
 * @author tangyucong
 * @Title: itemSearchListenerDelete
 * @ProjectName pinyougouparent
 * @Description: TODO
 * @date 2018/9/2110:00
 */
@Component
public class itemSearchListenerDelete implements MessageListener {

    @Autowired
    private ItemSearchService itemSearchService;

    @Override
    public void onMessage(Message message) {
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            Long[] ids = (Long[]) objectMessage.getObject();
            List<Long> longs = Arrays.asList(ids);
            itemSearchService.deleteByGoodsIds(longs);
            System.out.println("成功删除索引");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
