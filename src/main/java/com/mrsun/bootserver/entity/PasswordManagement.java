package com.mrsun.bootserver.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * 密码管理表实体类
 *
 * @author sunxiaogang
 */
@Data
@TableName("tb_password_management")
public class PasswordManagement extends BaseEntity {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 网站名称
     */
    private String name;
    /**
     * 网址
     */
    @TableField(condition = SqlCondition.LIKE)
    private String website;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String code;
    /**
     * 提示
     */
    private String tip;
    /**
     * 是否本地存储盐 0否(默认) 1是
     */
    private Integer localStorage;
    /**
     * 盐
     */
    private String salt;
    /**
     * 自定义秘钥 0否 1是
     */
    private Integer custom;
    /**
     * 分组id
     */
    private Long groupId;
    /**
     * 备份
     */
    private String remark;
    /**
     * 填充
     */
    private String fill;

}
