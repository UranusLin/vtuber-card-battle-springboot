package com.vcb.vtuber_card_battle.controller;

import com.vcb.vtuber_card_battle.config.ShopProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shop")
public class ShopController {
    private ShopProperties shopProperties;

    public ShopController(ShopProperties shopProperties) {
        this.shopProperties = shopProperties;
    }

    @GetMapping("/info")
    public ResponseEntity<ShopProperties> getShopInfo() {
        return ResponseEntity.ok(shopProperties);
    }
}
