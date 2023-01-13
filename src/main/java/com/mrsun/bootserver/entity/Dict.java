package com.mrsun.bootserver.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 字典表实体类
 *
 * @author sunxiaogang
 */
@Data
@TableName("tb_dict")
public class Dict extends BaseEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer parentId;

    @TableField(condition = SqlCondition.LIKE)
    private String name;

    @TableField(condition = SqlCondition.LIKE)
    private String dictCode;

    private String dictValue;

}
