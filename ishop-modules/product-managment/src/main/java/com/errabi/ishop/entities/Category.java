package com.errabi.ishop.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "CATEGORY")
public class Category {
    @Id
    private UUID id;
    private String name;
    private String description;
}
