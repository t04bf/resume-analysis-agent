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

    /**
     * 对结果真实性边界的说明，提醒必须结合人工面试核验。
     */
    private String authenticityNotice;

    /**
     * 输入信息完整度评分（0-100），用于提示分析可信度。
     */
    private Integer inputQualityScore;
}
