import com.errabi.ishop.entities.Category
import com.errabi.ishop.repositories.CategoryRepository
import com.errabi.ishop.services.CategoryService
import com.errabi.ishop.services.mapper.CategoryMapperImpl
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Ignore
import spock.lang.Specification

@ContextConfiguration(classes = [CategoryService.class, CategoryMapperImpl.class])
class CategoryServiceTest extends Specification {

    @Autowired
    private CategoryService categoryService;
    @SpringBean
    private CategoryRepository categoryRepository = Mock(CategoryRepository.class)

    @Ignore
    def "Should find category by id "() {
        given: 'A uuid for a category '
        categoryRepository.findById(_)>> Optional.of(Category.builder().name("LAPTOP").build())
        when: 'Call the service method findById category'
        def category = categoryService.findCategoryById(UUID.randomUUID())
        then: 'Check the results'
        category.name == "LAPTOP"
    }
    @Ignore
    def "Should check if category already exist "() {
        given: 'A uuid for an exist category '
        categoryRepository.findById(_)>> Optional.of(new Category())
        when: 'Call the service method check category'
        def exist = categoryService.checkCategoryExist(UUID.randomUUID())
        then: 'Check the results'
        exist == true
    }
    @Ignore
    def "Should check if category not exist "() {
        given: 'A uuid for an exist category '
        categoryRepository.findById(_)>> Optional.empty()
        when: 'Call the service method check category'
        def exist = categoryService.checkCategoryExist(UUID.randomUUID())
        then: 'Check the results'
        exist == false
    }
    @Ignore
    def "Should return all categories "() {
        given: 'A list of categories '
        categoryRepository.findAll()> List.of(Category.builder().name("LAPTOP").build())
        when: 'Call the service method get all categories'
        def list = categoryService.getAllCategories();
        then: 'Check the results'
        list.size() == 1
    }
}