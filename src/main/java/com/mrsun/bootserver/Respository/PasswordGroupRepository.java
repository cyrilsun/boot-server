package com.mrsun.bootserver.Respository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrsun.bootserver.entity.PasswordGroup;
import org.apache.ibatis.annotations.Mapper;

/**
 * 密码分组数据库操作类
 *
 * @author sunxiaogang
 */
@Mapper
public interface PasswordGroupRepository extends BaseMapper<PasswordGroup> {

}
