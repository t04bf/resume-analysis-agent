package com.example.resumeagent;

import com.example.resumeagent.dto.AnalyzeResumeRequest;
import com.example.resumeagent.dto.AnalyzeResumeResponse;
import com.example.resumeagent.service.ResumeAnalysisService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "agent.llm.provider=mock")
class ResumeAnalysisServiceTest {

    @Autowired
    private ResumeAnalysisService resumeAnalysisService;

    @Test
    void analyzeShouldReturnReport() {
        AnalyzeResumeRequest request = new AnalyzeResumeRequest();
        request.setCandidateName("张三");
        request.setTargetPosition("Java 后端开发工程师");
        request.setResumeText("3年Java后端开发经验，熟悉Spring Boot、MySQL、Redis、RabbitMQ，做过招聘系统和订单系统。");
        request.setJobDescription("要求熟悉Java、Spring Boot、MySQL、Redis、消息队列，有订单或招聘系统经验。");

        AnalyzeResumeResponse response = resumeAnalysisService.analyze(request);

        Assertions.assertNotNull(response.getAnalysisId());
        Assertions.assertNotNull(response.getCandidateProfile());
        Assertions.assertNotNull(response.getJobProfile());
        Assertions.assertNotNull(response.getMatchScore());
        Assertions.assertFalse(response.getInterviewQuestions().isEmpty());
        Assertions.assertNotNull(response.getReport());
    }
}
