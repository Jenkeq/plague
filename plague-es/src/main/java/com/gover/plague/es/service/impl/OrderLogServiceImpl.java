package com.gover.plague.es.service.impl;

import com.gover.plague.es.service.OrderLogService;
import com.gover.plague.repository.OrderLogRepository;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 日志服务类 实现类
 */
@Service
@Component
public class OrderLogServiceImpl implements OrderLogService {

    @Autowired
    private OrderLogRepository orderLogRepository;


}
