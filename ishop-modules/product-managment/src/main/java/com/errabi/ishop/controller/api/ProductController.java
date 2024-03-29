package com.errabi.ishop.controller.api;

import com.errabi.common.model.ProductDto;
import com.errabi.ishop.controller.openapi.ProductOpenApi;
import com.errabi.ishop.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/products")
public class ProductController implements ProductOpenApi {

    ProductService productService ;

    @Override
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("productId") UUID productId)  {
        return new ResponseEntity<>(productService.findProductById(productId), HttpStatus.OK);
    }

    @PutMapping(("/{productId}"))
    public ResponseEntity<Void> updateProduct(@PathVariable("productId") UUID productId,@RequestBody ProductDto Product ){
        productService.updateProduct(productId,Product);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping(("/{productId}"))
    public ResponseEntity<Void>  deleteProduct(@PathVariable("productId") UUID productId){
         productService.deleteProduct(productId);
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<ProductDto> saveProduct(@RequestBody  ProductDto  Product){
        var newProduct = productService.saveProduct(Product);
        var headers = new HttpHeaders();
        headers.add("location","/api/v1/products/"+newProduct.getId().toString());
        return new ResponseEntity<>(newProduct,headers,HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getAllProduct(@RequestParam(required = false) int page,@RequestParam(required = false) int pageSize){
        return new ResponseEntity<>(productService.findAllProduct(page,pageSize), HttpStatus.OK);
    }
    @GetMapping("/fetch_suggestions")
    public ResponseEntity<List<String>> fetchSuggestions(@RequestParam String query){
        return new ResponseEntity<>(productService.fetchSuggestions(query), HttpStatus.OK);
    }
    @GetMapping("/queries")
    public ResponseEntity<List<ProductDto>> search(@RequestParam String query,@RequestParam(required = false) int page,@RequestParam(required = false) int pageSize){
        return new ResponseEntity<>(productService.processSearch(query,page,pageSize), HttpStatus.OK);
    }
    @GetMapping("/{productId}/categories/{categoryId}")
    public ResponseEntity<Void> addProductToCategory(@PathVariable("productId") UUID productId,@PathVariable("categoryId") UUID categoryId){
        productService.addProductToCategory(categoryId,productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
