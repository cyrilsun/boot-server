package com.mrsun.bootserver.Respository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrsun.bootserver.entity.Dict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 字典表数据库操作类
 *
 * @author sunxiaogang
 */
@Mapper
public interface DictRepository extends BaseMapper<Dict> {

    @Select("select dict_value from tb_dict where dict_code = #{0} limit 1")
    String selectValueByCode(String code);
}
