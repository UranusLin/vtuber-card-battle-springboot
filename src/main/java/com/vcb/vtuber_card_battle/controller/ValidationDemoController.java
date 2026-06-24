package com.vcb.vtuber_card_battle.controller;

import com.vcb.vtuber_card_battle.entity.ProductRequest;
import com.vcb.vtuber_card_battle.entity.ProductResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/v1/demo")
@Validated  // 啟用 @PathVariable 等的驗證
public class ValidationDemoController {

    // 模擬資料庫（不需要真實 DB，方便 Demo）
    private final Map<Long, ProductResponse> fakeDb = new HashMap<>();
    private final AtomicLong idSequence = new AtomicLong(1);

    // POST /api/v1/demo/products
    // 驗證失敗 → 自動回傳 400；驗證通過 → 201 Created
    @PostMapping("/products")
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid ProductRequest request) {
        Long id = idSequence.getAndIncrement();
        ProductResponse response = ProductResponse.builder()
                .id(id)
                .name(request.getName())
                .price(request.getPrice())
                .stock(request.getStock())
                .contactEmail(request.getContactEmail())
                .createdAt(LocalDateTime.now())
                .build();
        fakeDb.put(id, response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /api/v1/demo/products/{id}
    // id = 0 或負數 → @Positive 驗證失敗，自動回傳 400
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponse> getById(
            @PathVariable @Positive(message = "ID 必須為正整數") Long id) {
        ProductResponse product = fakeDb.get(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }
}