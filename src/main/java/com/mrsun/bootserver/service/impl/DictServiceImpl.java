package com.mrsun.bootserver.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrsun.bootserver.Respository.DictRepository;
import com.mrsun.bootserver.common.enums.ErrorEnum;
import com.mrsun.bootserver.common.exception.BusinessException;
import com.mrsun.bootserver.entity.Dict;
import com.mrsun.bootserver.service.DTO.DictDTO;
import com.mrsun.bootserver.service.DictService;
import com.mrsun.bootserver.service.mapper.DictMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author sunxiaogang
 * @since 2019-10-25 13:53
 */
@Slf4j
@Service
public class DictServiceImpl implements DictService {

    private final DictRepository dictRepository;
    private final DictMapper dictMapper;

    public DictServiceImpl(DictRepository dictRepository, DictMapper dictMapper) {
        this.dictRepository = dictRepository;
        this.dictMapper = dictMapper;
    }

    @Override
    public int create(DictDTO dictDTO) {
        log.debug("创建字典表: [{}]", dictDTO);
        Dict dict = this.dictMapper.toEntity(dictDTO);
        return this.dictRepository.insert(dict);
    }

    @Override
    public int updateById(DictDTO dictDTO) {
        log.debug("修改字典表: [{}]", dictDTO);
        return this.dictRepository.updateById(this.dictMapper.toEntity(dictDTO));
    }

    @Override
    public IPage findPage(DictDTO dictDTO, Integer page, Integer limit) {
        log.debug("分页查询字典表: [页数：{}， 页大小: {}]", page, limit);
        LambdaQueryWrapper<Dict> qw = new LambdaQueryWrapper<>(this.dictMapper.toEntity(dictDTO));
        qw.orderByDesc(Dict::getCreateTime);

        Page<Dict> dictPage = new Page<>(page, limit);
        IPage iPage = dictRepository.selectPage(dictPage, qw);
        List<DictDTO> dictDTOs = this.dictMapper.toDto(iPage.getRecords());
        iPage.setRecords(dictDTOs);
        return iPage;
    }

    @Override
    public Optional<DictDTO> findOneById(Integer id) {
        return Optional.ofNullable(this.dictRepository.selectById(id))
                .map(this.dictMapper::toDto);
    }

    @Override
    public int deleteById(Integer id) {
        log.debug("删除字典:{}", id);
        Dict dict = this.dictRepository.selectById(id);
        if (dict != null && StrUtil.equals("system", dict.getCreateBy())) {
            throw new BusinessException(ErrorEnum.SYSTEM_CONFIG_DO_NOT_DELETE);
        }
        if (hasChild(id)) {
            throw new BusinessException(ErrorEnum.HAS_CHILDREN_CONFIG_ERROR);
        }
        return dictRepository.deleteById(id);
    }

    @Override
    public List<DictDTO> findAll(Integer parentId) {
        log.debug("根据父id查询全部字典表:{}", parentId);
        List<DictDTO> dictDTOS = this.dictMapper.toDto(this.dictRepository
                .selectList(new LambdaQueryWrapper<Dict>()
                        .eq(parentId != null, Dict::getParentId, parentId)));
        if (CollectionUtil.isNotEmpty(dictDTOS)) {
            dictDTOS.forEach(dictDTO -> {
                dictDTO.setHasChildren(hasChild(dictDTO.getId()));
            });
        }
        return dictDTOS;
    }

    /**
     * 是否有下级
     *
     * @param id 父id
     * @return
     */
    private Boolean hasChild(Integer id) {
        return this.dictRepository.selectCount(new LambdaQueryWrapper<Dict>()
                .eq(Dict::getParentId, id)) > 0;
    }
}
