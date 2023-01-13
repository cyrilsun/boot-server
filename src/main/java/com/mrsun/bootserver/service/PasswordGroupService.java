package com.mrsun.bootserver.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mrsun.bootserver.service.DTO.PasswordGroupDTO;

import java.util.List;
import java.util.Optional;

/**
 * 密码分组服务接口类
 *
 * @author sunxiaogang
 * @since 2022/12/19 15:12
 */
public interface PasswordGroupService {

    /**
     * 创建密码分组
     *
     * @param passwordGroupDTO 添加密码分组
     * @return 添加后的密码分组
     */
    Integer create(PasswordGroupDTO passwordGroupDTO);

    /**
     * 修改密码分组
     *
     * @param passwordGroupDTO 修改密码分组
     * @return 修改后的密码分组
     */
    Integer updateById(PasswordGroupDTO passwordGroupDTO);

    /**
     * 分页查询密码分组
     *
     * @param passwordGroupDTO 查询条件
     * @param limit            分页参数
     * @param page             分页参数
     * @return 分页
     */
    IPage findPage(PasswordGroupDTO passwordGroupDTO, Integer page, Integer limit);

    /**
     * 删除密码分组
     *
     * @param id 密码分组id
     */
    Integer deleteById(Long id);

    /**
     * 查询某个密码分组
     *
     * @return 列表
     */
    Optional<PasswordGroupDTO> findOneById(Long id);

    /**
     * 查询全部密码分组
     *
     * @return 列表
     */
    List<PasswordGroupDTO> findAll();

    /**
     * 查询所有的分组列表
     *
     * @return
     */
    List<PasswordGroupDTO> selectAllPasswordGroups();
}
