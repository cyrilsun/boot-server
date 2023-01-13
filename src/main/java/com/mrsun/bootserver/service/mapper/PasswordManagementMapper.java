package com.mrsun.bootserver.service.mapper;

import com.mrsun.bootserver.entity.PasswordManagement;
import com.mrsun.bootserver.service.DTO.PasswordManagementDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

/**
 * 密码管理表映射类
 *
 * @author sunxiaogang
 * @since 2022/12/19 14:29
 */
@Service
@Mapper(componentModel = "spring")
public interface PasswordManagementMapper extends EntityMapper<PasswordManagementDTO, PasswordManagement> {
}
