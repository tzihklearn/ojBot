package com.example.ojbot.pojo.dto.param;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author tzih
 * @date 2022.10.03
 */
@Configuration
@ConfigurationProperties(prefix = "q-group")
@Data
public class GroupList {

    public Map<String, Integer> QGroup;

}
