package com.mrsun.bootserver.service.mapper;

import com.mrsun.bootserver.entity.Permission;
import com.mrsun.bootserver.service.DTO.PermissionDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

/**
 * 权限Mapper
 *
 * @author sunxiaogang
 */
@Service
@Mapper(componentModel = "spring")
public interface PermissionMapper extends EntityMapper<PermissionDTO, Permission> {

}
