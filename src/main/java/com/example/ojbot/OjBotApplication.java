package com.example.ojbot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ConfigurationPropertiesScan
@MapperScan("com.example.ojbot.mapper")
public class OjBotApplication {
//
//    @Autowired
//    private RestTemplate restTemplate;
    public static void main(String[] args) {
        SpringApplication.run(OjBotApplication.class, args);
    }

}
