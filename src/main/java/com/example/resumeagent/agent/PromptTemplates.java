package com.example.resumeagent.agent;

import com.example.resumeagent.dto.AnalyzeResumeRequest;
import com.example.resumeagent.dto.CandidateProfile;
import com.example.resumeagent.dto.JobProfile;
import com.example.resumeagent.dto.MatchScore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class PromptTemplates {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private PromptTemplates() {
    }

    public static String resumeParserPrompt(AnalyzeResumeRequest request) {
        return """
                你是一个招聘系统里的简历解析 Agent。
                请把候选人简历解析为严格 JSON，不要输出 Markdown，不要输出解释。
                JSON 字段结构如下：
                {
                  "candidateName": "候选人姓名",
                  "currentTitle": "当前职位或最接近职位",
                  "yearsOfExperience": 3,
                  "educationSummary": "学历摘要",
                  "skills": [
                    {"name": "Java", "level": "熟练", "evidence": "项目中使用 Spring Boot 开发接口"}
                  ],
                  "workExperiences": [
                    {"company": "公司名", "title": "职位", "startDate": "2023-01", "endDate": "2024-01", "description": "工作内容"}
                  ],
                  "projectExperiences": [
                    {"name": "项目名", "role": "角色", "description": "项目描述", "techStack": ["Java"], "highlights": ["亮点"]}
                  ],
                  "advantages": ["优势"],
                  "riskPoints": ["风险点"]
                }

                候选人姓名：%s
                目标岗位：%s
                简历全文：
                %s
                """.formatted(
                nullToEmpty(request.getCandidateName()),
                nullToEmpty(request.getTargetPosition()),
                request.getResumeText()
        );
    }

    public static String jobDescriptionPrompt(AnalyzeResumeRequest request) {
        return """
                你是一个招聘系统里的 JD 理解 Agent。
                请把岗位描述解析为严格 JSON，不要输出 Markdown，不要输出解释。
                JSON 字段结构如下：
                {
                  "positionName": "岗位名称",
                  "seniority": "初级/中级/高级/专家/未知",
                  "requiredSkills": ["Java", "Spring Boot"],
                  "preferredSkills": ["Elasticsearch"],
                  "responsibilities": ["负责后端接口开发"],
                  "businessKeywords": ["招聘", "订单"],
                  "evaluationFocus": ["项目深度", "高并发经验"]
                }

                目标岗位：%s
                JD全文：
                %s
                """.formatted(
                nullToEmpty(request.getTargetPosition()),
                request.getJobDescription()
        );
    }

    public static String matchScoringPrompt(CandidateProfile candidateProfile,
                                           JobProfile jobProfile,
                                           AnalyzeResumeRequest request) {
        return """
                你是一个招聘系统里的候选人与岗位匹配打分 Agent。
                请根据候选人画像和岗位画像输出严格 JSON，不要输出 Markdown，不要输出解释。
                打分区间 0-100，分数要可解释。
                JSON 字段结构如下：
                {
                  "overallScore": 82,
                  "skillScore": 85,
                  "projectScore": 80,
                  "experienceScore": 78,
                  "stabilityScore": 70,
                  "matchLevel": "强匹配/较匹配/一般/不匹配",
                  "matchedSkills": ["Java", "Spring Boot"],
                  "missingSkills": ["Kubernetes"],
                  "scoreReasons": ["理由"],
                  "riskPoints": ["风险点"]
                }

                目标岗位：%s
                候选人画像：
                %s

                岗位画像：
                %s
                """.formatted(
                nullToEmpty(request.getTargetPosition()),
                toJson(candidateProfile),
                toJson(jobProfile)
        );
    }

    public static String interviewQuestionPrompt(CandidateProfile candidateProfile,
                                                 JobProfile jobProfile,
                                                 MatchScore matchScore,
                                                 AnalyzeResumeRequest request) {
        return """
                你是一个招聘系统里的面试题生成 Agent。
                请根据候选人简历、岗位要求和匹配结果，生成个性化面试题。
                输出严格 JSON 数组，不要输出 Markdown，不要输出解释。
                JSON 数组元素结构如下：
                {
                  "type": "基础八股/项目深挖/业务场景/风险追问",
                  "question": "问题",
                  "evaluationPoint": "考察点",
                  "referenceDirection": "理想回答方向",
                  "difficulty": "简单/中等/困难"
                }

                要求：
                1. 至少生成 8 个问题。
                2. 必须围绕候选人真实项目经历追问。
                3. 必须覆盖岗位核心技能。
                4. 风险点要设计追问问题。

                目标岗位：%s
                候选人画像：
                %s

                岗位画像：
                %s

                匹配结果：
                %s
                """.formatted(
                nullToEmpty(request.getTargetPosition()),
                toJson(candidateProfile),
                toJson(jobProfile),
                toJson(matchScore)
        );
    }

    public static String reportPrompt(CandidateProfile candidateProfile,
                                      JobProfile jobProfile,
                                      MatchScore matchScore,
                                      String questionsJson,
                                      AnalyzeResumeRequest request) {
        return """
                你是招聘系统里的 HR 分析报告 Agent。
                请输出严格 JSON，不要输出 Markdown，不要输出解释。
                JSON 字段结构如下：
                {
                  "summary": "一句话总结候选人是否适合该岗位",
                  "hrRecommendation": "建议进入面试/建议储备/建议淘汰/需要人工复核",
                  "candidateHighlights": ["亮点"],
                  "concerns": ["顾虑"],
                  "interviewSuggestion": "面试建议",
                  "nextAction": "下一步动作"
                }

                目标岗位：%s
                候选人画像：
                %s

                岗位画像：
                %s

                匹配结果：
                %s

                面试题：
                %s
                """.formatted(
                nullToEmpty(request.getTargetPosition()),
                toJson(candidateProfile),
                toJson(jobProfile),
                toJson(matchScore),
                questionsJson
        );
    }

    private static String toJson(Object value) {
        try {
            return OBJECT_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            return String.valueOf(value);
        }
    }

    private static String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}
