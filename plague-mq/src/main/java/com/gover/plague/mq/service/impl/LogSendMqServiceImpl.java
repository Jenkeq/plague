package com.gover.plague.mq.service.impl;

import com.gover.plague.channel.OrderLogChannel;
import com.gover.plague.common.ResultVO;
import com.gover.plague.mq.req.OrderLogReq;
import com.gover.plague.mq.service.LogSendMqService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

@Service
@Component
public class LogSendMqServiceImpl implements LogSendMqService {

//    方式一
//    @Resource(name = OrderLogChannel.ORDER_LOG_OUTPUT)
//    private MessageChannel messageChannel;

    @Autowired
    private OrderLogChannel orderLogChannel;

    /**
     * 发送订单日志
     * @param req
     * @return
     */
    @Override
    public ResultVO sendOrderLog(OrderLogReq req) {
        MessageChannel messageChannel = orderLogChannel.msgChannel();
        boolean send = messageChannel.send(MessageBuilder
                .withPayload(req)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build());

//        方式一
//        boolean isSendSuccess = orderLogChannel.send(MessageBuilder.withPayload(req).build());
        return ResultVO.success(send);
    }
}
