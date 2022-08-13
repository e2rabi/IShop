package com.errabi.ishop.services;

import com.errabi.common.exception.IShopException;
import com.errabi.common.model.ProductDto;
import com.errabi.common.service.IShopAbstractService;
import com.errabi.common.utils.IShopErrors;
import com.errabi.ishop.entities.Product;
import com.errabi.common.exception.IShopNotFoundException;
import com.errabi.ishop.repositories.ProductRepository;
import com.errabi.ishop.services.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.unit.Fuzziness;
import org.springframework.beans.BeanUtils;
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
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.errabi.common.utils.IShopErrors.PRODUCT_NOT_FOUND_ERROR_CODE;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService extends IShopAbstractService<ProductDto> {

    private final ElasticsearchOperations elasticsearchOperations ;
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final MerchantService merchantService;
    private final ProductMapper mapper;

    private static final  String PRODUCT_INDEX = "product-index";

    public ProductDto saveProduct(ProductDto productDto) {
        log.info("Save new product {}",productDto);

        validateBusinessData(productDto);

        var entity = mapper.toEntity(productDto);
        var indexQuery = new IndexQueryBuilder()
                                                .withId(entity.getId().toString())
                                                .withObject(entity)
                                                .build();
        elasticsearchOperations.index(indexQuery, IndexCoordinates.of(PRODUCT_INDEX));
        return mapper.toModel(entity);
    }

    public ProductDto findProductById(UUID id) {
        log.info("Get product by id : {}",id);
        QueryBuilder queryBuilder =
                QueryBuilders
                        .matchQuery("id", id.toString());
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .build();
        SearchHit<Product> productDtoSearchHits =  elasticsearchOperations.searchOne(searchQuery,Product.class, IndexCoordinates.of(PRODUCT_INDEX));
        if( productDtoSearchHits != null ){
            return mapper.toModel(productDtoSearchHits.getContent());
        } else{
            throw new IShopNotFoundException(PRODUCT_NOT_FOUND_ERROR_CODE);
        }
    }

    public List<ProductDto> findAllProduct(int page, int pageSize) {
        log.info("Getting all product...");
        Pageable of = PageRequest.of(page, pageSize);
        return StreamSupport.stream(productRepository.findAll(of).spliterator(),false)
                .map(mapper::toModel)
                .collect(Collectors.toList());
    }
    public void updateProduct(UUID id, ProductDto dto) {
        log.info("Update product with id : {}",id);
        var product = productRepository.findById(id)
                .orElseThrow(()->new IShopNotFoundException(IShopErrors.PRODUCT_NOT_FOUND_ERROR_CODE));
        BeanUtils.copyProperties(dto,product);
        productRepository.save(product);
    }

    public void deleteProduct(UUID id) {
        log.info("Delete product with id {}",id);
        productRepository.deleteById(id);
    }

    public List<ProductDto> processSearch(String query,int page,int pageSize) {
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
        return productHits.stream()
                            .map(searchHit ->searchHit.getContent())
                            .map(mapper::toModel)
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

    public void addProductToCategory(UUID categoryId,UUID productId){
        log.info("add product {} to category {}",productId,categoryId);
        var product = findProductById(productId);

        validateBusinessData(product);
        product.setCategoryId(categoryId);

        updateProduct(productId,product);
    }

    public void removeProductFromCategory(UUID categoryId,UUID productId){
      // todo to implement logic
    }

    @Override
    protected void validateBusinessData(ProductDto productDto){
        if(!categoryService.checkCategoryExist(productDto.getCategoryId())){
            log.info("Invalid category ID");
            throw new IShopException(IShopErrors.CATEGORY_NOT_FOUND_ERROR_CODE);
        }
        if(!merchantService.checkMerchantExist(productDto.getMerchantId())){
            log.info("Invalid Merchant ID");
            throw new IShopException(IShopErrors.MERCHANT_NOT_FOUND_ERROR_CODE);
        }
    }
}
