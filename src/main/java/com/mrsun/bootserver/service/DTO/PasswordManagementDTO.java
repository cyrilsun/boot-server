package com.mrsun.bootserver.service.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * 密码管理表DTO类
 *
 * @author sunxiaogang
 */
@Data
public class PasswordManagementDTO implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @NotEmpty(message = "名称不能为空")
    private String name;

    @NotEmpty(message = "网址不能为空")
    private String website;

    @NotEmpty(message = "用户名不能为空")
    @Length(max = 64)
    private String username;

    @NotEmpty(message = "密码不能为空")
    @Length(max = 32)
    private String code;

    private String tip;

    private Integer localStorage;

    private String salt;

    private Integer custom;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long groupId;

    private String remark;

    @JsonIgnore
    private String fill;

    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
