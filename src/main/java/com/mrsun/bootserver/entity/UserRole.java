package com.mrsun.bootserver.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户角色关联表
 *
 * @author sunxiaogang
 */
@Data
@TableName("sys_user_role")
public class UserRole {
    private Integer userId;
    private Integer roleId;
}
