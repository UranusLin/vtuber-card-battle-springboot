package com.vcb.vtuber_card_battle.repository;

import com.vcb.vtuber_card_battle.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // JPQL：用類別名和屬性名，不是資料表名和欄位名
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice")
    List<Product> findByPriceRange(
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice
    );

    // 查詢部分欄位（DTO 投影）
    @Query("SELECT p.name, p.price FROM Product p WHERE p.category.id = :categoryId")
    List<Object[]> findNameAndPriceByCategoryId(@Param("categoryId") Long categoryId);

    // 統計查詢
    @Query("SELECT COUNT(p) FROM Product p WHERE p.category.id = :categoryId")
    long countByCategoryId(@Param("categoryId") Long categoryId);

    // 原生 SQL：寫資料庫的 SQL，可以用特定資料庫的語法
    @Query(value = "SELECT * FROM products WHERE price > :price LIMIT :limit",
            nativeQuery = true)
    List<Product> findExpensiveProducts(
            @Param("price") BigDecimal price,
            @Param("limit") int limit
    );

    // 原生 SQL 的分頁查詢（需要同時提供 countQuery）
    @Query(value = "SELECT * FROM products WHERE category_id = :categoryId",
            countQuery = "SELECT COUNT(*) FROM products WHERE category_id = :categoryId",
            nativeQuery = true)
    Page<Product> findByCategoryIdNative(
            @Param("categoryId") Long categoryId,
            Pageable pageable
    );

    // 方法命名 + 分頁（回傳 Page）
    Page<Product> findByCategory_Id(Long categoryId, Pageable pageable);

    // @Query + 分頁
    @Query("SELECT p FROM Product p WHERE p.price >= :minPrice ORDER BY p.price ASC")
    Page<Product> findProductsAbovePrice(
            @Param("minPrice") BigDecimal minPrice,
            Pageable pageable
    );

    // Slice：用於無限滾動
    Slice<Product> findByIsAvailable(Boolean isAvailable, Pageable pageable);

    // 依分類 ID 和可用狀態查詢
    Page<Product> findByCategory_IdAndIsAvailable(
            Long categoryId, Boolean isAvailable, Pageable pageable);

    // 關鍵字搜尋（名稱模糊查詢）
    Page<Product> findByNameContainingIgnoreCaseAndIsAvailable(
            String keyword, Boolean isAvailable, Pageable pageable);

    // 價格區間查詢
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :min AND :max AND p.isAvailable = true")
    Page<Product> findAvailableByPriceRange(
            @Param("min") BigDecimal min,
            @Param("max") BigDecimal max,
            Pageable pageable
    );

    // 查詢時同時載入 Category（解決 N+1 問題）
    @Query("SELECT p FROM Product p JOIN FETCH p.category WHERE p.id = :id")
    Optional<Product> findByIdWithCategory(@Param("id") Long id);

    // 查詢庫存不足的商品
    @Query("SELECT p FROM Product p WHERE p.stock <= :threshold AND p.isAvailable = true")
    List<Product> findLowStockProducts(@Param("threshold") Integer threshold);

    // 更新庫存
    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.stock = p.stock - :quantity WHERE p.id = :id AND p.stock >= :quantity")
    int decreaseStock(@Param("id") Long id, @Param("quantity") Integer quantity);

    // 統計各分類商品數
    @Query("SELECT c.name, COUNT(p) FROM Product p JOIN p.category c GROUP BY c.id, c.name")
    List<Object[]> countProductsByCategory();
}