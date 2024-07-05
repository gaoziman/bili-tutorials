package org.leocoder.action.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.leocoder.action.domain.UserImage;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.leocoder.action.domain.UserContract;
import org.leocoder.action.mapper.UserContractMapper;
import org.leocoder.action.service.UserContractService;

import java.util.Date;

/**
 * @author : Leo
 * @date  2024-07-04 20:17
 * @version 1.0
 * @description :
 */

@Service
public class UserContractServiceImpl extends ServiceImpl<UserContractMapper, UserContract> implements UserContractService {

    /**
     * 保存或更新用户图片信息
     *
     * @param id 用户id
     * @param bucket 图片bucket
     * @param object 图片object
     */
    @Override
    public boolean saveOrUpdateUserContract(Integer id, String bucket, String object) {

        boolean flag = false;

        UserContract userContract = new UserContract();
        userContract.setBucket(bucket);
        userContract.setObject(object);
        userContract.setUid(id);

        LambdaQueryWrapper<UserContract> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserContract::getUid, id);
        Long count = baseMapper.selectCount(wrapper);
        if (count <= 0) {
            // 新增
            userContract.setCreateTime(new Date());
            flag = baseMapper.insert(userContract) >= 1;
        }else {
            // 更新
            userContract.setUpdateTime(new Date());
            flag = baseMapper.update(userContract, wrapper) >=1;
        }
        return flag;
    }
}
