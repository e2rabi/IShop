package com.errabi.ishop.services.mappers;

import com.errabi.common.model.RoleDto;
import com.errabi.ishop.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring",nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface RoleMapper {

    RoleDto toModel(Role role);
    Role toEntity(RoleDto roleDto);
}
