package com.mrsun.bootserver.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mrsun.bootserver.entity.User;
import com.mrsun.bootserver.service.DTO.UserDTO;
import com.mrsun.bootserver.service.DTO.UserInfo;

/**
 * 用户服务
 *
 * @author sunxiaogang
 */
public interface UserService {

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    User findUserByUserName(String username);

    /**
     * 查询用户信息
     *
     * @return 用户信息
     */
    UserInfo getUserInfo();

    /**
     * 创建用户
     *
     * @param userDTO 参数
     * @return 结果
     */
    Integer createUser(UserDTO userDTO);

    /**
     * 更新用户
     *
     * @param userDTO 用户参数
     * @return 更新结果
     */
    Integer updateUser(UserDTO userDTO);

    /**
     * 分页查询用户列表
     *
     * @param userDTO 参数
     * @param page    第几页
     * @param limit   每页多少条记录
     * @return 用户列表
     */
    IPage findPage(UserDTO userDTO, Integer page, Integer limit);


    /**
     * 删除用户
     *
     * @param id 用户id
     * @return 删除结果
     */
    Integer deleteUser(Integer id);

    /**
     * 重置密码
     *
     * @param id 用户id
     * @return 结果
     */
    Integer resetPassword(Integer id);
}
