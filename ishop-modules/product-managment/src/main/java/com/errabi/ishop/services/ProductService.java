package com.errabi.ishop.services;

import com.errabi.ishop.entities.Product;
import com.errabi.common.exception.IShopNotFoundException;
import com.errabi.ishop.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.unit.Fuzziness;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ElasticsearchOperations elasticsearchOperations ;
    private final ProductRepository productRepository;
    private static final  String PRODUCT_INDEX = "product-index";

    public Product saveProduct(Product dto) {
        log.info("Save new product {}",dto);
        var indexQuery = new IndexQueryBuilder()
                .withId(dto.getId().toString())
                .withObject(dto)
                .build();
        elasticsearchOperations.index(indexQuery, IndexCoordinates.of(PRODUCT_INDEX));
        return dto;
    }

    public Product findProductById(UUID id) throws IShopNotFoundException {
        log.info("Get product by id : {}",id);
        QueryBuilder queryBuilder =
                QueryBuilders
                        .matchQuery("id", id.toString());
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .build();
        SearchHit<Product> productDtoSearchHits =  elasticsearchOperations.searchOne(searchQuery,Product.class, IndexCoordinates.of(PRODUCT_INDEX));
        if( productDtoSearchHits != null ){
            return productDtoSearchHits.getContent();
        } else{
            throw new IShopNotFoundException("Product not found");
        }
    }

    public List<Product> findAllProduct(int page, int pageSize) {
        log.info("Getting all product...");
        Pageable of = PageRequest.of(page, pageSize);
        return StreamSupport.stream(productRepository.findAll(of).spliterator(),false)
                .collect(Collectors.toList());
    }
    public void updateProduct(UUID id, Product dto) {
        log.info("Update product with id : {}",id);
        Optional<Product> product = productRepository.findById(id);
        product.ifPresentOrElse(e->{
            product.get().setName(dto.getName());
            productRepository.save(product.get());
        },()->new IShopNotFoundException("Product not found"));

    }

    public void deleteProduct(UUID id) {
        log.info("Delete product with id {}",id);
        productRepository.deleteById(id);
    }

    public List<Product> processSearch(String query,int page,int pageSize) {
        log.info("Search with query {}", query);
        QueryBuilder queryBuilder =
                QueryBuilders
                        .multiMatchQuery(query, "name", "description")
                        .fuzziness(Fuzziness.AUTO);
        Query searchQuery = new NativeSearchQueryBuilder()
                .withFilter(queryBuilder)
                .withPageable(PageRequest.of(page, pageSize))
                .build();

        SearchHits<Product> productHits =
                elasticsearchOperations
                        .search(searchQuery, Product.class,
                                IndexCoordinates.of(PRODUCT_INDEX));
        return productHits.stream().map(searchHit ->searchHit.getContent())
                .collect(Collectors.toList());
    }

    public List<String> fetchSuggestions(String query) {
        log.info("Search with suggestions {}", query);
        QueryBuilder queryBuilder = QueryBuilders
                .wildcardQuery("name", query+"*");

        Query searchQuery = new NativeSearchQueryBuilder()
                .withFilter(queryBuilder)
                .withPageable(PageRequest.of(0, 5))
                .build();

        SearchHits<Product> searchSuggestions =
                elasticsearchOperations.search(searchQuery,
                        Product.class,
                        IndexCoordinates.of(PRODUCT_INDEX));

        List<String> suggestions = new ArrayList<>();

        searchSuggestions.getSearchHits().forEach(searchHit-> suggestions.add(searchHit.getContent().getName()));
        return suggestions;
    }
}
