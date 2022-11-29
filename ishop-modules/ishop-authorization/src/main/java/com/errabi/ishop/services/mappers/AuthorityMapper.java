package com.errabi.ishop.services.mappers;

import com.errabi.common.model.AuthorityDto;
import com.errabi.ishop.entities.Authority;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(componentModel = "spring",nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface AuthorityMapper {

    AuthorityDto toModel(Authority authority);
    Authority toEntity(AuthorityDto authorityDto);
    List<Authority> toEntity(List<AuthorityDto> authorityDto);
}
