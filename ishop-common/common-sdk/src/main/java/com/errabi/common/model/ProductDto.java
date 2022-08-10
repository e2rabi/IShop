package com.errabi.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private UUID id;
    private String name ;
    private String description;
    private String imageBase64;
    private Double price ;
    private String status ;
    private UUID categoryId;
    private UUID merchantId ;
}
