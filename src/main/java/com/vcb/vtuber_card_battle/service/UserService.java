package com.vcb.vtuber_card_battle.service;

import com.vcb.vtuber_card_battle.entity.User;
import com.vcb.vtuber_card_battle.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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

    public List<User> getAllUser() {
        return userRepository.findAll();
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

    public List<User> getUserByIsActive(Boolean active) {
        return userRepository.findByIsActive(active);
    }
}
