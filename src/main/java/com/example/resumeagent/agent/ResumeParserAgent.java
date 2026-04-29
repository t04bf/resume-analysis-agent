package com.example.resumeagent.agent;

import com.example.resumeagent.dto.AnalyzeResumeRequest;
import com.example.resumeagent.dto.CandidateProfile;
import com.example.resumeagent.llm.LlmClient;
import com.example.resumeagent.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResumeParserAgent {

    private final LlmClient llmClient;
    private final JsonUtils jsonUtils;

    public CandidateProfile parse(AnalyzeResumeRequest request) {
        String prompt = PromptTemplates.resumeParserPrompt(request);
        String content = llmClient.chat(prompt);
        CandidateProfile profile = jsonUtils.fromJson(content, CandidateProfile.class);

        if (profile.getCandidateName() == null || profile.getCandidateName().isBlank()) {
            profile.setCandidateName(request.getCandidateName());
        }
        return profile;
    }
}
