package com.errabi.ishop.services;

import com.errabi.ishop.repositories.CategoryRepository;
import com.errabi.ishop.services.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

    public Boolean checkCategoryExist(UUID id){
      return categoryRepository.findById(id)
              .isPresent();
    }
}
