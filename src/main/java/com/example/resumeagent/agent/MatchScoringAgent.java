package com.example.resumeagent.agent;

import com.example.resumeagent.dto.AnalyzeResumeRequest;
import com.example.resumeagent.dto.CandidateProfile;
import com.example.resumeagent.dto.JobProfile;
import com.example.resumeagent.dto.MatchScore;
import com.example.resumeagent.llm.LlmClient;
import com.example.resumeagent.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchScoringAgent {

    private final LlmClient llmClient;
    private final JsonUtils jsonUtils;

    public MatchScore score(CandidateProfile candidateProfile,
                            JobProfile jobProfile,
                            AnalyzeResumeRequest request) {
        String prompt = PromptTemplates.matchScoringPrompt(candidateProfile, jobProfile, request);
        String content = llmClient.chat(prompt);
        return jsonUtils.fromJson(content, MatchScore.class);
    }
}
