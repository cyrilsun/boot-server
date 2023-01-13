package com.mrsun.bootserver.Respository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrsun.bootserver.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description
 *
 * @author sunxiaogang
 */
@Mapper
public interface RoleRepository extends BaseMapper<Role> {

    /**
     * 根据用户名查询角色编码列表
     *
     * @param username 用户名
     * @return 角色编码列表
     */
    List<String> selectRoleCodesByUsername(String username);

    /**
     * 根据用户id查询角色列表
     *
     * @param userId 用户id
     * @return 角色列表
     */
    List<Integer> selectRoleIdsByUserId(@Param("userId") Integer userId);
}
