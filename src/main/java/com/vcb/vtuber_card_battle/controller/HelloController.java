package com.vcb.vtuber_card_battle.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {
    // 處理 GET /api/hello
    @GetMapping("/hello")
    public String hello() {
        return "Hello, Spring Boot!";
    }

    // 處理 GET /api/hello/{name}（路徑變數）
    @GetMapping("/hello/{name}")
    public String helloName(@PathVariable String name) {
        return "Hello, " + name + "!";
    }

    // 處理 GET /api/greet?message=xxx（查詢參數）
    @GetMapping("/greet")
    public String greet(@RequestParam(defaultValue = "World") String message) {
        return "Greetings: " + message;
    }
}
