package com.mrsun.bootserver.controller;

import com.mrsun.bootserver.common.Result;
import com.mrsun.bootserver.service.DTO.PasswordGroupDTO;
import com.mrsun.bootserver.service.PasswordGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 密码分组控制器
 *
 * @author sunxiaogang
 **/
@Slf4j
@RestController
@RequestMapping("/api")
public class PasswordGroupController {

    private final PasswordGroupService passwordGroupService;

    public PasswordGroupController(PasswordGroupService passwordGroupService) {
        this.passwordGroupService = passwordGroupService;
    }

    /**
     * 添加密码分组
     *
     * @param passwordGroupDTO 添加密码分组数据
     * @return 创建的密码分组
     */
    @PostMapping("/password-groups")
    public Result<?> addPasswordGroup(@RequestBody @Valid PasswordGroupDTO passwordGroupDTO) {
        log.debug("请求添加密码分组:[{}]", passwordGroupDTO);
        return Result.success(this.passwordGroupService.create(passwordGroupDTO));
    }

    /**
     * 更新密码分组
     *
     * @param passwordGroupDTO 更新密码分组数据
     * @return 更新后的密码分组
     */
    @PutMapping("/password-groups")
    public Result<?> updatePasswordGroup(@RequestBody PasswordGroupDTO passwordGroupDTO) {
        log.debug("请求修改密码分组:[{}]", passwordGroupDTO);
        return Result.success(passwordGroupService.updateById(passwordGroupDTO));
    }

    /**
     * 查询密码分组详情
     *
     * @param id 密码分组id
     * @return 密码分组信息
     */
    @GetMapping("/password-groups/{id}")
    public Result<?> getPasswordGroup(@PathVariable Long id) {
        log.debug("请求查询密码分组详情:[{}]", id);
        return Result.success(this.passwordGroupService.findOneById(id));
    }

    /**
     * 分页查询密码分组列表
     *
     * @param passwordGroupDTO 查询条件
     * @param page             分页参数
     * @param limit            分页参数
     * @return 分页
     */
    @PostMapping(value = "/password-groups/page", params = {"page", "limit"})
    public Result<?> findAllPasswordGroup(@RequestBody PasswordGroupDTO passwordGroupDTO, Integer page, Integer limit) {
        log.debug("请求分页查询密码分组列表:[条件: {}, 分页参数:{},{}]", passwordGroupDTO, page, limit);
        return Result.success(passwordGroupService.findPage(passwordGroupDTO, page, limit));
    }

    /**
     * 删除密码分组
     *
     * @param id 密码分组id
     * @return 删除结果
     */
    @DeleteMapping("/password-groups/{id}")
    public Result<?> deletePasswordGroup(@PathVariable Long id) {
        log.debug("请求删除密码分组:[{}]", id);
        return Result.success(this.passwordGroupService.deleteById(id));
    }

    @GetMapping("/password-groups/list")
    public Result<?> selectAllPasswordGroups(){
        log.debug("请求查询所有分组列表");
        return Result.success(this.passwordGroupService.selectAllPasswordGroups());
    }

}
