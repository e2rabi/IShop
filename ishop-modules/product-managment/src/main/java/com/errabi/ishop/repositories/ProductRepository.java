package com.errabi.ishop.repositories;

import com.errabi.ishop.entities.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository  extends ElasticsearchRepository<Product, UUID> {
}
