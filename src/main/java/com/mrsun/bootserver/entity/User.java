package com.mrsun.bootserver.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 用户表
 *
 * @author sunxiaogang
 */
@Data
@TableName("sys_user")
public class User extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    @TableField(condition = SqlCondition.LIKE)
    private String name;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private Integer gender;
    private String avatar;
    private String phone;
    private String email;
    private Integer delFlag;

    @TableField(exist = false)
    private List<String> roles;
}
