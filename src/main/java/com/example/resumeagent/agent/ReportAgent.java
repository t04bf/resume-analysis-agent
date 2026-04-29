package com.example.resumeagent.agent;

import com.example.resumeagent.dto.AnalysisReport;
import com.example.resumeagent.dto.AnalyzeResumeRequest;
import com.example.resumeagent.dto.CandidateProfile;
import com.example.resumeagent.dto.InterviewQuestion;
import com.example.resumeagent.dto.JobProfile;
import com.example.resumeagent.dto.MatchScore;
import com.example.resumeagent.llm.LlmClient;
import com.example.resumeagent.util.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReportAgent {

    private final LlmClient llmClient;
    private final JsonUtils jsonUtils;
    private final ObjectMapper objectMapper;

    public AnalysisReport generate(CandidateProfile candidateProfile,
                                   JobProfile jobProfile,
                                   MatchScore matchScore,
                                   List<InterviewQuestion> questions,
                                   AnalyzeResumeRequest request) {
        String questionsJson = toJson(questions);
        String prompt = PromptTemplates.reportPrompt(candidateProfile, jobProfile, matchScore, questionsJson, request);
        String content = llmClient.chat(prompt);
        return jsonUtils.fromJson(content, AnalysisReport.class);
    }

    private String toJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            return String.valueOf(value);
        }
    }
}
