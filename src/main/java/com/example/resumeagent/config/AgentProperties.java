package com.example.resumeagent.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "agent")
public class AgentProperties {

    private Llm llm = new Llm();

    @Data
    public static class Llm {
        /**
         * mock 或 openai-compatible
         */
        private String provider = "mock";

        /**
         * 例如：https://api.openai.com/v1
         */
        private String baseUrl = "https://api.openai.com/v1";

        private String apiKey;

        private String model = "gpt-4o-mini";

        private Double temperature = 0.2;

        private Integer maxTokens = 2000;

        private Integer timeoutSeconds = 60;
    }
}
