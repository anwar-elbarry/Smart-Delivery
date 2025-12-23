package org.example.smart_delivery.mapper.response;

import org.example.smart_delivery.dto.response.UserRespDTO;
import org.example.smart_delivery.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserRespMapper {
    @Mapping(target = "roleName", source = "role.name")
    UserRespDTO toRespDto(User entity);
    @Mapping(target = "verificationCodeExpired", ignore = true)
    @Mapping(target = "verificationCode", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role.name", source = "roleName")
    User toRespEntity(UserRespDTO dto);
}
