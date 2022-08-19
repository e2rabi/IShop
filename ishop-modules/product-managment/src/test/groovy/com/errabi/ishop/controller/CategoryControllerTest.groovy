package com.errabi.ishop.controller

import com.errabi.ishop.controller.api.v1.CategoryController
import com.errabi.ishop.repositories.CategoryRepository
import com.errabi.ishop.services.CategoryService
import com.errabi.ishop.services.mapper.CategoryMapperImpl
import com.errabi.ishop.stub.CategoryStub
import com.fasterxml.jackson.databind.ObjectMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
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
        //results.andExpect(jsonPath('$.name').value('LAPTOP'))
        results.andReturn().getResponse().getHeader("location")=='/api/v1/categories/'+CategoryStub.getEntity().getId().toString()



    }
}