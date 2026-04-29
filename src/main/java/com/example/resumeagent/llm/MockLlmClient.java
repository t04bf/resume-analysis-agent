package com.example.resumeagent.llm;

public class MockLlmClient implements LlmClient {

    @Override
    public String chat(String prompt) {
        if (prompt.contains("简历解析 Agent")) {
            return resumeProfileJson();
        }
        if (prompt.contains("JD 理解 Agent")) {
            return jobProfileJson();
        }
        if (prompt.contains("匹配打分 Agent")) {
            return matchScoreJson();
        }
        if (prompt.contains("面试题生成 Agent")) {
            return interviewQuestionsJson();
        }
        if (prompt.contains("HR 分析报告 Agent")) {
            return reportJson();
        }
        return "{}";
    }

    private String resumeProfileJson() {
        return """
                {
                  "candidateName": "张三",
                  "currentTitle": "Java 后端开发工程师",
                  "yearsOfExperience": 3,
                  "educationSummary": "本科，计算机相关专业",
                  "skills": [
                    {"name": "Java", "level": "熟练", "evidence": "使用 Java 参与多个后端业务模块开发"},
                    {"name": "Spring Boot", "level": "熟练", "evidence": "负责 REST 接口、业务 Service 和参数校验"},
                    {"name": "MySQL", "level": "熟练", "evidence": "参与表结构设计、索引优化和慢 SQL 排查"},
                    {"name": "Redis", "level": "掌握", "evidence": "使用缓存、分布式锁和验证码场景"},
                    {"name": "RabbitMQ", "level": "掌握", "evidence": "用于订单状态和异步消息处理"},
                    {"name": "Elasticsearch", "level": "了解", "evidence": "参与职位或内容搜索相关功能"}
                  ],
                  "workExperiences": [
                    {"company": "某互联网公司", "title": "Java 后端开发实习生", "startDate": "2024-06", "endDate": "至今", "description": "参与招聘系统、订单系统、接口联调和业务后台开发"}
                  ],
                  "projectExperiences": [
                    {
                      "name": "招聘业务系统",
                      "role": "后端开发",
                      "description": "负责候选人、职位、简历分析和搜索相关模块",
                      "techStack": ["Java", "Spring Boot", "MySQL", "Redis", "Elasticsearch"],
                      "highlights": ["完成候选人画像接口", "优化职位搜索查询", "设计部分表结构"]
                    },
                    {
                      "name": "订单业务系统",
                      "role": "后端开发",
                      "description": "负责订单创建、支付状态流转和消息异步处理",
                      "techStack": ["Java", "Spring Boot", "MySQL", "Redis", "RabbitMQ"],
                      "highlights": ["处理订单幂等", "使用消息队列解耦状态同步"]
                    }
                  ],
                  "advantages": ["Java 后端基础较完整", "有真实业务项目经验", "技术栈与岗位要求匹配度较高"],
                  "riskPoints": ["高并发经验描述偏概括", "Elasticsearch 深度需要面试验证", "缺少明确的性能指标数据"]
                }
                """;
    }

    private String jobProfileJson() {
        return """
                {
                  "positionName": "Java 后端开发工程师",
                  "seniority": "中级",
                  "requiredSkills": ["Java", "Spring Boot", "MySQL", "Redis", "消息队列", "接口设计"],
                  "preferredSkills": ["Elasticsearch", "高并发系统", "订单系统", "招聘业务"],
                  "responsibilities": ["负责核心业务接口开发", "参与数据库设计和性能优化", "保障系统稳定性和可维护性"],
                  "businessKeywords": ["招聘", "订单", "搜索", "支付"],
                  "evaluationFocus": ["Java 基础", "项目深度", "数据库优化", "缓存一致性", "消息可靠性"]
                }
                """;
    }

