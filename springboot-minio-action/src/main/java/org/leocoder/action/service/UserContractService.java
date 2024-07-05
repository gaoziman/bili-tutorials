package org.leocoder.action.service;

import org.leocoder.action.domain.UserContract;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author : Leo
 * @version 1.0
 * @date 2024-07-04 20:17
 * @description :
 */

public interface UserContractService extends IService<UserContract> {


    /**
     * 保存或更新用户图片信息
     *
     * @param id     用户id
     * @param bucket 图片bucket
     * @param object 图片object
     */
    boolean saveOrUpdateUserContract(Integer id, String bucket, String object);
}
