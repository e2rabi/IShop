package com.errabi.ishop.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "MERCHANT")
public class Merchant {
    @Id
    private UUID id;
    private String name;
    private String activity;
    private String description;
    private String address;
    private String phone ;
    private String email;
}
