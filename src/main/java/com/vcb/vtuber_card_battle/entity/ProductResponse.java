package com.vcb.vtuber_card_battle.entity;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class ProductResponse {

    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private String contactEmail;
    private LocalDateTime createdAt;
}