package com.mrsun.bootserver.service.mapper;

import com.mrsun.bootserver.entity.User;
import com.mrsun.bootserver.service.DTO.UserDTO;
import com.mrsun.bootserver.service.DTO.UserInfo;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户Mapper
 *
 * @author sunxiaogang
 */
@Service
@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User> {


    UserInfo toUserInfo(User user);

    List<UserInfo> toUserInfo(List<User> users);
}
