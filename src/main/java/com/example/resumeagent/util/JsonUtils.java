package com.example.resumeagent.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JsonUtils {

    private final ObjectMapper objectMapper;

    public <T> T fromJson(String content, Class<T> clazz) {
        String json = extractJson(content);
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("解析大模型 JSON 失败，目标类型: " + clazz.getSimpleName() + ", 原始内容: " + content, e);
        }
    }

    public <T> List<T> fromJsonList(String content, Class<T> clazz) {
        String json = extractJson(content);
        JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
        try {
            return objectMapper.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("解析大模型 JSON 数组失败，目标类型: " + clazz.getSimpleName() + ", 原始内容: " + content, e);
        }
    }

    /**
     * 兼容大模型偶尔返回 ```json ... ``` 或前后带解释文本的情况。
     */
    private String extractJson(String content) {
        if (content == null || content.isBlank()) {
            return "{}";
        }

        String text = content.trim();
        if (text.startsWith("```")) {
            text = text.replaceFirst("^```json", "")
                    .replaceFirst("^```", "")
                    .replaceFirst("```$", "")
                    .trim();
        }

        int objectStart = text.indexOf('{');
        int arrayStart = text.indexOf('[');

        if (arrayStart >= 0 && (objectStart < 0 || arrayStart < objectStart)) {
            int arrayEnd = text.lastIndexOf(']');
            if (arrayEnd > arrayStart) {
                return text.substring(arrayStart, arrayEnd + 1);
            }
        }

        if (objectStart >= 0) {
            int objectEnd = text.lastIndexOf('}');
            if (objectEnd > objectStart) {
                return text.substring(objectStart, objectEnd + 1);
            }
        }

        return text;
    }
}
