package com.vcb.vtuber_card_battle.controller;

import com.vcb.vtuber_card_battle.entity.User;
import com.vcb.vtuber_card_battle.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    // 暫時用 List 模擬資料庫（下一單元再接真正的 DB）
    private List<User> users = new ArrayList<>();
    private Long nextId = 1L;
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ─────────────────────────────────────────
    // GET /api/users → 取得所有使用者
    // ─────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) Boolean active) {
        if (active != null) {
            return ResponseEntity.ok(userService.getUserByIsActive(active));
        } else {
            return ResponseEntity.ok(userService.getAllUser());  // HTTP 200 + JSON 列表
        }
    }

    // ─────────────────────────────────────────
    // GET /api/users/{id} → 取得單一使用者
    // ─────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
//        Optional<User> user = users.stream()
//                .filter(u -> u.getId().equals(id))
//                .findFirst();
        Optional<User> user = userService.getUserById(id);
        log.info(user.toString());
        // 找到回 200，找不到回 404
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ─────────────────────────────────────────
    // POST /api/users → 新增使用者
    // @RequestBody：把 Request Body 的 JSON 轉成 Java 物件
    // ─────────────────────────────────────────
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));  // HTTP 201
    }

    // ─────────────────────────────────────────
    // PUT /api/users/{id} → 更新使用者
    // ─────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,
                                           @RequestBody User updatedUser) {
        return userService.updateUser(id, updatedUser)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ─────────────────────────────────────────
    // DELETE /api/users/{id} → 刪除使用者
    // ─────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> getUserByUsernameKeyword(@RequestParam String keyword) {
        return ResponseEntity.ok(userService.getUserByUsernameKeyword(keyword));
    }
}