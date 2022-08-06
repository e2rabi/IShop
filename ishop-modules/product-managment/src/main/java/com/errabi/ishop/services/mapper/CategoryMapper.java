package com.errabi.ishop.services.mapper;

import com.errabi.common.model.CategoryDto;
import com.errabi.ishop.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;


@Mapper(componentModel = "spring",nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CategoryMapper {
    Category toEntity(CategoryDto categoryDto);
    CategoryDto toModel(Category category);
}
