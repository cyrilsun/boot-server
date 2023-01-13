package com.mrsun.bootserver.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.CryptoException;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrsun.bootserver.Respository.PasswordManagementRepository;
import com.mrsun.bootserver.common.enums.ErrorEnum;
import com.mrsun.bootserver.common.exception.BusinessException;
import com.mrsun.bootserver.entity.PasswordManagement;
import com.mrsun.bootserver.security.IAuthenticationFacade;
import com.mrsun.bootserver.service.DTO.PasswordManagementDTO;
import com.mrsun.bootserver.service.PasswordManagementService;
import com.mrsun.bootserver.service.mapper.PasswordManagementMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

/**
 * @author sunxiaogang
 * @since 2019-10-25 13:53
 */
@Slf4j
@Service
public class PasswordManagementServiceImpl implements PasswordManagementService {

    private final PasswordManagementRepository passwordManagementRepository;
    private final PasswordManagementMapper passwordManagementMapper;
    private final IAuthenticationFacade iAuthenticationFacade;

    public PasswordManagementServiceImpl(PasswordManagementRepository passwordManagementRepository,
                                         PasswordManagementMapper passwordManagementMapper,
                                         IAuthenticationFacade iAuthenticationFacade) {
        this.passwordManagementRepository = passwordManagementRepository;
        this.passwordManagementMapper = passwordManagementMapper;
        this.iAuthenticationFacade = iAuthenticationFacade;
    }

    /**
     * 创建密码管理表
     *
     * @param passwordManagementDTO 添加密码管理表
     * @return 添加后的密码管理表
     */
    @Override
    public Integer create(PasswordManagementDTO passwordManagementDTO) {
        log.debug("创建密码管理表: [{}]", passwordManagementDTO);
        PasswordManagement passwordManagementDB = this.passwordManagementRepository.selectOne(new LambdaQueryWrapper<PasswordManagement>()
                .eq(PasswordManagement::getWebsite, passwordManagementDTO.getWebsite())
                .eq(PasswordManagement::getUsername, passwordManagementDTO.getUsername())
                .last("limit 1"));
        if (passwordManagementDB != null) {
            throw new BusinessException(ErrorEnum.WEBSITE_EXISTS);
        }
        // 设置参数
        this.setParam(passwordManagementDTO);
        PasswordManagement passwordManagement = this.passwordManagementMapper.toEntity(passwordManagementDTO);
        return this.passwordManagementRepository.insert(passwordManagement);
    }

    /**
     * 设置参数
     *
     * @param passwordManagementDTO
     */
    private void setParam(PasswordManagementDTO passwordManagementDTO) {
        // 密钥长度
        String salt = passwordManagementDTO.getSalt();
        int length = StrUtil.isEmpty(salt) ? 0 : salt.length();
        if (length > 0) {
            passwordManagementDTO.setCustom(1); // 自定义秘钥
        }
        // 128 192 256位
        if (length == 16 || length == 24 || length == 32) {
            // 设置密码
            passwordManagementDTO.setCode(Base64.encode(SecureUtil.aes(salt
                    .getBytes(StandardCharsets.UTF_8)).encrypt(passwordManagementDTO.getCode())));
            passwordManagementDTO.setSalt(Base64.encode(salt)); // 秘钥
        } else {
            String fill = UUID.fastUUID().toString(true).substring(length);
            String saltNew = StrUtil.isEmpty(salt) ? fill : salt + fill;
            // 设置密码
            passwordManagementDTO.setCode(Base64.encode(SecureUtil.aes((saltNew)
                    .getBytes(StandardCharsets.UTF_8)).encrypt(passwordManagementDTO.getCode())));
            // 设置填充
            passwordManagementDTO.setFill(Base64.encode(fill));
            passwordManagementDTO.setSalt(Base64.encode(saltNew)); // 秘钥
        }
        // 本地存储秘钥
        if (passwordManagementDTO.getLocalStorage() != null && passwordManagementDTO.getLocalStorage() == 1) {
            passwordManagementDTO.setSalt("");
        }
    }

    @Override
    public Integer updateById(PasswordManagementDTO passwordManagementDTO) {
        log.debug("修改密码管理表: [{}]", passwordManagementDTO);
        passwordManagementDTO.setCode(null);
        passwordManagementDTO.setSalt(null);
        passwordManagementDTO.setFill(null);
        return this.passwordManagementRepository.updateById(this.passwordManagementMapper.toEntity(passwordManagementDTO));
    }

