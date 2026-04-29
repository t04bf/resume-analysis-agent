package com.example.resumeagent.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MatchScore {

    private Integer overallScore;

    private Integer skillScore;

    private Integer projectScore;

    private Integer experienceScore;

    private Integer stabilityScore;

    /**
     * 强匹配、较匹配、一般、不匹配
     */
    private String matchLevel;

    private List<String> matchedSkills = new ArrayList<>();

    private List<String> missingSkills = new ArrayList<>();

    private List<String> scoreReasons = new ArrayList<>();

    private List<String> riskPoints = new ArrayList<>();
}
