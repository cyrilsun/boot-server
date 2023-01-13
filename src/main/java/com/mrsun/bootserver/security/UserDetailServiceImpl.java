package com.mrsun.bootserver.security;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mrsun.bootserver.Respository.UserRepository;
import com.mrsun.bootserver.entity.User;
import com.mrsun.bootserver.service.RoleService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户认证处理实现类
 *
 * @author sunxiaogang
 */
@Component("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {

    @Resource
    private UserRepository userRepository;
    @Resource
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username));
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        CustomerUserDetails customerUserDetails = new CustomerUserDetails();
        List<String> roleCodes = roleService.selectRoleCodesByUsername(username);
        if (CollectionUtil.isNotEmpty(roleCodes)) {
            customerUserDetails.setRoleCodes(roleCodes);
        }
        customerUserDetails.setCurrentUser(user);
        return customerUserDetails;
    }

}
