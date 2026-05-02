package com.example.resumeagent.util;

import com.example.resumeagent.dto.AnalyzeResumeRequest;
import org.springframework.stereotype.Component;

@Component
public class InputQualityEvaluator {

    public int score(AnalyzeResumeRequest request) {
        int score = 40;
        score += lengthScore(request.getResumeText(), 300, 2500, 35);
        score += lengthScore(request.getJobDescription(), 150, 1800, 20);

        if (hasValue(request.getCandidateName())) {
            score += 3;
        }
        if (hasValue(request.getTargetPosition())) {
            score += 2;
        }
        return Math.min(score, 100);
    }

    private int lengthScore(String text, int minLength, int goodLength, int maxPoints) {
        if (!hasValue(text)) {
            return 0;
        }
        int length = text.trim().length();
        if (length < minLength) {
            return maxPoints / 3;
        }
        if (length >= goodLength) {
            return maxPoints;
        }
        double ratio = (double) (length - minLength) / (goodLength - minLength);
        return (int) Math.round((maxPoints / 3.0) + ratio * (maxPoints * 2.0 / 3.0));
    }

    private boolean hasValue(String value) {
        return value != null && !value.isBlank();
    }
}
