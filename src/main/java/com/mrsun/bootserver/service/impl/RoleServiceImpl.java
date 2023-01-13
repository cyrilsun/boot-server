package com.mrsun.bootserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrsun.bootserver.Respository.RoleRepository;
import com.mrsun.bootserver.Respository.UserRoleRepository;
import com.mrsun.bootserver.common.enums.ErrorEnum;
import com.mrsun.bootserver.common.exception.BusinessException;
import com.mrsun.bootserver.entity.Role;
import com.mrsun.bootserver.entity.UserRole;
import com.mrsun.bootserver.service.DTO.RoleDTO;
import com.mrsun.bootserver.service.RoleService;
import com.mrsun.bootserver.service.mapper.RoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色服务实现类
 *
 * @author sunxiaogang
 */
@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final UserRoleRepository userRoleRepository;

    /**
     * 构造器
     *
     * @param roleRepository     roleRepository
     * @param roleMapper         roleMapper
     * @param userRoleRepository userRoleRepository
     */
    public RoleServiceImpl(RoleRepository roleRepository,
                           RoleMapper roleMapper,
                           UserRoleRepository userRoleRepository) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public List<String> selectRoleCodesByUsername(String username) {
        log.debug("根据用户名查询角色编码列表:{}", username);
        return this.roleRepository.selectRoleCodesByUsername(username);
    }

    @Override
    public Integer createRole(RoleDTO roleDTO) {
        log.debug("创建角色:{}", roleDTO);
        this.checkRoleNameAndCode(roleDTO.getRoleName(), roleDTO.getRoleCode(), null);
        return this.roleRepository.insert(this.roleMapper.toEntity(roleDTO));
    }

    @Override
    public Integer updateRole(RoleDTO roleDTO) {
        log.debug("修改角色:{}", roleDTO);
        this.checkRoleNameAndCode(roleDTO.getRoleName(), roleDTO.getRoleCode(), roleDTO.getId());
        return this.roleRepository.updateById(this.roleMapper.toEntity(roleDTO));
    }

    /**
     * 校验角色名和角色编码
     *
     * @param roleName 角色名称
     * @param roleCode 角色编码
     * @param roleId   角色id
     */
    private void checkRoleNameAndCode(String roleName, String roleCode, Integer roleId) {
        Role roleByName = this.roleRepository.selectOne(new LambdaQueryWrapper<Role>()
                .eq(Role::getRoleName, roleName));
        Role roleByCode = this.roleRepository.selectOne(new LambdaQueryWrapper<Role>()
                .eq(Role::getRoleCode, roleCode));
        if (roleId != null) {
            if (roleByName != null && !roleByName.getId().equals(roleId)) {
                throw new BusinessException(ErrorEnum.ROLE_NAME_EXISTS_ERROR);
            }
            if (roleByCode != null && !roleByCode.getId().equals(roleId)) {
                throw new BusinessException(ErrorEnum.ROLE_CODE_EXISTS_ERROR);
            }
        } else {
            if (roleByName != null) {
                throw new BusinessException(ErrorEnum.ROLE_NAME_EXISTS_ERROR);
            }
            if (roleByCode != null) {
                throw new BusinessException(ErrorEnum.ROLE_CODE_EXISTS_ERROR);
            }
        }
    }

    @Override
    public IPage findPage(RoleDTO roleDTO, Integer page, Integer limit) {
        log.debug("分页查询角色列表,参数:{},分页参数:{},{}", roleDTO, page, limit);
        LambdaQueryWrapper<Role> qw = new LambdaQueryWrapper<>(this.roleMapper.toEntity(roleDTO));
        Page<Role> pageRole = new Page<>(page, limit);
        IPage iPage = this.roleRepository.selectPage(pageRole, qw);
        iPage.setRecords(this.roleMapper.toDto(iPage.getRecords()));
        return iPage;
    }

    @Override
    public Integer deleteRole(Integer id) {
        log.debug("删除角色:{}", id);
        Long userRoleCount = this.userRoleRepository.selectCount(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getRoleId, id));
        if (userRoleCount > 0) {
            throw new BusinessException(ErrorEnum.ROLE_USED_ERROR);
        }
        return this.roleRepository.deleteById(id);
    }

    @Override
    public List<RoleDTO> selectRoles() {
        log.debug("查询所有角色列表");
        return this.roleMapper.toDto(this.roleRepository.selectList(null));
    }
}
