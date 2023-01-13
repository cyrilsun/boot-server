package com.mrsun.bootserver.service.mapper;

import com.mrsun.bootserver.entity.Role;
import com.mrsun.bootserver.service.DTO.RoleDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

/**
 * 角色Mapper
 *
 * @author sunxiaogang
 */
@Service
@Mapper(componentModel = "spring")
public interface RoleMapper extends EntityMapper<RoleDTO, Role> {

}
