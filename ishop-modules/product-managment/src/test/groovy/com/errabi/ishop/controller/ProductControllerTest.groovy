package com.errabi.ishop.controller

import com.errabi.ishop.controller.api.ProductController
import com.errabi.ishop.entities.Category
import com.errabi.ishop.entities.Merchant
import com.errabi.ishop.entities.Product
import com.errabi.ishop.repositories.CategoryRepository
import com.errabi.ishop.repositories.MerchantRepository
import com.errabi.ishop.repositories.ProductRepository
import com.errabi.ishop.services.CategoryService
import com.errabi.ishop.services.MerchantService
import com.errabi.ishop.services.ProductService
import com.errabi.ishop.services.mapper.CategoryMapperImpl
import com.errabi.ishop.services.mapper.ProductMapperImpl
import com.errabi.ishop.stub.ProductStub
import com.fasterxml.jackson.databind.ObjectMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.SearchHit
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers=[ProductController.class])
@ContextConfiguration(classes = [ProductController.class,CategoryService,MerchantService, MerchantRepository,CategoryMapperImpl, ProductMapperImpl, MerchantService.class, ProductService.class, CategoryService.class, ObjectMapper.class])
class ProductControllerTest extends Specification{

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper ;
    @SpringBean
    private ElasticsearchOperations elasticsearchOperations = Mock(ElasticsearchOperations)
    @SpringBean
    private ProductRepository productRepository = Mock(ProductRepository)
    @SpringBean
    private CategoryRepository categoryRepository = Mock(CategoryRepository)
    @SpringBean
    private MerchantRepository merchantRepository = Mock(MerchantRepository)
    private SearchHit<Product> searchHit = Mock(SearchHit<Product>)
    def "setup"(){
        elasticsearchOperations.index(_,_)>>{}
        categoryRepository.findById(_) >> Optional.of(new Category())
        merchantRepository.findById(_) >> Optional.of(new Merchant())
        productRepository.findById(_)>> Optional.of(ProductStub.getEntity())
        elasticsearchOperations.searchOne(_,_,_) >> searchHit
    }

    def "Should create a product with OK 201 "() {
        given: 'A new product '
        var product = ProductStub.getProduct();
        when: 'Call the web service method save product'
        def results = mockMvc.perform(post("/api/v1/products")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(product)))
        then: 'Check the results'
        results.andExpect(status().isCreated())
        and:
        results.andExpect(jsonPath('$.name').value('LAPTOP'))
        results.andReturn().getResponse().getHeader("location")=='/api/v1/products/'+ProductStub.getProduct().getId().toString()

    }
    def "Should update a product with OK 204"() {
        given: 'An exist product '
        var product = ProductStub.getProduct();
        when: 'Call the web service method update product'
        def results = mockMvc.perform(put("/api/v1/products/{productId}",UUID.randomUUID().toString())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(product)))
        then: 'Check the results'
        results.andExpect(status().isNoContent())
    }
    def "Should get a product by id with OK 200"() {
        given: 'An exist product '
        var product = ProductStub.getProduct();
        when: 'Call the web service method update product'
        def results = mockMvc.perform(get("/api/v1/products/{productId}",UUID.randomUUID().toString())
                .contentType("application/json"))
        then: 'Check the results'
        results.andExpect(status().isOk())
    }
}
