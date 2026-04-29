package com.example.resumeagent.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(AgentProperties properties) {
        int timeoutSeconds = properties.getLlm().getTimeoutSeconds() == null
                ? 60
                : properties.getLlm().getTimeoutSeconds();

        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(timeoutSeconds))
                .setReadTimeout(Duration.ofSeconds(timeoutSeconds))
                .build();
    }
}
