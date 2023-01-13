package com.mrsun.bootserver.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * 实体基类
 *
 * @author sunxiaogang
 */
@Data
public class BaseEntity {
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "update_by", fill = FieldFill.UPDATE)
    private String updateBy;
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;
}
