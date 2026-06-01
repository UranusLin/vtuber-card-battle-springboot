package com.vcb.vtuber_card_battle.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data           // Lombok：自動產生 getter/setter
@Component      // 讓 Spring 管理這個類
@ConfigurationProperties(prefix = "app")  // 綁定 app.* 開頭的配置
public class AppProperties {

    // 對應 app.name
    private String name;

    // 對應 app.max-users（注意：properties 用 - 分隔，Java 用駝峰）
    private int maxUsers;

    // 對應 app.jwt-secret
    private String jwtSecret;

    // 對應 app.upload-dir
    private String uploadDir;
}