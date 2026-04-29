package com.example.resumeagent.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class AnalyzeResumeResponse {

    private String analysisId;

    private LocalDateTime createdAt;

    private CandidateProfile candidateProfile;

    private JobProfile jobProfile;

    private MatchScore matchScore;

    private List<InterviewQuestion> interviewQuestions;

    private AnalysisReport report;
}
