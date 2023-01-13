package com.mrsun.bootserver.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrsun.bootserver.Respository.PermissionRepository;
import com.mrsun.bootserver.Respository.RolePermissionRepository;
import com.mrsun.bootserver.common.enums.ErrorEnum;
import com.mrsun.bootserver.common.exception.BusinessException;
import com.mrsun.bootserver.entity.Permission;
import com.mrsun.bootserver.entity.RolePermission;
import com.mrsun.bootserver.security.IAuthenticationFacade;
import com.mrsun.bootserver.service.DTO.MenuMeta;
import com.mrsun.bootserver.service.DTO.PermissionDTO;
import com.mrsun.bootserver.service.PermissionService;
import com.mrsun.bootserver.service.mapper.PermissionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * description
 *
 * @author sunxiaogang
 */
@Slf4j
@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;
    private final RolePermissionRepository rolePermissionRepository;
    private final IAuthenticationFacade iAuthenticationFacade;


    /**
     * 构造器
     *
     * @param permissionRepository     permissionRepository
     * @param permissionMapper         permissionMapper
     * @param rolePermissionRepository rolePermissionRepository
     * @param iAuthenticationFacade
     */
    public PermissionServiceImpl(PermissionRepository permissionRepository,
                                 PermissionMapper permissionMapper,
                                 RolePermissionRepository rolePermissionRepository,
                                 IAuthenticationFacade iAuthenticationFacade) {
        this.permissionRepository = permissionRepository;
        this.permissionMapper = permissionMapper;
        this.rolePermissionRepository = rolePermissionRepository;
        this.iAuthenticationFacade = iAuthenticationFacade;
    }

    @Override
    public List<Permission> selectPermissionsByUserId(Integer userId) {
        log.debug("根据用户id查询权限列表:{}", userId);
        return permissionRepository.selectPermissionsByUserId(userId);
    }

    @Override
    public Integer createPermission(PermissionDTO permissionDTO) {
        log.debug("创建权限:{}", permissionDTO);
        this.checkPermissionNameAndCode(permissionDTO.getName(), permissionDTO.getCode(), null);
        return this.permissionRepository.insert(this.permissionMapper.toEntity(permissionDTO));
    }

    @Override
    public Integer updatePermission(PermissionDTO permissionDTO) {
        log.debug("更新权限:{}", permissionDTO);
        this.checkPermissionNameAndCode(permissionDTO.getName(), permissionDTO.getCode(), permissionDTO.getId());
        return this.permissionRepository.updateById(this.permissionMapper.toEntity(permissionDTO));
    }

    /**
     * 校验权限名称和编码
     *
     * @param name         权限名称
     * @param code         权限编码
     * @param permissionId 权限id
     */
    private void checkPermissionNameAndCode(String name, String code, Integer permissionId) {
        Permission permissionByName = this.permissionRepository.selectOne(new LambdaQueryWrapper<Permission>()
                .eq(Permission::getName, name));
        Permission permissionByCode = this.permissionRepository.selectOne(new LambdaQueryWrapper<Permission>()
                .eq(Permission::getCode, code));
        if (permissionId != null) {
            if (permissionByName != null && !permissionByName.getId().equals(permissionId)) {
                throw new BusinessException(ErrorEnum.PERMISSION_NAME_EXISTS_ERROR);
            }
            if (permissionByCode != null && !permissionByCode.getId().equals(permissionId)) {
                throw new BusinessException(ErrorEnum.PERMISSION_CODE_EXISTS_ERROR);
            }
        } else {
            if (permissionByName != null) {
                throw new BusinessException(ErrorEnum.PERMISSION_NAME_EXISTS_ERROR);
            }
            if (permissionByCode != null) {
                throw new BusinessException(ErrorEnum.PERMISSION_CODE_EXISTS_ERROR);
            }
        }
    }

    @Override
    public IPage findPage(PermissionDTO permissionDTO, Integer page, Integer limit) {
        log.debug("分页查询权限列表,参数:{},分页参数:{},{}", permissionDTO, page, limit);
        LambdaQueryWrapper<Permission> qw = new LambdaQueryWrapper<>(this.permissionMapper.toEntity(permissionDTO));
        qw.orderByAsc(Permission::getSortNo);
        Page<Permission> pageRole = new Page<>(page, limit);
        IPage iPage = this.permissionRepository.selectPage(pageRole, qw);
        iPage.setRecords(this.permissionMapper.toDto(iPage.getRecords()));
        return iPage;
    }

    @Override
    public Integer deletePermission(Integer id) {
        log.debug("删除权限:{}", id);
        Long rolePermissionCount = this.rolePermissionRepository.selectCount(new LambdaQueryWrapper<RolePermission>()
                .eq(RolePermission::getPermissionId, id));
        if (rolePermissionCount > 0) {
            throw new BusinessException(ErrorEnum.PERMISSION_USED_ERROR);
        }
        return this.permissionRepository.deleteById(id);
    }

    @Override
    public List<Tree<Integer>> selectPermissionTree() {
        log.debug("查询权限树");
        // 根据用户名查询权限列表
        String username = this.iAuthenticationFacade.getAuthentication().getName();
        List<Permission> permissions = this.permissionRepository.selectPermissionsByUsername(username);
        // 遍历权限列表
        if (CollectionUtil.isNotEmpty(permissions)) {
            //配置
            TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
            treeNodeConfig.setDeep(3); // 最大递归深度

            List<Tree<Integer>> treeNodes = TreeUtil.build(permissions, 0, treeNodeConfig,
                    (permission, tree) -> {
                        tree.setId(permission.getId());
                        tree.setParentId(permission.getParentId());
//                        tree.setWeight(treeNode.getWeight());
                        tree.setName(permission.getName());
                        // 扩展属性 ...
                        if (permission.getParentId().equals(0)) {

                            tree.putExtra("path", "/" + permission.getPath());
                            tree.putExtra("component", "Layout");
                        } else {
                            tree.putExtra("path", permission.getPath());
                            tree.putExtra("component", "() => import('@/views" + permission.getComponent() + "/index')");
//                            tree.putExtra("component", permission.getComponent()+"/index')");
                        }
//                        tree.putExtra("redirect", permission.getComponent());
                        MenuMeta meta = new MenuMeta();
                        meta.setTitle(permission.getTitle());
                        meta.setIcon(permission.getIcon());
//                        meta.setRoles(rolePermissionRepository.selectRolesByPermissionId(permission.getId()));
                        tree.putExtra("meta", meta);
//                        tree.putExtra("other", new Object());
                    });
            return treeNodes;
        }
        return null;
    }

    @Override
    public Object selectAllPermissions() {
        log.debug("查询所有权限列表");

        return null;
    }
}
