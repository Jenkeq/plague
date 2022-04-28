package com.gover.plague.repository;

import com.gover.plague.entity.log.OrderLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLogRepository extends ElasticsearchRepository<OrderLog, String> {
}
