package com.errabi.ishop.controller.openapi;

import com.errabi.ishop.entities.Product;
import com.errabi.common.exception.IShopNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Tag(name = "Product Management")
public interface ProductOpenApi {

     @Operation(summary = "Get Product by ID",
             description = "Return a product by ID")
     @ApiResponses(value = {
             @ApiResponse(responseCode = "200",
                     description = "${api.response-codes.ok.desc}"),
             @ApiResponse(responseCode = "400",
                     description = "${api.response-codes.badRequest.desc}",
                     content = { @Content(examples = { @ExampleObject(value = "") }) }),
             @ApiResponse(responseCode = "404",
                     description = "${api.response-codes.notFound.desc}",
                     content = { @Content(examples = { @ExampleObject(value = "") }) }) })
     ResponseEntity<Product> getProductById(@PathVariable("productId") UUID productId) throws IShopNotFoundException ;

}
