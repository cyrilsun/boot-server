package com.mrsun.bootserver.Respository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrsun.bootserver.entity.PasswordManagement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 密码管理表数据库操作类
 * @author sunxiaogang
 * @since 2022/12/19 14:29
*/
@Mapper
public interface PasswordManagementRepository extends BaseMapper<PasswordManagement> {

    @Select("select id, name, website, tip, group_id, remark from tb_password_management where id = #{0}")
    PasswordManagement selectDetailById(Long id);
}
