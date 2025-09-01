package com.Ecommerce.store.products;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private ProductRepository productRepository;
    private Productmapper productmapper;
    private CategoryRepository categoryRepository;

    @GetMapping
    public List<ProductDto> getAllProducts(
            @RequestParam(required = false,defaultValue = "", name = "categoryId") Byte categoryId
    ) {
        List<Product> products;
        if (categoryId != null) {
            products=productRepository.findByCategoryId(categoryId);
        }else{
            products=productRepository.findAllWithCategoryId();
        }
        return products.stream().map(productmapper::toDto).toList();
    }
    @GetMapping("/{productid}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long productid) {
        var product = productRepository.findById(productid).orElse(null);
        if(product == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(productmapper.toDto(product));
    }


    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody ProductDto productDto,
            UriComponentsBuilder uriBuilder
    ) {
        var category1=categoryRepository.findById(productDto.getCategory_id()).orElse(null);
        if(category1 == null){
            return ResponseEntity.notFound().build();
        }
        var product = productmapper.toEntity(productDto);
        product.setCategory(category1);
        productRepository.save(product);
        productDto.setId(product.getId());
        var uri=uriBuilder.path("/users/{id}").buildAndExpand(productDto.getId()).toUri();
        return ResponseEntity.created(uri).body(productDto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable(name = "id") Long id, @RequestBody ProductDto productDto) {
        var category=categoryRepository.findById(productDto.getCategory_id()).orElse(null);
        if(category == null){
            return ResponseEntity.notFound().build();
        }
        var product = productRepository.findById(id).orElse(null);
        if(product == null){
            return ResponseEntity.notFound().build();
        }
        productmapper.updateProductDto(productDto, product);
        product.setCategory(category);
        productRepository.save(product);
        productDto.setId(product.getId());
        return ResponseEntity.ok(productDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(name = "id") Long id) {
        var product = productRepository.findById(id).orElse(null);
        if(product == null){
            return ResponseEntity.notFound().build();
        }
        productRepository.delete(product);
        return ResponseEntity.noContent().build();
    }
}
