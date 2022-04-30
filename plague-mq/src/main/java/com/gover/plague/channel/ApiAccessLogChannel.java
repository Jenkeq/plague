package com.gover.plague.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

/**
 * API访问日志 发送通道
 */
@Component
public interface ApiAccessLogChannel {

    /**
     * 发消息的通道名称
     */
    String API_LOG_OUTPUT = "api_access_log_output";

    /**
     * 消息的订阅通道名称
     */
    String API_LOG_INPUT = "api_access_log_input";

    /**
     * 发消息的通道
     *
     * @return
     */
    @Output(API_LOG_OUTPUT)
    MessageChannel msgChannel();

    /**
     * 收消息的通道
     *
     * @return
     */
    @Input(API_LOG_INPUT)
    SubscribableChannel subChannel();
}
