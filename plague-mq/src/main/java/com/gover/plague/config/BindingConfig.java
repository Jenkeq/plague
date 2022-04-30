package com.gover.plague.config;

import com.gover.plague.channel.ApiAccessLogChannel;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(value = {ApiAccessLogChannel.class})
public class BindingConfig {
}
