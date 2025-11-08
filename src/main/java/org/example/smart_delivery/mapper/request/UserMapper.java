package org.example.smart_delivery.mapper.request;

import org.example.smart_delivery.dto.request.UserDTO;
import org.example.smart_delivery.entity.User;
import org.example.smart_delivery.mapper.RefMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = RefMapper.class)
public interface UserMapper {
    UserDTO toDto(User entity);
    User toEntity(UserDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UserDTO dto, @MappingTarget User entity);
}
