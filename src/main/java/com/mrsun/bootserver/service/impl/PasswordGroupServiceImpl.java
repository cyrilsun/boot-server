package com.mrsun.bootserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrsun.bootserver.Respository.PasswordGroupRepository;
import com.mrsun.bootserver.common.enums.ErrorEnum;
import com.mrsun.bootserver.common.exception.BusinessException;
import com.mrsun.bootserver.entity.PasswordGroup;
import com.mrsun.bootserver.security.IAuthenticationFacade;
import com.mrsun.bootserver.service.DTO.PasswordGroupDTO;
import com.mrsun.bootserver.service.PasswordGroupService;
import com.mrsun.bootserver.service.mapper.PasswordGroupMapper;
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
public class PasswordGroupServiceImpl implements PasswordGroupService {

    private final PasswordGroupRepository passwordGroupRepository;
    private final PasswordGroupMapper passwordGroupMapper;
    private final IAuthenticationFacade iAuthenticationFacade;

    public PasswordGroupServiceImpl(PasswordGroupRepository passwordGroupRepository,
                                    PasswordGroupMapper passwordGroupMapper,
                                    IAuthenticationFacade iAuthenticationFacade) {
        this.passwordGroupRepository = passwordGroupRepository;
        this.passwordGroupMapper = passwordGroupMapper;
        this.iAuthenticationFacade = iAuthenticationFacade;
    }

    @Override
    public Integer create(PasswordGroupDTO passwordGroupDTO) {
        log.debug("创建密码分组: [{}]", passwordGroupDTO);
        Long passwordGroupCount = this.passwordGroupRepository.selectCount(new LambdaQueryWrapper<PasswordGroup>()
                .eq(PasswordGroup::getName, passwordGroupDTO.getName())
                .eq(PasswordGroup::getCreateBy, iAuthenticationFacade.getAuthentication().getName()));
        if (passwordGroupCount > 0) {
            throw new BusinessException(ErrorEnum.PASSWORD_GROUP_NAME_EXISTS);
        }
        PasswordGroup passwordGroup = this.passwordGroupMapper.toEntity(passwordGroupDTO);
        return this.passwordGroupRepository.insert(passwordGroup);
    }

    @Override
    public Integer updateById(PasswordGroupDTO passwordGroupDTO) {
        log.debug("修改密码分组: [{}]", passwordGroupDTO);
        PasswordGroup updatePasswordGroup = this.passwordGroupMapper.toEntity(passwordGroupDTO);
        return this.passwordGroupRepository.updateById(updatePasswordGroup);
    }

    @Override
    public IPage findPage(PasswordGroupDTO passwordGroupDTO, Integer page, Integer limit) {
        log.debug("分页查询密码分组: [页数：{}， 页大小: {}]", page, limit);
        LambdaQueryWrapper<PasswordGroup> qw = new LambdaQueryWrapper<>();
        qw.eq(PasswordGroup::getCreateBy, this.iAuthenticationFacade.getAuthentication().getName());
        qw.orderByDesc(PasswordGroup::getCreateTime);

        Page<PasswordGroup> passwordGroupPage = new Page<>(page, limit);
        IPage iPage = passwordGroupRepository.selectPage(passwordGroupPage, qw);
        List<PasswordGroupDTO> passwordGroupDTOs = this.passwordGroupMapper.toDto(iPage.getRecords());
        iPage.setRecords(passwordGroupDTOs);
        return iPage;
    }

    @Override
    public Optional<PasswordGroupDTO> findOneById(Long id) {
        return Optional.ofNullable(this.passwordGroupRepository.selectById(id))
                .map(this.passwordGroupMapper::toDto);
    }

    @Override
    public Integer deleteById(Long id) {
        return passwordGroupRepository.deleteById(id);
    }

    /**
     * 查询全部密码分组
     *
     * @return 列表
     */
    @Override
    public List<PasswordGroupDTO> findAll() {
        log.debug("查询全部密码分组");
        return this.passwordGroupMapper.toDto(this.passwordGroupRepository
                .selectList(new LambdaQueryWrapper<PasswordGroup>()));
    }

    @Override
    public List<PasswordGroupDTO> selectAllPasswordGroups() {
        log.debug("查询所有的分组列表");
        String username = this.iAuthenticationFacade.getAuthentication().getName();
        List<PasswordGroup> passwordGroups = this.passwordGroupRepository
                .selectList(new LambdaQueryWrapper<PasswordGroup>()
                        .eq(PasswordGroup::getCreateBy, username));
        return this.passwordGroupMapper.toDto(passwordGroups);
    }
}