    @Override
    public IPage findPage(PasswordManagementDTO passwordManagementDTO, Integer page, Integer limit) {
        log.debug("分页查询密码管理表: [页数：{}， 页大小: {}]", page, limit);
        LambdaQueryWrapper<PasswordManagement> qw = new LambdaQueryWrapper<>();
        qw.select(PasswordManagement::getId, PasswordManagement::getName, PasswordManagement::getWebsite,
                PasswordManagement::getLocalStorage, PasswordManagement::getUsername,
                PasswordManagement::getGroupId, PasswordManagement::getRemark, PasswordManagement::getCreateTime);
        qw.eq(PasswordManagement::getCreateBy, this.iAuthenticationFacade.getAuthentication().getName());
        qw.like(StrUtil.isNotEmpty(passwordManagementDTO.getName()), PasswordManagement::getName, passwordManagementDTO.getName());
        qw.like(StrUtil.isNotEmpty(passwordManagementDTO.getWebsite()), PasswordManagement::getWebsite, passwordManagementDTO.getWebsite());
        qw.eq(passwordManagementDTO.getGroupId() != null, PasswordManagement::getGroupId, passwordManagementDTO.getGroupId());
        qw.orderByDesc(PasswordManagement::getCreateTime);
        Page<PasswordManagement> passwordManagementPage = new Page<>(page, limit);
        IPage iPage = passwordManagementRepository.selectPage(passwordManagementPage, qw);
        List<PasswordManagementDTO> passwordManagementDTOs = this.passwordManagementMapper.toDto(iPage.getRecords());
        iPage.setRecords(passwordManagementDTOs);
        return iPage;
    }

    /**
     * 查询某个密码管理表
     *
     * @return 列表
     */
    @Override
    public Optional<PasswordManagementDTO> findOneById(Long id) {
        return Optional.ofNullable(this.passwordManagementRepository.selectDetailById(id))
                .map(this.passwordManagementMapper::toDto);
    }

    /**
     * 删除密码管理表
     *
     * @param id 密码管理表id
     */
    @Override
    public Integer deleteById(Long id) {
        return passwordManagementRepository.deleteById(id);
    }

    /**
     * 查询全部密码管理表
     *
     * @return 列表
     */
    @Override
    public List<PasswordManagementDTO> findAll() {
        log.debug("查询全部密码管理表");
        return this.passwordManagementMapper.toDto(this.passwordManagementRepository
                .selectList(new LambdaQueryWrapper<PasswordManagement>()));
    }

    @Override
    public PasswordManagementDTO selectCode(PasswordManagementDTO passwordManagementDTO) {
        log.debug("查看密码:{}", passwordManagementDTO);
        PasswordManagement passwordManagement = this.passwordManagementRepository.selectById(passwordManagementDTO.getId());
        if (passwordManagement != null) {
            PasswordManagementDTO result = new PasswordManagementDTO();
            result.setId(passwordManagementDTO.getId());
            result.setName(passwordManagement.getName());
            result.setWebsite(passwordManagement.getWebsite());
            result.setUsername(passwordManagement.getUsername());
            result.setTip(passwordManagement.getTip());
            result.setCustom(passwordManagement.getCustom());
            // 自定义秘钥且未传秘钥不解析密码
            if (passwordManagement.getCustom() == 1 && StrUtil.isEmpty(passwordManagementDTO.getSalt())) {
                return result;
            }
            try {
                // 后台存储秘钥
                if (passwordManagement.getLocalStorage() == 0) {
                    byte[] key = Base64.decode(passwordManagement.getSalt()); // 密钥
                    result.setCode(SecureUtil.aes(key).decryptStr(passwordManagement.getCode())); // 密码
                } else {
                    String salt = passwordManagementDTO.getSalt(); // 本地存储秘钥
                    if (StrUtil.isNotEmpty(salt)) {
                        if (StrUtil.isNotEmpty(passwordManagement.getFill())) {
                            salt = passwordManagementDTO.getSalt() + Base64.decodeStr(passwordManagement.getFill());
                        }
                        result.setCode(SecureUtil.aes(salt.getBytes(StandardCharsets.UTF_8)).decryptStr(passwordManagement.getCode()));
                    }
                }
            } catch (Exception e) {
                if (e instanceof CryptoException) {
                    throw new BusinessException(ErrorEnum.PASSWORD_SALT_ERROR);
                }
            }
            return result;
        } else {
            throw new BusinessException(ErrorEnum.DATA_NOT_EXISTS);
        }
    }

    @Override
    public int updateCode(PasswordManagementDTO passwordManagementDTO) {
        log.debug("重置密码:{}", passwordManagementDTO);
        if (StrUtil.isEmpty(passwordManagementDTO.getCode())) {
            throw new BusinessException(ErrorEnum.PASSWORD_NOT_NULL);
        }
        this.setParam(passwordManagementDTO);
        return this.passwordManagementRepository.updateById(this.passwordManagementMapper.toEntity(passwordManagementDTO));
    }
}
