package com.errabi.ishop.stub;

import com.errabi.common.model.CategoryDto;
import com.errabi.ishop.entities.Category;

import java.util.UUID;

public class CategoryStub {

    private final static UUID id = UUID.randomUUID();

    public static CategoryDto getParentCategory(){
       return CategoryDto.builder()
                                   .id(id)
                                   .description("Laptop description")
                                   .name("LAPTOP")
                                   .parentCategory(null)
                                   .build();
    }


    public static Category getEntity(){
       return Category.builder()
                    .id(id)
                    .name("LAPTOP")
                    .build();
    }
    public static UUID getParentCategoryId(){
        return id;
    }
}
