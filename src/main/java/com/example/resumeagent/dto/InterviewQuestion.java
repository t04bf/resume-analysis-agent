package com.example.resumeagent.dto;

import lombok.Data;

@Data
public class InterviewQuestion {

    /**
     * 基础八股、项目深挖、业务场景、风险追问
     */
    private String type;

    private String question;

    private String evaluationPoint;

    private String referenceDirection;

    /**
     * 简单、中等、困难
     */
    private String difficulty;
}
