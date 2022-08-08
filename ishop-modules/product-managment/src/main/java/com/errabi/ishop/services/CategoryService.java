package com.errabi.ishop.services;

import com.errabi.common.exception.IShopNotFoundException;
import com.errabi.common.model.CategoryDto;
import com.errabi.ishop.repositories.CategoryRepository;
import com.errabi.ishop.services.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.errabi.common.utils.IShopErrors.CATEGORY_NOT_FOUND_ERROR_CODE;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

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
    public List<CategoryDto> getAllCategories(){
        log.info("Get all categories");
        return categoryRepository.findAll().stream()
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
}
