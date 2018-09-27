package com.pinyougou.page.listener;

import com.pinyougou.page.service.ItemPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * @author tangyucong
 * @Title: PageDeleteListener
 * @ProjectName pinyougouparent
 * @Description: TODO
 * @date 2018/9/2110:48
 */
@Component
public class PageDeleteListener implements MessageListener {

    @Autowired
    private ItemPageService itemPageService;

    @Override
    public void onMessage(Message message) {
        ObjectMessage objectMessage = (ObjectMessage) message;
        try {
            Long[] goodsIds = (Long[]) objectMessage.getObject();
            boolean b = itemPageService.deleteItemHtml(goodsIds);
            System.out.println("删除结果:"+b);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
