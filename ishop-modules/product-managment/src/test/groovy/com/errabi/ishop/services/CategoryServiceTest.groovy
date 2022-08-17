package com.errabi.ishop.services

import com.errabi.common.exception.IShopNotFoundException;
import com.errabi.ishop.services.mapper.CategoryMapperImpl
import com.errabi.ishop.stub.CategoryStub
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification

@DataJpaTest
@EnableJpaRepositories(basePackages = ["com.errabi.ishop.*"])
@EntityScan("com.errabi.ishop.entities")
@TestPropertySource(properties = [
    "spring.jpa.hibernate.ddl-auto=update"
])
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
        def uuid = UUID.fromString("7f603305-0a5d-4fa5-bedd-b9105945ee73");
        when: 'Call the service method find category by id '
        def newCategory = categoryService.findCategoryById(uuid);
        then: 'Check the results'
        newCategory.name == "Animals & Pet Supplies"
    }

    def "Should find all parent categories"() {
        given: 'An exist parents categories '
        when: 'Call the service method getAllCategories '
        def categories = categoryService.getAllCategories(0,100);
        then: 'Check the results'
        categories.size()==13;
    }
    def "Should update parent category"() {
        given: 'An exist parents categories '
        def uuid = UUID.fromString("7f603305-0a5d-4fa5-bedd-b9105945ee73");
        var updatedCategory = categoryService.findCategoryById(uuid)
        updatedCategory.setName("Animals");
        when: 'Call the service method updateCategory '
        categoryService.updateCategory(updatedCategory,uuid);
        def results = categoryService.findCategoryById(uuid);
        then: 'Check the results'
        results.getName()=="Animals"
    }
    def "Should delete parent category by id"() {
        given: 'An exist parents categories '
        var categoryId =  categoryService.saveCategory(CategoryStub.getParentCategory()).getId();
        when: 'Call the service method deleteById '
        categoryService.deleteCategory(categoryId);
        categoryService.findCategoryById(categoryId);
        then: 'Check the results'
        thrown(IShopNotFoundException)
    }
}