package com.mrsun.bootserver.Respository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrsun.bootserver.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Repository
 *
 * @author sunxiaogang
 */
@Mapper
public interface UserRepository extends BaseMapper<User> {
}
