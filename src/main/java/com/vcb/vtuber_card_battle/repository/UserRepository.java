package com.vcb.vtuber_card_battle.repository;

import com.vcb.vtuber_card_battle.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Page<User> findByIsActive(Boolean isActive, Pageable pageable);

    Optional<User> findByEmailAndIsActive(String email, Boolean isActive);

    List<User> findByEmailOrUsername(String email, String username);

    List<User> findByUsernameContaining(String keyword);

    List<User> findByUsernameContainingIgnoreCase(String keyword);

    List<User> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    List<User> findByIdGreaterThan(Long id);

    List<User> findByIsActiveTrueOrderByCreatedAtDesc( );

    boolean existsByEmail(String email);

    long countByIsActive(Boolean isActive);

    List<User> findTop3ByIsActiveTrueOrderByCreatedAtDesc();

    @Query("SELECT u FROM User u WHERE u.isActive = true AND u.createdAt >= :since ORDER BY u.createdAt DESC")
    List<User> findActiveUsersSince(@Param("since") LocalDateTime since);

    // 更新操作：必須加 @Modifying 和 @Transactional
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.isActive = :isActive WHERE u.id = :id")
    int updateActiveStatus(@Param("id") Long id, @Param("isActive") Boolean isActive);

    // 軟刪除（soft delete）
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.isActive = false WHERE u.id = :id")
    void softDeleteById(@Param("id") Long id);

    // 批次刪除
    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.isActive = false AND u.updatedAt < :before")
    int deleteInactiveUsersBefore(@Param("before") LocalDateTime before);
}
