package com.mrsun.bootserver.controller;

import com.mrsun.bootserver.common.Result;
import com.mrsun.bootserver.service.DTO.UserDTO;
import com.mrsun.bootserver.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 *
 * @author sunxiaogang
 */
@Slf4j
@RestController
@RequestMapping(value = "/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 查询用户信息
     *
     * @return 用户信息
     */
    @GetMapping(value = "/users/info")
    public Result<?> getUserInfo() {
        log.debug("请求查询用户信息");
        return Result.success(this.userService.getUserInfo());
    }

    /**
     * 创建用户
     *
     * @param userDTO 参数
     * @return 新增结果
     */
    @PostMapping(value = "/users")
    public Result<?> create(@RequestBody @Validated UserDTO userDTO) {
        log.debug("请求创建用户:{}", userDTO);
        return Result.success(this.userService.createUser(userDTO));
    }

    /**
     * 更新用户
     *
     * @param userDTO 用户参数
     * @return 更新结果
     */
    @PutMapping(value = "/users")
    public Result<?> update(@RequestBody @Validated UserDTO userDTO) {
        log.debug("请求更新用户:{}", userDTO);
        return Result.success(this.userService.updateUser(userDTO));
    }

    /**
     * 分页查询用户列表
     *
     * @param userDTO 参数
     * @param page    第几页
     * @param limit   每页多少条记录
     * @return 用户列表
     */
    @PostMapping(value = "/users/page", params = {"page", "limit"})
    public Result<?> findUsers(@RequestBody UserDTO userDTO, Integer page, Integer limit) {
        log.debug("请求分页查询用户列表,参数:{},分页参数:[{},{}]", userDTO, page, limit);
        return Result.success(this.userService.findPage(userDTO, page, limit));
    }

    /**
     * 删除用户
     *
     * @param id 用户id
     * @return 删除结果
     */
    @DeleteMapping(value = "/users/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        log.debug("请求删除用户:{}", id);
        return Result.success(this.userService.deleteUser(id));
    }

    /**
     * 重置密码
     *
     * @param id 用户id
     * @return 结果
     */
    @PutMapping(value = "/users/password/{id}")
    public Result<?> resetPassword(@PathVariable Integer id) {
        log.debug("请求重置密码:{}", id);
        return Result.success(this.userService.resetPassword(id));
    }
}
