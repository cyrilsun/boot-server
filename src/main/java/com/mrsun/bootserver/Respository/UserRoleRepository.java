package com.mrsun.bootserver.Respository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrsun.bootserver.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户角色关联Repository
 *
 * @author sunxiaogang
 */
@Mapper
public interface UserRoleRepository extends BaseMapper<UserRole> {
}
