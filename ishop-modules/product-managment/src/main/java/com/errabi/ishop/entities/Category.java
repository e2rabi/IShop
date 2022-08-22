package com.errabi.ishop.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CATEGORY")
public class Category extends BaseEntity {

    private String name;
    private String description;
    @ManyToOne(optional=true, fetch= FetchType.LAZY)
    @JoinColumn(name="PARENT_CATEGORY_ID")
    private Category parentCategory;
}
