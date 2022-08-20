package com.errabi.ishop.services

import com.errabi.common.exception.IShopNotFoundException
import com.errabi.common.model.ProductDto
import com.errabi.ishop.entities.Product
import com.errabi.ishop.repositories.ProductRepository
import com.errabi.ishop.services.mapper.ProductMapperImpl
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.SearchHit
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = [ProductService, ProductMapperImpl.class])
class ProductServiceTest extends Specification {

    @Autowired
    private ProductService productService ;
    @SpringBean
    private ElasticsearchOperations elasticsearchOperations =Mock(ElasticsearchOperations)
    @SpringBean
    private ProductRepository productRepository = Mock(ProductRepository)
    @SpringBean
    private CategoryService categoryService = Mock(CategoryService)
    @SpringBean
    private MerchantService merchantService = Mock(MerchantService)

    def "Should create a new product "() {
        given: 'A new valid product '
        categoryService.checkCategoryExist(_)>> true
        merchantService.checkMerchantExist(_)>> true
        elasticsearchOperations.index(_,_)>>{}
        when: 'Call the service method save product'
        def newProduct = productService.saveProduct(ProductDto.builder().id(UUID.randomUUID()).name("Test").build())
        then: 'Check the results'
        newProduct.name == "Test"
    }
    def "Should find a product by ID OK "() {
        given: 'An exist product '
         var searchHit = Mock(SearchHit<Product>)
        searchHit.getContent()>> Product.builder().id(UUID.randomUUID()).name("test").build()
        elasticsearchOperations.searchOne(_,_,_)>> searchHit
        when: 'Call the service method find product by id'
        def product = productService.findProductById(UUID.randomUUID())
        then: 'Check the results'
        product.name == "test"
    }
    def "Should find a product by ID KO "() {
        given: 'An not exist product '
        var searchHit = Mock(SearchHit<Product>)
        searchHit.getContent()>> Product.builder().id(UUID.randomUUID()).name("test").build()
        elasticsearchOperations.searchOne(_,_,_)>> null
        when: 'Call the service method find product by id'
        def product = productService.findProductById(UUID.randomUUID())
        then: 'Check the results'
         thrown(IShopNotFoundException)
    }
    def "Should find all product "() {
        given: 'A list of product '
        var page = new PageImpl<Product>(List.of(Product.builder().name("test").build()),PageRequest.of(0,1),1)
        productRepository.findAll(_)>> page
        when: 'Call the service method find all product'
        def products = productService.findAllProduct(0,100)
        then: 'Check the results'
        products.size() == 1
    }
    def "Should delete product by id "() {
        given: 'An exist product'
        productRepository.deleteById(_)>>{}
        when: 'Call the service method delete by id'
        productService.deleteProduct(UUID.randomUUID())
        then: 'Check the results'
        1 *  productRepository.deleteById(*_)
    }
}