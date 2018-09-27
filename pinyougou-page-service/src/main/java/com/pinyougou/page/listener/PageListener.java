package com.pinyougou.page.listener;

import com.pinyougou.page.service.ItemPageService;
import com.sun.tools.internal.ws.processor.model.jaxb.RpcLitMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author tangyucong
 * @Title: PageListener
 * @ProjectName pinyougouparent
 * @Description: TODO
 * @date 2018/9/2110:31
 */
@Component
public class PageListener implements MessageListener {

    @Autowired
    private ItemPageService itemPageService;

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            String text = textMessage.getText();
            boolean b = itemPageService.genItemHtml(Long.parseLong(text));
            System.out.println(b);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
