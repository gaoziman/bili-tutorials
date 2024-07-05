package org.leocoder.action.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.leocoder.action.mapper.UserInfoMapper;
import org.leocoder.action.domain.UserInfo;
import org.leocoder.action.service.UserInfoService;

import java.util.List;

/**
 * @author : Leo
 * @date  2024-07-04 20:19
 * @version 1.0
 * @description :
 */

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {


    /**
     * 根据id获取用户信息 多表查询
     *
     * @param id 用户id
     * @return 用户信息
     */
    @Override
    public UserInfo getUserById(Integer id) {
        return baseMapper.getUserById(id);
    }


    /**
     * 获取用户列表 多表查询
     * @return 用户列表
     */
    @Override
    public List<UserInfo> userList() {
        return baseMapper.userList();
    }
}
