package com.mrsun.bootserver.controller;

import com.mrsun.bootserver.common.Result;
import com.mrsun.bootserver.service.DTO.PermissionDTO;
import com.mrsun.bootserver.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 权限控制器
 *
 * @author sunxiaogang
 */
@Slf4j
@RestController
@RequestMapping(value = "/api")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    /**
     * 创建权限
     *
     * @param permissionDTO 参数
     * @return 新增结果
     */
    @PostMapping(value = "/permissions")
    public Result<?> create(@RequestBody @Validated PermissionDTO permissionDTO) {
        log.debug("请求创建权限:{}", permissionDTO);
        return Result.success(this.permissionService.createPermission(permissionDTO));
    }

    /**
     * 更新权限
     *
     * @param permissionDTO 参数
     * @return 更新结果
     */
    @PutMapping(value = "/permissions")
    public Result<?> update(@RequestBody @Validated PermissionDTO permissionDTO) {
        log.debug("请求更新权限:{}", permissionDTO);
        return Result.success(this.permissionService.updatePermission(permissionDTO));
    }

    /**
     * 分页查询权限列表
     *
     * @param permissionDTO 参数
     * @param page          第几页
     * @param limit         每页多少条记录
     * @return 权限列表
     */
    @PostMapping(value = "/permissions/page", params = {"page", "limit"})
    public Result<?> findUsers(@RequestBody PermissionDTO permissionDTO, Integer page, Integer limit) {
        log.debug("请求分页查询权限列表,参数:{},分页参数:[{},{}]", permissionDTO, page, limit);
        return Result.success(this.permissionService.findPage(permissionDTO, page, limit));
    }

    /**
     * 删除权限
     *
     * @param id 权限id
     * @return 删除结果
     */
    @DeleteMapping(value = "/permissions/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        log.debug("请求删除权限:{}", id);
        return Result.success(this.permissionService.deletePermission(id));
    }

    /**
     * 查询权限树
     *
     * @return
     */
    @GetMapping(value = "/permissions/tree")
    public Result<?> selectPermissionTree() {
        log.debug("请求查询权限树");
        return Result.success(this.permissionService.selectPermissionTree());
    }

    /**
     * 查询所有权限列表
     * @return
     */
    @GetMapping(value = "/permissions/list")
    public Result<?> selectAllPermissions() {
        log.debug("请求查询所有权限列表");
        return Result.success(this.permissionService.selectAllPermissions());
    }
}
