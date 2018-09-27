package com.pinyougou.pay.service;

import java.util.Map;

/**
 * @author tangyucong
 * @Title: WeixinPayService
 * @ProjectName pinyougouparent
 * @Description: TODO
 * @date 2018/9/2515:13
 */
public interface WeixinPayService {
    public Map createNative(String out_trade_no, String total_fee);

    public Map queryPayStatus(String out_trade_no);

    /**
     * 关闭支付
     * @param out_trade_no
     * @return
     */
    public Map closePay(String out_trade_no);

}
