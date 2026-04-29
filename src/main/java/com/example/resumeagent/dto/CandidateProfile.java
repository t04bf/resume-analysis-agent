package com.example.resumeagent.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CandidateProfile {

    private String candidateName;

    private String currentTitle;

    private Integer yearsOfExperience;

    private String educationSummary;

    private List<SkillItem> skills = new ArrayList<>();

    private List<WorkExperience> workExperiences = new ArrayList<>();

    private List<ProjectExperience> projectExperiences = new ArrayList<>();

    private List<String> advantages = new ArrayList<>();

    private List<String> riskPoints = new ArrayList<>();
}
