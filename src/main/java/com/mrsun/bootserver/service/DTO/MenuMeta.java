package com.mrsun.bootserver.service.DTO;

import lombok.Data;

import java.io.Serializable;

/**
 * 菜单meta DTO
 *
 * @author sunxiaogang
 */
@Data
public class MenuMeta implements Serializable {
    private String title;
    private String icon;
//    private List<String> roles;
}
