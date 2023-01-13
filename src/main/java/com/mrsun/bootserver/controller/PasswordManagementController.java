package com.mrsun.bootserver.controller;

import com.mrsun.bootserver.common.Result;
import com.mrsun.bootserver.service.DTO.PasswordManagementDTO;
import com.mrsun.bootserver.service.PasswordManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 密码管理表控制器
 *
 * @author sunxiaogang
 **/
@Slf4j
@RestController
@RequestMapping("/api")
public class PasswordManagementController {

    private final PasswordManagementService passwordManagementService;

    public PasswordManagementController(PasswordManagementService passwordManagementService) {
        this.passwordManagementService = passwordManagementService;
    }

    /**
     * 添加密码管理表
     *
     * @param passwordManagementDTO 添加密码管理表数据
     * @return 创建的密码管理表
     */
    @PostMapping("/password-managements")
    public Result<?> addPasswordManagement(@RequestBody @Valid PasswordManagementDTO passwordManagementDTO) {
        log.debug("请求添加密码管理表:[{}]", passwordManagementDTO);
        return Result.success(this.passwordManagementService.create(passwordManagementDTO));
    }

    /**
     * 更新密码管理表
     *
     * @param passwordManagementDTO 更新密码管理表数据
     * @return 更新后的密码管理表
     */
    @PutMapping("/password-managements")
    public Result<?> updatePasswordManagement(@RequestBody PasswordManagementDTO passwordManagementDTO) {
        log.debug("请求修改密码管理表:[{}]", passwordManagementDTO);
        return Result.success(passwordManagementService.updateById(passwordManagementDTO));
    }

    /**
     * 查询密码管理表详情
     *
     * @param id 密码管理表id
     * @return 密码管理表信息
     */
    @GetMapping("/password-managements/{id}")
    public Result<?> getPasswordManagement(@PathVariable Long id) {
        log.debug("请求查询密码管理表详情:[{}]", id);
        return Result.success(this.passwordManagementService.findOneById(id));
    }

    /**
     * 分页查询密码管理表列表
     *
     * @param passwordManagementDTO 查询条件
     * @param limit                 分页参数
     * @param page                  分页参数
     * @return 分页
     */
    @PostMapping(value = "/password-managements/page", params = {"page", "limit"})
    public Result<?> findAllPasswordManagement(@RequestBody PasswordManagementDTO passwordManagementDTO, Integer page, Integer limit) {
        log.debug("请求分页查询密码管理表列表:[条件: {}, 分页参数:{},{}]", passwordManagementDTO, page, limit);
        return Result.success(passwordManagementService.findPage(passwordManagementDTO, page, limit));
    }

    /**
     * 删除密码管理表
     *
     * @param id 密码管理表id
     * @return 删除结果
     */
    @DeleteMapping("/password-managements/{id}")
    public Result<?> deletePasswordManagement(@PathVariable Long id) {
        log.debug("请求删除密码管理表:[{}]", id);
        return Result.success(this.passwordManagementService.deleteById(id));
    }

    /**
     * 查看密码
     *
     * @param passwordManagementDTO
     * @return
     */
    @PostMapping("/password-managements/code")
    public Result<?> selectCode(@RequestBody PasswordManagementDTO passwordManagementDTO) {
        log.debug("请求查看密码:{}", passwordManagementDTO);
        return Result.success(this.passwordManagementService.selectCode(passwordManagementDTO));
    }

    /**
     * 重置密码
     *
     * @param passwordManagementDTO
     * @return
     */
    @PutMapping("/password-managements/code")
    public Result<?> updateCode(@RequestBody PasswordManagementDTO passwordManagementDTO) {
        log.debug("请求重置密码:{}", passwordManagementDTO);
        return Result.success(this.passwordManagementService.updateCode(passwordManagementDTO));
    }
}
