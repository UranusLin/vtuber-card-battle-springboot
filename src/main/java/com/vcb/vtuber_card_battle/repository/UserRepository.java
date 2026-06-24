package com.vcb.vtuber_card_battle.repository;

import com.vcb.vtuber_card_battle.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
