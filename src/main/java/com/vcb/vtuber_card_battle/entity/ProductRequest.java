package com.vcb.vtuber_card_battle.entity;

import jakarta.validation.constraints.*;
import lombok.Data;


import java.math.BigDecimal;

@Data
public class ProductRequest {
    @NotBlank(message = "商品名稱不能為空")
    @Size(min = 2, max = 100, message = "商品名稱長度須在 2 到 100 字之間")
    private String name;

    @NotNull(message = "價格不能為空")
    @DecimalMin(value = "0.0", inclusive = false, message = "價格必須大於 0")
    private BigDecimal price;

    @NotNull(message = "庫存不能為空")
    @Min(value = 0, message = "庫存不能為負數")
    private Integer stock;

    @Email(message = "聯絡 Email 格式不正確")
    private String contactEmail;
}