package com.alex.asset;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement
@EnableJpaAuditing
@SpringBootApplication
public class AssetTrackProApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssetTrackProApplication.class, args);
    }

}
