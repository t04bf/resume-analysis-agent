package com.example.resumeagent.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AnalyzeResumeRequest {

    private String candidateName;

    private String targetPosition;

    @NotBlank(message = "简历文本不能为空")
    @Size(max = 20000, message = "简历文本不能超过 20000 字符")
    private String resumeText;

    @NotBlank(message = "岗位描述不能为空")
    @Size(max = 12000, message = "岗位描述不能超过 12000 字符")
    private String jobDescription;
}
