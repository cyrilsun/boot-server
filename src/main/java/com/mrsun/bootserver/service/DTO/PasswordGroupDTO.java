package com.mrsun.bootserver.service.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 密码分组DTO类
 *
 * @author sunxiaogang
 */
@Data
public class PasswordGroupDTO implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String name;

    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
