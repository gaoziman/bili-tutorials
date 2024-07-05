package org.leocoder.action.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.leocoder.action.domain.UserInfo;

import java.util.List;

/**
 * @author : Leo
 * @date  2024-07-04 20:19
 * @version 1.0
 * @description :
 */
@Mapper

public interface UserInfoMapper extends BaseMapper<UserInfo> {


    /**
     * 根据id获取用户信息 多表查询
     *
     * @param id 用户id
     * @return 用户信息
     */
    UserInfo getUserById(@Param("id") Integer id);


    /**
     * 获取用户列表 多表查询
     * @return 用户列表
     */
    List<UserInfo> userList();

}