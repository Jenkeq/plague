package com.gover.plague.mapper;

import com.gover.plague.trans.dto.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    /**
     * 根据用户名获取用户信息
     * @return
     */
    UserDO getUserByName(@Param("req") String userName);
}
