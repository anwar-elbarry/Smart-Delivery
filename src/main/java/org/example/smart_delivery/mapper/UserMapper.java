package org.example.smart_delivery.mapper;

import org.example.smart_delivery.dto.UserDTO;
import org.example.smart_delivery.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = RefMapper.class)
public interface UserMapper {
    UserDTO toDto(User entity);
    User toEntity(UserDTO dto);
}
