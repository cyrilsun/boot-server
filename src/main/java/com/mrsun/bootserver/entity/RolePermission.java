package com.mrsun.bootserver.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 角色权限关联表
 *
 * @author sunxiaogang
 */
@Data
@TableName("sys_role_permission")
public class RolePermission {
    private Integer roleId;
    private Integer permissionId;
}
