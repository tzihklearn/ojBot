package com.example.ojbot.pojo.dto.param;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Set;

/**
 * @author tzih
 * @date 2022.10.02
 */
@Configuration
@ConfigurationProperties(prefix = "problem")
@Data
public class ProblemList {

    public Map<Integer, Set<Integer>> problems;

}
