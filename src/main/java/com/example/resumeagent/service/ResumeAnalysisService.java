package com.example.resumeagent.service;

import com.example.resumeagent.agent.InterviewQuestionAgent;
import com.example.resumeagent.agent.JobDescriptionAgent;
import com.example.resumeagent.agent.MatchScoringAgent;
import com.example.resumeagent.agent.ReportAgent;
import com.example.resumeagent.agent.ResumeParserAgent;
import com.example.resumeagent.dto.AnalysisReport;
import com.example.resumeagent.dto.AnalyzeResumeRequest;
import com.example.resumeagent.dto.AnalyzeResumeResponse;
import com.example.resumeagent.dto.CandidateProfile;
import com.example.resumeagent.dto.InterviewQuestion;
import com.example.resumeagent.dto.JobProfile;
import com.example.resumeagent.dto.MatchScore;
import com.example.resumeagent.util.InputQualityEvaluator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResumeAnalysisService {

    private final ResumeParserAgent resumeParserAgent;
    private final JobDescriptionAgent jobDescriptionAgent;
    private final MatchScoringAgent matchScoringAgent;
    private final InterviewQuestionAgent interviewQuestionAgent;
    private final ReportAgent reportAgent;
    private final InputQualityEvaluator inputQualityEvaluator;

    public AnalyzeResumeResponse analyze(AnalyzeResumeRequest request) {
        CandidateProfile candidateProfile = resumeParserAgent.parse(request);
        JobProfile jobProfile = jobDescriptionAgent.analyze(request);
        MatchScore matchScore = matchScoringAgent.score(candidateProfile, jobProfile, request);
        List<InterviewQuestion> questions = interviewQuestionAgent.generate(candidateProfile, jobProfile, matchScore, request);
        AnalysisReport report = reportAgent.generate(candidateProfile, jobProfile, matchScore, questions, request);
        int inputQualityScore = inputQualityEvaluator.score(request);

        return AnalyzeResumeResponse.builder()
                .analysisId(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .candidateProfile(candidateProfile)
                .jobProfile(jobProfile)
                .matchScore(matchScore)
                .interviewQuestions(questions)
                .report(report)
                .inputQualityScore(inputQualityScore)
                .authenticityNotice("本结果基于候选人提交文本与模型推断生成，不能替代背调和技术面试，请对关键经历做追问核验。")
                .build();
    }
}
