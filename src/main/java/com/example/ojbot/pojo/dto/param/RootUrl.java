package com.example.ojbot.pojo.dto.param;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author tzih
 * @date 2022.10.02
 */
@Configuration
@ConfigurationProperties(prefix = "root-url")
@Data
public class RootUrl {

    Map<Integer, String> url;

}
