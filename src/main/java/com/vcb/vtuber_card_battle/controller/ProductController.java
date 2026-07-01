package com.vcb.vtuber_card_battle.controller;

import com.vcb.vtuber_card_battle.dto.ProductCreateDto;
import com.vcb.vtuber_card_battle.dto.ProductUpdateDto;
import com.vcb.vtuber_card_battle.entity.Product;
import com.vcb.vtuber_card_battle.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.vcb.vtuber_card_battle.util.Utils.getPageable;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/categroy/{id}")
    public Page<Product> getProductByCategoryId(@PathVariable Long id, @RequestParam(defaultValue = "0") int page,    // 第幾頁（從 0 開始）
                                                @RequestParam(defaultValue = "10") int size,   // 每頁幾筆
                                                @RequestParam(defaultValue = "id") String sortBy,
                                                @RequestParam(defaultValue = "asc") String direction) {
        Pageable pageable = getPageable(page, size, sortBy, direction);

        return productService.findProductsByCategory(id, pageable);
    }

    @GetMapping
    public Page<Product> searchProducts(@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "0") int page,    // 第幾頁（從 0 開始）
                                        @RequestParam(defaultValue = "10") int size,   // 每頁幾筆
                                        @RequestParam(defaultValue = "id") String sortBy,
                                        @RequestParam(defaultValue = "asc") String direction) {
        Pageable pageable = getPageable(page, size, sortBy, direction);
        return productService.searchProducts(keyword, pageable);
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable long id) {
        return productService.findById(id);
    }

    @PostMapping
    public Product createProduct(@RequestBody ProductCreateDto productCreateDto) {
        return productService.createProduct(productCreateDto);
    }

    @PatchMapping("/{id}")
    public Product updateProductById(@PathVariable long id, @RequestBody ProductUpdateDto productUpdateDto) {
        return productService.updateProduct(id, productUpdateDto);
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<Void> decreaseStock(@PathVariable long id, @RequestParam int quantity) {
        productService.decreaseStock(id, quantity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }


}
