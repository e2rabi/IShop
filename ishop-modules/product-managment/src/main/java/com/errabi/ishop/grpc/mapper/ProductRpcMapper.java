package com.errabi.ishop.grpc.mapper;

import com.errabi.common.model.ProductDto;
import com.errabi.ishop.ProductRequest;
import com.errabi.ishop.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductRpcMapper {
    ProductDto toModel(ProductRequest request);
    ProductResponse toRpc(ProductDto productDto);
}
