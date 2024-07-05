package org.leocoder.action.service;

import org.leocoder.action.domain.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author : Leo
 * @version 1.0
 * @date 2024-07-04 20:19
 * @description :
 */

public interface UserInfoService extends IService<UserInfo> {


    /**
     * 根据id获取用户信息 多表查询
     *
     * @param id 用户id
     * @return 用户信息
     */
    UserInfo getUserById(Integer id);


    /**
     * 获取用户列表 多表查询
     * @return 用户列表
     */
    List<UserInfo> userList();

}
