package com.gover.plague.config;

import com.gover.plague.channel.OrderLogChannel;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(value = {OrderLogChannel.class})
public class BindingConfig {
}
