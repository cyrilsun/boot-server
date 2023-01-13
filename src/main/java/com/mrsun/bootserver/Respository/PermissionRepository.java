package com.mrsun.bootserver.Respository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrsun.bootserver.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 权限Repository
 *
 * @author sunxiaogang
 */
@Mapper
public interface PermissionRepository extends BaseMapper<Permission> {
    /**
     * 根据用户id查询权限列表
     *
     * @param userId 用户id
     * @return 权限列表
     */
    List<Permission> selectPermissionsByUserId(@Param("userId") Integer userId);

    /**
     * 根据用户名查询权限列表
     *
     * @param username 用户名
     * @return 权限列表
     */
    List<Permission> selectPermissionsByUsername(@Param("username") String username);


}
