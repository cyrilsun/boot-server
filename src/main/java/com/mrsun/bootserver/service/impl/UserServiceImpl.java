package com.mrsun.bootserver.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrsun.bootserver.Respository.DictRepository;
import com.mrsun.bootserver.Respository.RoleRepository;
import com.mrsun.bootserver.Respository.UserRepository;
import com.mrsun.bootserver.Respository.UserRoleRepository;
import com.mrsun.bootserver.common.enums.ErrorEnum;
import com.mrsun.bootserver.common.exception.BusinessException;
import com.mrsun.bootserver.entity.User;
import com.mrsun.bootserver.entity.UserRole;
import com.mrsun.bootserver.service.DTO.UserDTO;
import com.mrsun.bootserver.service.DTO.UserInfo;
import com.mrsun.bootserver.service.UserService;
import com.mrsun.bootserver.service.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户服务实现类
 *
 * @author sunxiaogang
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;
    private final DictRepository dictRepository;

    /**
     * 构造器
     *
     * @param userRepository     userRepository
     * @param userMapper         userInfoMapper
     * @param roleRepository     roleRepository
     * @param passwordEncoder    passwordEncoder
     * @param userRoleRepository userRoleRepository
     * @param dictRepository
     */
    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder passwordEncoder,
                           UserRoleRepository userRoleRepository,
                           DictRepository dictRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
        this.dictRepository = dictRepository;
    }

    @Override
    public User findUserByUserName(String username) {
        log.debug("根据用户名查询用户信息:{}", username);
        return this.userRepository.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username));
    }

    @Override
    public UserInfo getUserInfo() {
        log.debug("查询用户信息");
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userRepository.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username));
        // 查询角色编码列表
        List<String> roleCodes = this.roleRepository.selectRoleCodesByUsername(username);
        user.setRoles(roleCodes);
        return this.userMapper.toUserInfo(user);
    }

    @Override
    @Transactional
    public Integer createUser(UserDTO userDTO) {
        log.debug("创建用户:{}", userDTO);
        this.checkUsername(userDTO.getUsername(), null);
        User user = this.userMapper.toEntity(userDTO);
        user.setPassword(this.passwordEncoder.encode(this.dictRepository.selectValueByCode("init_password")));
        int insertResult = this.userRepository.insert(user);
        // 新增用户、角色关联关系
        userDTO.getRoleIds().forEach(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(roleId);
            this.userRoleRepository.insert(userRole);
        });
        return insertResult;
    }

    @Override
    @Transactional
    public Integer updateUser(UserDTO userDTO) {
        log.debug("更新用户:{}", userDTO);
        Integer userId = userDTO.getId();
        this.checkUsername(userDTO.getUsername(), userId);
        if (CollectionUtil.isNotEmpty(userDTO.getRoleIds())) {
            // 删除用户角色关联
            this.userRoleRepository.delete(new LambdaQueryWrapper<UserRole>()
                    .eq(UserRole::getUserId, userId));
            // 新增用户角色关联
            userDTO.getRoleIds().forEach(roleId -> {
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                this.userRoleRepository.insert(userRole);
            });
        }
        return this.userRepository.updateById(this.userMapper.toEntity(userDTO));
    }

    /**
     * 校验用户名
     *
     * @param username 用户名
     * @param userId   用户id
     */
    private void checkUsername(String username, Integer userId) {
        User user = this.userRepository.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        if (userId != null) {
            if (user != null && !user.getId().equals(userId)) {
                throw new BusinessException(ErrorEnum.USERNAME_EXISTS_ERROR);
            }
        } else {
            if (user != null) {
                throw new BusinessException(ErrorEnum.USERNAME_EXISTS_ERROR);
            }
        }
    }

    @Override
    public IPage findPage(UserDTO userDTO, Integer page, Integer limit) {
        log.debug("分页查询用户列表:参数:{},分页参数[{},{}]", userDTO, page, limit);
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>(this.userMapper.toEntity(userDTO));
        qw.orderByDesc(User::getCreateTime);
        Page<User> pageUser = new Page<>(page, limit);
        IPage iPage = userRepository.selectPage(pageUser, qw);
        List<UserDTO> userDTOList = this.userMapper.toDto(iPage.getRecords());
        if (CollectionUtil.isNotEmpty(userDTOList)) {
            userDTOList.forEach(u -> {
                u.setRoleIds(this.roleRepository.selectRoleIdsByUserId(u.getId()));
            });
        }
        iPage.setRecords(userDTOList);
        return iPage;
    }

    @Override
    @Transactional
    public Integer deleteUser(Integer id) {
        log.debug("删除用户:{}", id);
        if (id.equals(1)) {
            throw new BusinessException(ErrorEnum.SUPER_ADMIN_DELETE_ERROR);
        }
        // 删除用户角色关联
        this.userRoleRepository.delete(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, id));
        return this.userRepository.deleteById(id);
    }

    @Override
    public Integer resetPassword(Integer id) {
        log.debug("重置密码:{}", id);
        String encode = this.passwordEncoder.encode(this.dictRepository.selectValueByCode("init_password"));
        User user = new User();
        user.setId(id);
        user.setPassword(encode);
        return this.userRepository.updateById(user);
    }
}
