package com.mrsun.bootserver.service.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mrsun.bootserver.entity.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 权限DTO
 *
 * @author sunxiaogang
 */
@Data
public class PermissionDTO extends BaseEntity {
    private Integer id;
    private Integer parentId;
    @NotBlank
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
    private String createBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String updateBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
