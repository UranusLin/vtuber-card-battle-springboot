package com.vcb.vtuber_card_battle.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

// 分類 Entity（「一」的那端）
@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    // 一個分類有多個商品
    // mappedBy = "category" 表示由 Product.category 這個欄位來維護關聯
    // cascade = CascadeType.ALL 表示對分類的操作會連帶影響商品
    // orphanRemoval = true 表示從分類移除的商品會被自動刪除
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude    // 避免 Lombok toString() 產生無窮遞迴！
    private List<Product> products = new ArrayList<>();

    // 雙向關聯的輔助方法（維持一致性）
    public void addProduct(Product product) {
        products.add(product);
        product.setCategory(this);  // 同時設定子端的關聯
    }

    public void removeProduct(Product product) {
        products.remove(product);
        product.setCategory(null);
    }
}
