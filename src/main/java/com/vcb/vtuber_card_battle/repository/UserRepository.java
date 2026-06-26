package com.vcb.vtuber_card_battle.repository;

import com.vcb.vtuber_card_battle.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    List<User> findByIsActive(Boolean isActive);

    Optional<User> findByEmailAndIsActive(String email, Boolean isActive);

    List<User> findByEmailOrUsername(String email, String username);

    List<User> findByUsernameContaining(String keyword);

    List<User> findByUsernameContainingIgnoreCase(String keyword);

    List<User> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    List<User> findByIdGreaterThan(Long id);

    List<User> findByIsActiveTrueOrderByCreatedAtDesc( );

    boolean existsByEmail(String email);

    long countByIsActive(Boolean isActive);
}
