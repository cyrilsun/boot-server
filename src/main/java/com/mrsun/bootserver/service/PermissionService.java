package com.mrsun.bootserver.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mrsun.bootserver.entity.Permission;
import com.mrsun.bootserver.service.DTO.PermissionDTO;

import java.util.List;

/**
 * 权限服务类
 *
 * @author sunxiaogang
 */
public interface PermissionService {

    /**
     * 根据用户id查询权限列表
     *
     * @param userId 用户id
     * @return 权限列表
     */
    List<Permission> selectPermissionsByUserId(Integer userId);

    /**
     * 创建权限
     *
     * @param permissionDTO 参数
     * @return 结果
     */
    Integer createPermission(PermissionDTO permissionDTO);

    /**
     * 更新权限
     *
     * @param permissionDTO 参数
     * @return 结果
     */
    Integer updatePermission(PermissionDTO permissionDTO);

    /**
     * 分页查询权限列表
     *
     * @param permissionDTO 参数
     * @param page          第几页
     * @param limit         每页多少条记录
     * @return 权限分页列表
     */
    IPage findPage(PermissionDTO permissionDTO, Integer page, Integer limit);

    /**
     * 删除权限
     *
     * @param id 权限id
     * @return 结果
     */
    Integer deletePermission(Integer id);

    /**
     * 查询权限树
     *
     * @return
     */
    List<Tree<Integer>> selectPermissionTree();

    /**
     * 查询所有权限列表
     * @return 结果
     */
    Object selectAllPermissions();
}