    private String matchScoreJson() {
        return """
                {
                  "overallScore": 84,
                  "skillScore": 88,
                  "projectScore": 82,
                  "experienceScore": 78,
                  "stabilityScore": 75,
                  "matchLevel": "较匹配",
                  "matchedSkills": ["Java", "Spring Boot", "MySQL", "Redis", "RabbitMQ", "Elasticsearch"],
                  "missingSkills": ["高并发压测经验", "完整系统架构设计经验"],
                  "scoreReasons": [
                    "候选人技术栈与岗位核心要求高度重合",
                    "有招聘系统和订单系统相关项目经历",
                    "具备接口开发、数据库设计、缓存和消息队列使用经验",
                    "部分高并发和架构设计经验需要面试继续验证"
                  ],
                  "riskPoints": ["项目指标不够量化", "技术深度需要通过追问验证"]
                }
                """;
    }

    private String interviewQuestionsJson() {
        return """
                [
                  {"type": "基础八股", "question": "HashMap 在 JDK 1.8 中的扩容机制是怎样的？为什么负载因子默认是 0.75？", "evaluationPoint": "Java 集合基础和底层理解", "referenceDirection": "能说明数组、链表、红黑树、扩容迁移和性能折中", "difficulty": "中等"},
                  {"type": "基础八股", "question": "Spring Boot 接口请求进入 Controller 之前会经历哪些关键流程？", "evaluationPoint": "Spring MVC 请求链路理解", "referenceDirection": "能讲清 DispatcherServlet、HandlerMapping、参数绑定、拦截器和异常处理", "difficulty": "中等"},
                  {"type": "项目深挖", "question": "你在招聘系统中做候选人画像接口时，表结构是怎么设计的？为什么这么拆？", "evaluationPoint": "数据库建模能力", "referenceDirection": "能解释候选人、简历、技能、项目经历等实体关系", "difficulty": "中等"},
                  {"type": "项目深挖", "question": "订单系统里如何避免用户重复提交订单或重复支付？", "evaluationPoint": "幂等设计", "referenceDirection": "能讲唯一订单号、状态机、Redis 锁、数据库唯一索引和回调幂等", "difficulty": "困难"},
                  {"type": "业务场景", "question": "如果职位搜索接口变慢，你会从哪些方向排查？", "evaluationPoint": "性能排查能力", "referenceDirection": "能覆盖慢 SQL、索引、ES 查询、分页、缓存、日志链路和数据量", "difficulty": "中等"},
                  {"type": "业务场景", "question": "Redis 缓存和 MySQL 数据不一致时，你会怎么设计更新策略？", "evaluationPoint": "缓存一致性", "referenceDirection": "能说明先更新数据库再删除缓存、延迟双删、MQ 重试和最终一致性", "difficulty": "困难"},
                  {"type": "风险追问", "question": "你简历里提到 Elasticsearch，请具体说一个你写过的查询 DSL 或调优案例。", "evaluationPoint": "验证 ES 是否真实深入使用", "referenceDirection": "能说出索引、mapping、bool query、排序、分页或问题排查细节", "difficulty": "中等"},
                  {"type": "风险追问", "question": "你提到消息队列异步处理订单状态，如果消息消费失败或重复消费，你们怎么兜底？", "evaluationPoint": "消息可靠性", "referenceDirection": "能说明 ACK、重试、死信队列、幂等消费和补偿任务", "difficulty": "困难"}
                ]
                """;
    }

    private String reportJson() {
        return """
                {
                  "summary": "候选人与 Java 后端岗位整体较匹配，具备 Spring Boot、MySQL、Redis、消息队列等核心技能，并有招聘和订单相关项目经验。",
                  "hrRecommendation": "建议进入面试",
                  "candidateHighlights": [
                    "技术栈与岗位要求重合度高",
                    "有真实业务系统开发经历",
                    "项目方向与招聘、订单、搜索等业务关键词相关"
                  ],
                  "concerns": [
                    "高并发经验和性能优化指标不够量化",
                    "Elasticsearch 和消息可靠性需要面试重点验证",
                    "系统设计能力需要通过场景题判断"
                  ],
                  "interviewSuggestion": "建议先验证 Java 基础和 Spring Boot 请求链路，再围绕招聘系统、订单幂等、缓存一致性、消息可靠性进行项目深挖。",
                  "nextAction": "安排一轮技术面试，重点考察项目真实性、问题排查能力和复杂业务设计能力。"
                }
                """;
    }
}
