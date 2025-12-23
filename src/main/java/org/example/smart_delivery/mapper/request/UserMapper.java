package org.example.smart_delivery.mapper.request;

import org.example.smart_delivery.dto.request.UserDTO;
import org.example.smart_delivery.entity.User;
import org.example.smart_delivery.mapper.RefMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = RefMapper.class)
public interface UserMapper {
    @Mapping(target = "roleId", source = "role.id")
    UserDTO toDto(User entity);
    @Mapping(target = "verificationCodeExpired", ignore = true)
    @Mapping(target = "verificationCode", ignore = true)
    @Mapping(target = "role.id", source = "roleId")
    User toEntity(UserDTO dto);

    @Mapping(target = "role.id", source = "roleId")
    @Mapping(target = "verificationCodeExpired", ignore = true)
    @Mapping(target = "verificationCode", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UserDTO dto, @MappingTarget User entity);
}
