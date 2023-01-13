package com.mrsun.bootserver.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mrsun.bootserver.service.DTO.PasswordManagementDTO;

import java.util.List;
import java.util.Optional;

/**
 * 密码管理表服务接口类
 *
 * @author sunxiaogang
 */
public interface PasswordManagementService {

    /**
     * 创建密码管理表
     *
     * @param passwordManagementDTO 添加密码管理表
     * @return 添加后的密码管理表
     */
    Integer create(PasswordManagementDTO passwordManagementDTO);

    /**
     * 修改密码管理表
     *
     * @param passwordManagementDTO 修改密码管理表
     * @return 修改后的密码管理表
     */
    Integer updateById(PasswordManagementDTO passwordManagementDTO);

    /**
     * 分页查询密码管理表
     *
     * @param passwordManagementDTO 查询条件
     * @param limit                 分页参数
     * @param page                  分页参数
     * @return 分页
     */
    IPage findPage(PasswordManagementDTO passwordManagementDTO, Integer page, Integer limit);

    /**
     * 删除密码管理表
     *
     * @param id 密码管理表id
     */
    Integer deleteById(Long id);

    /**
     * 查询某个密码管理表
     *
     * @return 列表
     */
    Optional<PasswordManagementDTO> findOneById(Long id);

    /**
     * 查询全部密码管理表
     *
     * @return 列表
     */
    List<PasswordManagementDTO> findAll();

    /**
     * 查看密码
     *
     * @param passwordManagementDTO 参数
     * @return 结果
     */
    PasswordManagementDTO selectCode(PasswordManagementDTO passwordManagementDTO);

    /**
     * 重置密码
     *
     * @param passwordManagementDTO 参数
     * @return 结果
     */
    int updateCode(PasswordManagementDTO passwordManagementDTO);
}
