package com.errabi.ishop.entities;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "CATEGORY")
public class Category {
    @Id
    private UUID id;
    private String name;
    private String description;
    @ManyToOne(optional=true, fetch= FetchType.LAZY)
    @JoinColumn(name="PARENT_CATEGORY_ID")
    private Category parentCategory;

    @OneToMany(mappedBy="parentCategory")
    private List<Category> categories;
}
