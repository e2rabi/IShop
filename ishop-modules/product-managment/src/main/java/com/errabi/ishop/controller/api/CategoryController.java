package com.errabi.ishop.controller.api;

import com.errabi.common.model.CategoryDto;
import com.errabi.ishop.controller.openapi.CategoryOpenApi;
import com.errabi.ishop.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController implements CategoryOpenApi {

    private final CategoryService categoryService;

    @Override
    @PostMapping
    public ResponseEntity<CategoryDto> saveCategory(@RequestBody CategoryDto categoryDto){
        var newCategory = categoryService.saveCategory(categoryDto);
        var headers = new HttpHeaders();
        headers.add("location","/api/v1/categories/"+newCategory.getId().toString());
        return new ResponseEntity<>(newCategory,headers, HttpStatus.CREATED);
    }
    @DeleteMapping(("/{categoryId}"))
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteCategory(@PathVariable("categoryId") UUID categoryId){
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(("/{categoryId}"))
    public ResponseEntity<Void> updateCategory(@PathVariable("categoryId") UUID categoryId, @RequestBody CategoryDto categoryDto ){
        categoryService.updateCategory(categoryDto,categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategory(@RequestParam(required = false) int page, @RequestParam(required = false) int pageSize){
        return new ResponseEntity<>(categoryService.getAllCategories(page,pageSize), HttpStatus.OK);
    }
    @GetMapping(("/{categoryId}"))
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("categoryId") UUID categoryId ){
        return new ResponseEntity<>( categoryService.findCategoryById(categoryId),HttpStatus.OK);
    }


}
