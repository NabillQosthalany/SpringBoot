package com.example.SpringFullStack.Controller;

import com.example.SpringFullStack.DTO.ProductDTO;
import com.example.SpringFullStack.Model.Category;
import com.example.SpringFullStack.Repository.CategoryRepo;
import com.example.SpringFullStack.common.ApiResponse;
import com.example.SpringFullStack.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryRepo categoryRepo;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDTO productDTO){
        Optional<Category>optionalCategory = categoryRepo.findById(productDTO.getCategoryId());
        if(!optionalCategory.isPresent()){
            return new ResponseEntity<>(new ApiResponse(false,"category does not exists"),HttpStatus.BAD_GATEWAY);
        }
        productService.createProduct(productDTO,optionalCategory.get());
        return new ResponseEntity<>(new ApiResponse(true,"product has been added"),HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductDTO>> getProducts() {
        List<ProductDTO> products = productService.getAllProduct();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PutMapping("/update{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId")Integer productId,@RequestBody ProductDTO productDTO) throws Exception {
        Optional<Category>optionalCategory = categoryRepo.findById(productDTO.getCategoryId());
        if(!optionalCategory.isPresent()){
            return new ResponseEntity<>(new ApiResponse(false,"category does not exists"),HttpStatus.BAD_GATEWAY);
        }
        productService.updateProduct(productDTO,productId);
        return new ResponseEntity<>(new ApiResponse(true,"product has been updated"),HttpStatus.OK);
    }
}
