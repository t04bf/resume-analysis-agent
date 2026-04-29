package com.example.resumeagent;

import com.example.resumeagent.config.AgentProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AgentProperties.class)
public class ResumeAnalysisAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResumeAnalysisAgentApplication.class, args);
    }
}
