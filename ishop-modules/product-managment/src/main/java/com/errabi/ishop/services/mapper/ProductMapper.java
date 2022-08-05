package com.errabi.ishop.services.mapper;

import com.errabi.common.model.ProductDto;
import com.errabi.ishop.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring",nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ProductMapper {

    Product toEntity(ProductDto productDto);
    ProductDto toModel(Product product);
}
