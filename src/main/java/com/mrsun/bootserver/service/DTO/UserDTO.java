package com.mrsun.bootserver.service.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户DTO
 *
 * @author sunxiaogang
 */
@Data
public class UserDTO implements Serializable {
    private Integer id;
    @NotBlank
    private String username;
    @NotBlank
    private String name;
    @JsonIgnore
    private String password;
    private Integer gender;
    private String avatar;
    private String phone;
    private String email;
    private Integer delFlag;
    private String createBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String updateBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 角色id列表
     */
    private List<Integer> roleIds;
}
