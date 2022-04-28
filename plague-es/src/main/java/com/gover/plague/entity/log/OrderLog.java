package com.gover.plague.entity.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * 订单日志
 */
@Data
@Document(indexName = "orderlog", shards = 1, replicas = 1)
public class OrderLog {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String orderId;

    @Field(type = FieldType.Text)
    private String userName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Field(type= FieldType.Date, format= DateFormat.custom, pattern="yyyy-MM-dd HH:mm:ss.SSS")
    private Date creatTime;

}
