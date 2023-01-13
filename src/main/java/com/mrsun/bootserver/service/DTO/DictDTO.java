package com.mrsun.bootserver.service.DTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * 字典表DTO类
 *
 * @author sunxiaogang
 */
@Data
public class DictDTO implements Serializable {

    private Integer id;

    private Integer parentId;

    @NotEmpty(message = "名称不能为空")
    private String name;

    private String dictCode;

    private String dictValue;

    private boolean hasChildren;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;
}
