package com.example.resumeagent.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AnalysisReport {

    private String summary;

    /**
     * 建议进入面试、建议储备、建议淘汰、需要人工复核
     */
    private String hrRecommendation;

    private List<String> candidateHighlights = new ArrayList<>();

    private List<String> concerns = new ArrayList<>();

    private String interviewSuggestion;

    private String nextAction;
}
