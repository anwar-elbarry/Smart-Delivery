package org.example.smart_delivery.mapper.response;

import org.example.smart_delivery.dto.response.UserRespDTO;
import org.example.smart_delivery.entity.User;
import org.example.smart_delivery.mapper.RoleMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring",uses = RoleMapper.class)
public interface UserRespMapper {
    UserRespDTO toRespDto(User entity);
    @Mapping(target = "verificationCodeExpired", ignore = true)
    @Mapping(target = "verificationCode", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toRespEntity(UserRespDTO dto);
}
