package com.errabi.ishop.controller

import com.errabi.ishop.controller.api.CategoryController
import com.errabi.ishop.repositories.CategoryRepository
import com.errabi.ishop.services.CategoryService
import com.errabi.ishop.services.mapper.CategoryMapperImpl
import com.errabi.ishop.stub.CategoryStub
import com.fasterxml.jackson.databind.ObjectMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.data.domain.PageImpl
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers=[CategoryController.class])
@ContextConfiguration(classes = [CategoryController.class,CategoryService.class, CategoryMapperImpl.class,ObjectMapper.class])
class CategoryControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper ;
    @SpringBean
    private CategoryRepository categoryRepository = Mock(CategoryRepository.class);

    def "setup"(){
        categoryRepository.save(_)>>  CategoryStub.getEntity();
        categoryRepository.findById(_)>> Optional.of(CategoryStub.getEntity())
        categoryRepository.deleteById(_)>> {}
        categoryRepository.findAll(_)>> new PageImpl(List.of(CategoryStub.getEntity()))
    }

    def "Should create a new parent category with OK 201 "() {
        given: 'A new parent category '
        var category = CategoryStub.getParentCategory();
        when: 'Call the web service method save category'
        def results = mockMvc.perform(post("/api/v1/categories")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(category)))
        then: 'Check the results'
        results.andExpect(status().isCreated())
        and:
        results.andExpect(jsonPath('$.name').value('LAPTOP'))
        results.andReturn().getResponse().getHeader("location")=='/api/v1/categories/'+CategoryStub.getEntity().getId().toString()

    }
    def "Should find a parent category by id  OK 200"() {
        given: 'A new parent category '
        var category = CategoryStub.getParentCategory();
        when: 'Call the web service method find category by id'
        def results = mockMvc.perform(get("/api/v1/categories/{categoryId}",category.getId()));
        then: 'Check the results'
        results.andExpect(status().isOk());
        and:
        results.andExpect(jsonPath('$.name').value('LAPTOP'))

    }
    def "Should update a parent category with id OK 200"() {
        given: 'An existing parent category '
        var category = CategoryStub.getParentCategory();
        when: 'Call the web service method update category'
        def results = mockMvc.perform(put("/api/v1/categories/{categoryId}",category.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(category)))
        then: 'Check the results'
        results.andExpect(status().isNoContent())
    }
    def "Should find all categories OK 200"() {
        given: 'A list of category '
        when: 'Call the web service method get all categories'
        def results = mockMvc.perform(get("/api/v1/categories/")
                .param("page","0").param("pageSize","10"))

        then: 'Check the results'
        results.andExpect(status().isOk());
        and:
        results.andExpect(jsonPath('$.length()').value(1))

    }
    def "Should delete a category by id  OK 200"() {
        given: 'A new parent category '
        var category = CategoryStub.getParentCategory();
        when: 'Call the web service method delete category'
        def results = mockMvc.perform(delete("/api/v1/categories/{categoryId}",category.getId()));
        then: 'Check the results'
        results.andExpect(status().isNoContent());

    }
}