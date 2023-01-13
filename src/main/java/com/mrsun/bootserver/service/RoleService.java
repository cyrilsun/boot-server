package com.mrsun.bootserver.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mrsun.bootserver.service.DTO.RoleDTO;

import java.util.List;

/**
 * 角色服务
 *
 * @author sunxiaogang
 */
public interface RoleService {

    /**
     * 根据用户名查询角色编码列表
     *
     * @param username 用户名
     * @return 角色编码列表
     */
    List<String> selectRoleCodesByUsername(String username);

    /**
     * 创建角色
     *
     * @param roleDTO 参数
     * @return 创建结果
     */
    Integer createRole(RoleDTO roleDTO);

    /**
     * 修改角色
     *
     * @param roleDTO 参数
     * @return 修改结果
     */
    Integer updateRole(RoleDTO roleDTO);

    /**
     * 分页查询角色列表
     *
     * @param roleDTO 参数
     * @param page    第几页
     * @param limit   每页多少条记录
     * @return
     */
    IPage findPage(RoleDTO roleDTO, Integer page, Integer limit);

    /**
     * 删除角色
     *
     * @param id 角色id
     * @return 删除结果
     */
    Integer deleteRole(Integer id);

    /**
     * 查询所有角色列表
     *
     * @return 角色列表
     */
    List<RoleDTO> selectRoles();
}
