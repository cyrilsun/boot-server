package com.mrsun.bootserver.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * 角色表
 *
 * @author sunxiaogang
 */
@Data
@TableName("sys_role")
public class Role extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField(condition = SqlCondition.LIKE)
    private String roleName;
    private String roleCode;
    private String description;
}
