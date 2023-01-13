package com.mrsun.bootserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 密码分组实体类
 *
 * @author sunxiaogang
 */
@TableName("tb_password_group")
@Data
public class PasswordGroup extends BaseEntity {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 分组名称
     */
    private String name;
}
