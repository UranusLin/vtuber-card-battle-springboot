package com.vcb.vtuber_card_battle.service;

import com.vcb.vtuber_card_battle.dto.ProductCreateDto;
import com.vcb.vtuber_card_battle.dto.ProductUpdateDto;
import com.vcb.vtuber_card_battle.entity.Category;
import com.vcb.vtuber_card_battle.entity.Product;
import com.vcb.vtuber_card_battle.repository.CategoryRepository;
import com.vcb.vtuber_card_battle.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)  // 預設唯讀事務（查詢較快）
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    // 查詢：繼承類別的 readOnly = true
    public Page<Product> findProductsByCategory(Long categoryId, Pageable pageable) {
        // 確認分類存在
        if (!categoryRepository.existsById(categoryId)) {
            throw new EntityNotFoundException("找不到分類 ID: " + categoryId);
        }
        return productRepository.findByCategory_IdAndIsAvailable(categoryId, true, pageable);
    }

    public Page<Product> searchProducts(String keyword, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCaseAndIsAvailable(
                keyword, true, pageable);
    }

    public Product findById(Long id) {
        return productRepository.findByIdWithCategory(id)
                .orElseThrow(() -> new EntityNotFoundException("找不到商品 ID: " + id));
    }

    // 寫入操作：需要覆寫 @Transactional（不能是 readOnly）
    @Transactional
    public Product createProduct(ProductCreateDto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("找不到分類"));

        Product product = Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .category(category)
                .build();

        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Long id, ProductUpdateDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("找不到商品 ID: " + id));

        // 直接修改屬性，JPA 的 Dirty Checking 會自動更新
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());

        // 不需要呼叫 save()，事務結束時自動 flush！
        return product;
    }

    @Transactional
    public void decreaseStock(Long productId, Integer quantity) {
        int affected = productRepository.decreaseStock(productId, quantity);
        if (affected == 0) {
            throw new IllegalStateException("庫存不足或商品不存在");
        }
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("找不到商品 ID: " + id));
        productRepository.delete(product);
    }
}