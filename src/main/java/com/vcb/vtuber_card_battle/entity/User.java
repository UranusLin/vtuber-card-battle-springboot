package com.vcb.vtuber_card_battle.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Entity                         // 告訴 JPA：這個類別對應一張資料表
@Table(name = "users")          // 指定資料表名稱（不寫則預設用類別名）
@Data                           // Lombok：自動產生 getter/setter/toString/equals/hashCode
@EqualsAndHashCode(callSuper = true)  // 記得讓 equals/hashCode 包含父類欄位
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity{

    @Column(name = "username", nullable = false, unique = true, length = 50)
    @NotBlank(message = "使用者名稱不能為空")
    private String username;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Email 不能為空")
    @Email(message = "Email 格式不正確")
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "is_active")
    @Builder.Default                                 // Builder 使用時的預設值
    private Boolean isActive = true;

    // 一個使用者對應一個使用者資料（UserProfile）
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,
            optional = false)
    @ToString.Exclude
    private UserProfile profile;
}