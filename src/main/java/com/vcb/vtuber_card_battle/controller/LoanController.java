package com.vcb.vtuber_card_battle.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class LoanController {

    // 1. 借書：POST /api/v1/members/{memberId}/loans
    @PostMapping("/member/{memeberId}/loans")
    public String createLoans(@PathVariable int memeberId) {
        return "";
    }
    // 2. 查詢借閱紀錄：GET /api/v1/members/{memberId}/loans
    //    支援查詢參數：status（ACTIVE/RETURNED）
    @GetMapping("/members/{memberId}/loans")
    public String getMemberLoans(@PathVariable int memberId, @RequestParam String status) {
        return "";
    }

    // 3. 還書：PATCH /api/v1/loans/{loanId}
    @PatchMapping("/loans/{loanId}")
    public String createLoan(@PathVariable int loanId) {
        return "";
    }
    // 4. 查詢逾期未還的借閱紀錄：GET /api/v1/loans?status=OVERDUE
    @GetMapping("/loans")
    public String getLoansByStatus(@RequestParam String status) {
        return "";
    }
}