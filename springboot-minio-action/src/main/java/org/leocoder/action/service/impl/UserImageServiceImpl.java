package org.leocoder.action.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.leocoder.action.mapper.UserImageMapper;
import org.leocoder.action.domain.UserImage;
import org.leocoder.action.service.UserImageService;

import java.util.Date;

/**
 * @author : Leo
 * @date  2024-07-04 20:19
 * @version 1.0
 * @description :
 */

@Service
public class UserImageServiceImpl extends ServiceImpl<UserImageMapper, UserImage> implements UserImageService {


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

        UserImage userImage = new UserImage();
        userImage.setBucket(bucket);
        userImage.setObject(object);
        userImage.setUid(id);

        LambdaQueryWrapper<UserImage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserImage::getUid, id);
        Long count = baseMapper.selectCount(wrapper);
        if (count <= 0) {
            // 新增
            userImage.setCreateTime(new Date());
            flag = baseMapper.insert(userImage) >= 1;
        }else {
            // 更新
            userImage.setUpdateTime(new Date());
            flag = baseMapper.update(userImage, wrapper) >=1;
        }
        return flag;
    }
}
