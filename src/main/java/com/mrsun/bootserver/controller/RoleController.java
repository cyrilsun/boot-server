package com.mrsun.bootserver.controller;

import com.mrsun.bootserver.common.Result;
import com.mrsun.bootserver.service.DTO.RoleDTO;
import com.mrsun.bootserver.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 角色控制器
 *
 * @author sunxiaogang
 */
@Slf4j
@RestController
@RequestMapping(value = "/api")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * 创建角色
     *
     * @param roleDTO 参数
     * @return 创建结果
     */
    @PostMapping(value = "/roles")
    public Result<?> create(@RequestBody @Validated RoleDTO roleDTO) {
        log.debug("请求创建角色:{}", roleDTO);
        return Result.success(this.roleService.createRole(roleDTO));
    }

    /**
     * 修改角色
     *
     * @param roleDTO 参数
     * @return 修改结果
     */
    @PutMapping(value = "/roles")
    public Result<?> update(@RequestBody @Validated RoleDTO roleDTO) {
        log.debug("请求修改角色:{}", roleDTO);
        return Result.success(this.roleService.updateRole(roleDTO));
    }

    /**
     * 分页查询角色列表
     *
     * @param roleDTO 参数
     * @param page    第几页
     * @param limit   每页多少条记录
     * @return
     */
    @PostMapping(value = "/roles/page", params = {"page", "limit"})
    public Result<?> findRoles(@RequestBody RoleDTO roleDTO, Integer page, Integer limit) {
        log.debug("请求分页查询角色列表,参数:{},分页:{},{}", roleDTO, page, limit);
        return Result.success(this.roleService.findPage(roleDTO, page, limit));
    }

    /**
     * 删除角色
     *
     * @param id 角色id
     * @return 删除结果
     */
    @DeleteMapping(value = "/roles/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        log.debug("请求删除角色:{}", id);
        return Result.success(this.roleService.deleteRole(id));
    }

    /**
     * 查询所有角色列表
     *
     * @return 角色列表
     */
    @GetMapping(value = "/roles/list")
    public Result<?> selectRoles() {
        log.debug("请求查询所有角色列表");
        return Result.success(this.roleService.selectRoles());
    }
}
