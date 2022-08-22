package com.errabi.ishop.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "MERCHANT")
public class Merchant extends BaseEntity {

    private String name;
    private String activity;
    private String description;
    private String address;
    private String phone ;
    private String email;
}
