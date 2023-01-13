package com.mrsun.bootserver.Respository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrsun.bootserver.entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 角色权限关联Repository
 *
 * @author sunxiaogang
 */
@Mapper
public interface RolePermissionRepository extends BaseMapper<RolePermission> {

    /**
     * 根据权限id查询角色列表
     *
     * @param permissionId 权限id
     * @return 角色列表
     */
    @Select("SELECT sr.role_code FROM sys_role_permission srp JOIN sys_role sr ON srp.role_id = sr.id WHERE srp.permission_id = #{0}")
    List<String> selectRolesByPermissionId(Integer permissionId);
}
