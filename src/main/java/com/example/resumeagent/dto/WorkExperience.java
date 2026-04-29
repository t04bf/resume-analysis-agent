package com.example.resumeagent.dto;

import lombok.Data;

@Data
public class WorkExperience {

    private String company;

    private String title;

    private String startDate;

    private String endDate;

    private String description;
}
