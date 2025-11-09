package org.example.smart_delivery.mapper.response;

import org.example.smart_delivery.dto.response.UserRespDTO;
import org.example.smart_delivery.entity.User;
import org.example.smart_delivery.mapper.RefMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = RefMapper.class)
public interface UserRespMapper {
    UserRespDTO toRespDto(User entity);
    User toRespEntity(UserRespDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRespEntityFromDto(UserRespDTO dto, @MappingTarget User entity);
}
