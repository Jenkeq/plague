package com.gover.plague.mapper;

import com.gover.plague.trans.dto.ResRoleDO;
import com.gover.plague.trans.dto.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 根据用户名获取用户信息
     * @return
     */
    List<UserDO> getUserByName(@Param("req") String userName);

    /**
     * 获取全部资源和角色映射
     * @return
     */
    List<ResRoleDO> getAllResRole();
}
