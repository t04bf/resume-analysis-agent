package com.example.resumeagent.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class JobProfile {

    private String positionName;

    /**
     * 初级、中级、高级、专家、未知
     */
    private String seniority;

    private List<String> requiredSkills = new ArrayList<>();

    private List<String> preferredSkills = new ArrayList<>();

    private List<String> responsibilities = new ArrayList<>();

    private List<String> businessKeywords = new ArrayList<>();

    private List<String> evaluationFocus = new ArrayList<>();
}
