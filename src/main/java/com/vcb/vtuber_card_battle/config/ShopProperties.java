package com.vcb.vtuber_card_battle.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "shop")
public class ShopProperties {
    private String name;
    private int maxItemsPerPage;
    private String currency;
}
