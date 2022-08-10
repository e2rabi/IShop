package com.errabi.ishop.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "product-index")
public class Product {
  @Id
  private UUID id;
  @Field(type = FieldType.Text, name = "name")
  private String name ;
  @Field(type = FieldType.Text, name = "description")
  private String description;
  @Field(type = FieldType.Text, name = "imageBase64")
  private String imageBase64;
  @Field(type = FieldType.Text, name = "price")
  private Double price ;
  @Field(type = FieldType.Text, name = "status")
  private String status ;
  @Field(type = FieldType.Text, name = "categoryId")
  private String categoryId;
  @Field(type = FieldType.Text, name = "merchantId")
  private UUID merchantId ;
}
