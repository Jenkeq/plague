package com.gover.plague.cache.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class DefaultMessageListenerContainerConfig {

//    @Bean
//    public RedisMessageListenerContainer container(RedisConnectionFactory factory) {
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(factory);
//        // 官方推荐我们使用自定义的线程池或者使用TaskExecutor
//        container.setTaskExecutor(executor());
//        container.addMessageListener(new MessageListener() {
//            @Override
//            public void onMessage(Message message, byte[] pattern) {
//                System.out.println(Thread.currentThread().getName() + ": " + new String(message.getBody()));
//            }
//        }, new ChannelTopic("message"));
//        return container;
//    }
//
//    @Bean
//    public TaskExecutor executor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
//        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() * 2);
//        executor.setQueueCapacity(100);
//        executor.initialize();
//        return executor;
//    }
}