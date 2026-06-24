package com.vcb.vtuber_card_battle.service;

import com.vcb.vtuber_card_battle.entity.User;
import com.vcb.vtuber_card_battle.entity.UserProfile;
import com.vcb.vtuber_card_battle.repository.UserRepository;
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
}
