package com.mrsun.bootserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限表
 *
 * @author sunxiaogang
 */
@Data
@TableName("sys_permission")
public class Permission extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer parentId;
    private String name;
    private String path;
    private String component;
    private String title;
    private String icon;
    private String code;
    private String url;
    private Integer type;
    private Integer sortNo;
    private Integer isLeaf;
    private Integer delFlag;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(exist = false)
    private List<Permission> children = new ArrayList<>();
}
