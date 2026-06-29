package com.vcb.vtuber_card_battle.service;

import com.vcb.vtuber_card_battle.entity.User;
import com.vcb.vtuber_card_battle.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Page<User> getAllUser(Pageable pageable) {
        return userRepository.findAll((org.springframework.data.domain.Pageable) pageable);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Transactional
    public Optional<User> updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            return userRepository.save(user);
        });
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getUserByUsernameKeyword(String keyword) {
        return userRepository.findByUsernameContainingIgnoreCase(keyword);
    }

    public Page<User> getUserByIsActive(Boolean active, Pageable pageable) {
        return userRepository.findByIsActive(active, pageable);
    }

    public List<User> getTop3UserByCreateAtDesc() {
        return userRepository.findTop3ByIsActiveTrueOrderByCreatedAtDesc();
    }

    public List<User> getRecentActiveUsers(int days) {
        LocalDateTime since = LocalDateTime.now().minusDays(days);
        return userRepository.findActiveUsersSince(since);
    }

    @Transactional
    public boolean deactivateUser(Long id) {
        int affected = userRepository.updateActiveStatus(id, false);
        return affected > 0;  // 回傳是否成功（找不到 id 時為 0）
    }

    // 給足彈性，由外部決定Status狀態
    @Transactional
    public boolean updateUserStatus(Long id, boolean status) {
        int affected = userRepository.updateActiveStatus(id, status);
        return affected > 0;
    }

    @Transactional
    public boolean rollbackUserStatus(Long id) {
        int affected = userRepository.updateActiveStatus(id, Boolean.TRUE);
        return affected > 0;
    }
}
