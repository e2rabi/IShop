package com.errabi.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto extends AbstractRequest{
    private UUID id;
    private String name;
    private String description;
    @JsonIgnore
    private CategoryDto parentCategory;
}
