package com.errabi.ishop.services.mappers;

import com.errabi.common.model.UserDto;
import com.errabi.ishop.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring",nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper {
    User toEntity(UserDto userDto);
    UserDto toModel(User user);
}
