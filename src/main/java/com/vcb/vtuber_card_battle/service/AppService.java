package com.vcb.vtuber_card_battle.service;

import com.vcb.vtuber_card_battle.config.AppProperties;
import org.springframework.stereotype.Service;

@Service
public class AppService {

    // 透過依賴注入取得配置
    private final AppProperties appProperties;

    // 建構子注入（推薦方式）
    public AppService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public String getAppInfo() {
        return appProperties.getName() + " (最多 " + appProperties.getMaxUsers() + " 人)";
    }
}