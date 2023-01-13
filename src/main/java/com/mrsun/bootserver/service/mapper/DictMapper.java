package com.mrsun.bootserver.service.mapper;

import com.mrsun.bootserver.entity.Dict;
import com.mrsun.bootserver.service.DTO.DictDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

/**
 * 字典表映射类
 *
 * @author sunxiaogang
 */
@Service
@Mapper(componentModel = "spring")
public interface DictMapper extends EntityMapper<DictDTO, Dict> {
}
