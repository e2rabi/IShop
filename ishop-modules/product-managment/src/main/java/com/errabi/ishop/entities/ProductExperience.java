package com.errabi.ishop.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCT_EXPERIENCE")
public class ProductExperience extends   BaseEntity{

    private String reviewTitle;
    private String reviewBody;
    private String productId;

}
