package com.example.resumeagent.controller;

import com.example.resumeagent.dto.AnalyzeResumeRequest;
import com.example.resumeagent.dto.AnalyzeResumeResponse;
import com.example.resumeagent.service.ResumeAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/resume-agent")
public class ResumeAnalysisController {

    private final ResumeAnalysisService resumeAnalysisService;

    @GetMapping("/health")
    public Map<String, Object> health() {
        return Map.of(
                "status", "ok",
                "service", "resume-analysis-agent"
        );
    }

    @PostMapping("/analyze")
    public AnalyzeResumeResponse analyze(@Valid @RequestBody AnalyzeResumeRequest request) {
        return resumeAnalysisService.analyze(request);
    }
}
