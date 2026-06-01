package com.vcb.vtuber_card_battle.controller;

import com.vcb.vtuber_card_battle.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class AppController {
    private final AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping("/appInfo")
    public String getAppInfo() {
        return appService.getAppInfo();
    }
}
