package com.mrsun.bootserver.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mrsun.bootserver.service.DTO.DictDTO;

import java.util.List;
import java.util.Optional;

/**
 * 字典表服务接口类
 *
 * @author sunxiaogang
 */
public interface DictService {

    /**
     * 创建字典表
     *
     * @param dictDTO 添加字典表
     * @return 添加后的字典表
     */
    int create(DictDTO dictDTO);

    /**
     * 修改字典表
     *
     * @param dictDTO 修改字典表
     * @return 修改后的字典表
     */
    int updateById(DictDTO dictDTO);

    /**
     * 分页查询字典表
     *
     * @param dictDTO    查询条件
     * @param limit   分页参数
     * @param page 分页参数
     * @return 分页
     */
    IPage findPage(DictDTO dictDTO, Integer page, Integer limit);

    /**
     * 删除字典表
     *
     * @param id 字典表id
     */
    int deleteById(Integer id);

    /**
     * 查询某个字典表
     *
     * @return 列表
     */
    Optional<DictDTO> findOneById(Integer id);

    /**
     * 查询全部字典表
     * @param parentId 父id
     *
     * @return 列表
     */
    List<DictDTO> findAll(Integer parentId);
}
