package com.vcb.vtuber_card_battle.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

// 所有 Entity 繼承 BaseEntity，不用重複寫 id、時間欄位
@Entity
@Table(name = "products",
        indexes = {
                @Index(name = "idx_product_category", columnList = "category_id"),
                @Index(name = "idx_product_name", columnList = "name")
        })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)  // 記得讓 equals/hashCode 包含父類欄位
public class Product extends BaseEntity{

    @Column(nullable = false, length = 200)
    @NotBlank(message = "商品名稱不能為空")
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "價格不能為空")
    @DecimalMin(value = "0.0", inclusive = false, message = "價格必須大於 0")
    private BigDecimal price;


    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    @Builder.Default
    private Integer stock = 0;

    @Column(name = "is_available")
    @Builder.Default
    private boolean isAvailable = true;

    // 多個商品對應一個分類
    // @JoinColumn 指定外鍵欄位名稱
    // fetch = FetchType.LAZY 延遲載入（強烈推薦！）
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @ToString.Exclude    // 避免無窮遞迴！
    private Category category;

    // 多對多：使用中間表 product_tags
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "product_tags",                    // 中間表名稱
            joinColumns = @JoinColumn(name = "product_id"),    // 本表外鍵
            inverseJoinColumns = @JoinColumn(name = "tag_id")  // 對方外鍵
    )
    @ToString.Exclude
    private Set<Tag> tags = new HashSet<>();
}
