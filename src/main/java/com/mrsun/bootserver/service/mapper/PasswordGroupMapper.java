package com.mrsun.bootserver.service.mapper;

import com.mrsun.bootserver.entity.PasswordGroup;
import com.mrsun.bootserver.service.DTO.PasswordGroupDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

/**
 * 密码分组映射类
 *
 * @author sunxiaogang
 */
@Service
@Mapper(componentModel = "spring")
public interface PasswordGroupMapper extends EntityMapper<PasswordGroupDTO, PasswordGroup> {
}
