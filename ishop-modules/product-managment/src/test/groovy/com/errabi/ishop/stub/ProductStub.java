package com.errabi.ishop.stub;

import com.errabi.common.model.ProductDto;
import com.errabi.ishop.entities.Product;

import java.util.UUID;

public class ProductStub {

    private static UUID id = UUID.randomUUID();
    public static Product getEntity(){
        return Product.builder()
                .id(id)
                .description("test")
                .name("LAPTOP")
                .merchantId(UUID.randomUUID())
                .status("out of stock")
                .price(12.00)
                .build();
    }
    public static ProductDto getProduct(){
        return ProductDto.builder()
                .id(id)
                .categoryId(UUID.randomUUID())
                .description("test")
                .name("LAPTOP")
                .merchantId(UUID.randomUUID())
                .status("out of stock")
                .price(12.00)
                .build();
    }
}
