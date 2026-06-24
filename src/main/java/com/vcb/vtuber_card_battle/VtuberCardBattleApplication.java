package com.vcb.vtuber_card_battle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Slf4j
@SpringBootApplication
@EnableJpaAuditing    // 必須加這個，@CreatedDate 和 @LastModifiedDate 才會生效
public class VtuberCardBattleApplication {

	public static void main(String[] args) {
		SpringApplication.run(VtuberCardBattleApplication.class, args);
	}

}
