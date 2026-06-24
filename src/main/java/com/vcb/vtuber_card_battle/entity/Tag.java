package com.vcb.vtuber_card_battle.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tags")
@Data
@NoArgsConstructor
public class Tag extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    // mappedBy 表示這端不維護關聯（由 Product 那端的 @JoinTable 維護）
    @ManyToMany(mappedBy = "tags")
    @ToString.Exclude
    private Set<Product> products = new HashSet<>();
}
