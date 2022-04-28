package com.gover.plague.es.test;

import com.gover.plague.entity.log.OrderLog;
import com.gover.plague.repository.OrderLogRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

@SpringBootTest
class ESApplicationTests {

    @Autowired
    private OrderLogRepository orderLogRepository;

    @Test
    void contextLoads() {
        OrderLog orderLog = new OrderLog();
        orderLog.setId("1");
        orderLog.setUserName("kang");
        orderLog.setOrderId("1");
        orderLog.setCreatTime(new Date());

        OrderLog orderlog = orderLogRepository.save(orderLog);
        System.out.println(orderlog);
    }

}
