package com.errabi.ishop.services;

import com.errabi.ishop.services.mapper.CategoryMapperImpl
import com.errabi.ishop.stub.CategoryStub
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@DataJpaTest
@EnableJpaRepositories(basePackages = ["com.errabi.ishop.*"])
@EntityScan("com.errabi.ishop.entities")
@ContextConfiguration(classes = [CategoryService.class, CategoryMapperImpl.class])
class CategoryServiceTest extends Specification {

    @Autowired
    private CategoryService categoryService;

    def "Should create a new parent category "() {
        given: 'A new parent category '
        var category = CategoryStub.getParentCategory();
        when: 'Call the service method save category'
        def newCategory = categoryService.saveCategory(category);
        then: 'Check the results'
        newCategory.name == "LAPTOP"
    }

    def "Should find a parent category by id"() {
        given: 'An exist parent category '
        var categoryId = categoryService.saveCategory(CategoryStub.getParentCategory()).getId(); // todo use script to fill db
        when: 'Call the service method find category by id '
        def newCategory = categoryService.findCategoryById(categoryId);
        then: 'Check the results'
        newCategory.name == "LAPTOP"
    }

}