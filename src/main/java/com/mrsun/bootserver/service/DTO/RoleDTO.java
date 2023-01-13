package com.mrsun.bootserver.service.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 角色DTO
 *
 * @author sunxiaogang
 */
@Data
public class RoleDTO implements Serializable {
    private Integer id;
    @NotBlank
    private String roleName;
    @NotBlank
    private String roleCode;
    private String description;
    private String createBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String updateBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
