package com.mrsun.bootserver.service.DTO;

import lombok.Data;

import java.util.List;

/**
 * 用户信息
 *
 * @author sunxiaogang
 */
@Data
public class UserInfo {
    private Integer id;
    private String username;
    private String name;
    private String avatar;
    private List<String> roles;
}
