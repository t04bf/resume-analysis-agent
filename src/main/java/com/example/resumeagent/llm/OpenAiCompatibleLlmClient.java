package com.example.resumeagent.llm;

import com.example.resumeagent.config.AgentProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class OpenAiCompatibleLlmClient implements LlmClient {

    private final AgentProperties properties;
    private final RestTemplate restTemplate;

    @Override
    @SuppressWarnings("unchecked")
    public String chat(String prompt) {
        AgentProperties.Llm llm = properties.getLlm();
        if (llm.getApiKey() == null || llm.getApiKey().isBlank()) {
            throw new IllegalStateException("agent.llm.api-key 为空，请配置 OPENAI_API_KEY 或改用 mock 模式");
        }

        String url = trimRightSlash(llm.getBaseUrl()) + "/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(llm.getApiKey());

        Map<String, Object> body = Map.of(
                "model", llm.getModel(),
                "temperature", llm.getTemperature(),
                "max_tokens", llm.getMaxTokens(),
                "messages", List.of(
                        Map.of("role", "system", "content", "你是招聘系统中的 AI Agent，只输出用户要求的 JSON。"),
                        Map.of("role", "user", "content", prompt)
                )
        );

        try {
            Map<String, Object> response = restTemplate.postForObject(url, new HttpEntity<>(body, headers), Map.class);
            if (response == null) {
                throw new IllegalStateException("大模型接口返回为空");
            }
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
            if (choices == null || choices.isEmpty()) {
                throw new IllegalStateException("大模型接口未返回 choices: " + response);
            }
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            if (message == null || message.get("content") == null) {
                throw new IllegalStateException("大模型接口未返回 message.content: " + response);
            }
            return String.valueOf(message.get("content"));
        } catch (RestClientException e) {
            throw new IllegalStateException("调用大模型接口失败: " + e.getMessage(), e);
        }
    }

    private String trimRightSlash(String value) {
        if (value == null || value.isBlank()) {
            return "https://api.openai.com/v1";
        }
        while (value.endsWith("/")) {
            value = value.substring(0, value.length() - 1);
        }
        return value;
    }
}
