package com.example.resumeagent.llm;

import com.example.resumeagent.config.AgentProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class LlmClientConfiguration {

    private final AgentProperties properties;
    private final RestTemplate restTemplate;

    @Bean
    public LlmClient llmClient() {
        String provider = properties.getLlm().getProvider();
        if ("openai-compatible".equalsIgnoreCase(provider)) {
            return new OpenAiCompatibleLlmClient(properties, restTemplate);
        }
        return new MockLlmClient();
    }
}
