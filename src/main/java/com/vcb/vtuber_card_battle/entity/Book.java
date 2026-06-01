package com.vcb.vtuber_card_battle.entity;

import lombok.Data;

@Data
public class Book {
    private Long id;
    private String title;       // 書名
    private String author;      // 作者
    private String isbn;        // ISBN
    private double price;       // 價格
    private int stock;          // 庫存
}