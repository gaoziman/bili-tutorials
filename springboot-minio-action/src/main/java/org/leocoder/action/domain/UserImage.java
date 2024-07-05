package org.leocoder.action.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author : Leo
 * @version 1.0
 * @date 2024-07-04 20:19
 * @description :
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_user_image")
public class UserImage {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "`uid`")
    private Integer uid;

    @TableField(value = "bucket")
    private String bucket;

    @TableField(value = "`object`")
    private String object;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time")
    private Date createTime;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time")
    private Date updateTime;
}