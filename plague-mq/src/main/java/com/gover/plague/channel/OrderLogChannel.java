package com.gover.plague.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

@Component
public interface OrderLogChannel {

    /**
     * 发消息的通道名称
     */
    String ORDER_LOG_OUTPUT = "order_log_output";

    /**
     * 消息的订阅通道名称
     */
    String ORDER_LOG_INPUT = "order_log_input";

    /**
     * 发消息的通道
     *
     * @return
     */
    @Output(ORDER_LOG_OUTPUT)
    MessageChannel msgChannel();

    /**
     * 收消息的通道
     *
     * @return
     */
    @Input(ORDER_LOG_INPUT)
    SubscribableChannel subChannel();
}
