package com.example.resumeagent.agent;

import com.example.resumeagent.dto.AnalyzeResumeRequest;
import com.example.resumeagent.dto.JobProfile;
import com.example.resumeagent.llm.LlmClient;
import com.example.resumeagent.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobDescriptionAgent {

    private final LlmClient llmClient;
    private final JsonUtils jsonUtils;

    public JobProfile analyze(AnalyzeResumeRequest request) {
        String prompt = PromptTemplates.jobDescriptionPrompt(request);
        String content = llmClient.chat(prompt);
        JobProfile profile = jsonUtils.fromJson(content, JobProfile.class);

        if (profile.getPositionName() == null || profile.getPositionName().isBlank()) {
            profile.setPositionName(request.getTargetPosition());
        }
        return profile;
    }
}
