package com.errabi.ishop.services;

import com.errabi.common.exception.IShopNotFoundException;
import com.errabi.common.model.CategoryDto;
import com.errabi.common.service.IShopAbstractService;
import com.errabi.ishop.repositories.CategoryRepository;
import com.errabi.ishop.services.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.errabi.common.utils.IShopCodeError.CATEGORY_NOT_FOUND_ERROR_CODE;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService extends IShopAbstractService<CategoryDto> {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

    public Boolean checkCategoryExist(UUID id){
        log.info("Check category with id {} exist",id);
      return categoryRepository.findById(id)
              .isPresent();
    }

    public CategoryDto saveCategory(CategoryDto dto){
        log.info("Save new category {}",dto);
        return mapper.toModel(categoryRepository.save(mapper.toEntity(dto)));
    }
    public void deleteCategory(UUID id) {
        log.info("Delete category with id {}",id);
        categoryRepository.deleteById(id);
    }
    public List<CategoryDto> getAllCategories(int page,int pageSize){
        log.info("Get all categories");
        return categoryRepository.findAll(PageRequest.of(page,pageSize)).stream()
               .map(mapper::toModel)
               .collect(Collectors.toList());
    }
    public CategoryDto findCategoryById(UUID id){
        log.info("Get category by id {}",id);
        return mapper.toModel(categoryRepository.findById(id)
                .orElseThrow(()->new IShopNotFoundException(CATEGORY_NOT_FOUND_ERROR_CODE)));
    }

    public void updateCategory(CategoryDto dto, UUID id){
        log.info("Update category with id : {}",id);
        var category = findCategoryById(id);

        BeanUtils.copyProperties(dto,category);
        categoryRepository.save(mapper.toEntity(category));
    }

    @Override
    protected void validateBusinessData(CategoryDto categoryDto) {
        throw new NotImplementedException();
    }
}
