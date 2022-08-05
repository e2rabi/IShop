package com.errabi.ishop.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "PRODUCT_EXPERIENCE")
public class ProductExperience {
    @Id
    private UUID id;
    private String reviewTitle;
    private String reviewBody;
    private String productId;

}
