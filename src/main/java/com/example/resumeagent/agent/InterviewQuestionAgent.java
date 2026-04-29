package com.example.resumeagent.agent;

import com.example.resumeagent.dto.AnalyzeResumeRequest;
import com.example.resumeagent.dto.CandidateProfile;
import com.example.resumeagent.dto.InterviewQuestion;
import com.example.resumeagent.dto.JobProfile;
import com.example.resumeagent.dto.MatchScore;
import com.example.resumeagent.llm.LlmClient;
import com.example.resumeagent.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InterviewQuestionAgent {

    private final LlmClient llmClient;
    private final JsonUtils jsonUtils;

    public List<InterviewQuestion> generate(CandidateProfile candidateProfile,
                                            JobProfile jobProfile,
                                            MatchScore matchScore,
                                            AnalyzeResumeRequest request) {
        String prompt = PromptTemplates.interviewQuestionPrompt(candidateProfile, jobProfile, matchScore, request);
        String content = llmClient.chat(prompt);
        return jsonUtils.fromJsonList(content, InterviewQuestion.class);
    }
}
