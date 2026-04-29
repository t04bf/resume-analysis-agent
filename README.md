# AI 招聘系统 - 简历分析 Agent

这是一个完整可运行的 Java Spring Boot 示例项目，用来演示如何在招聘系统中落地一个“简历分析 Agent”。

项目支持两种模式：

1. `mock`：不需要 API Key，本地直接运行，返回模拟分析结果。
2. `openai-compatible`：接入 OpenAI 兼容接口，让 Agent 真正调用大模型完成简历解析、JD 分析、匹配打分和面试题生成。

## 一、项目解决的问题

传统招聘系统通常只能做关键词匹配，例如“Java”“Redis”“MySQL”，但很难判断：

- 候选人的项目经历是否真实匹配岗位要求
- 技术栈是浅层使用还是深度实践
- 简历中有哪些风险点需要面试追问
- 面试官应该围绕候选人的项目问哪些问题
- HR 是否能快速看到一份可解释的候选人摘要

这个项目把简历分析拆成多个 Agent 协作完成。

## 二、Agent 流程

```text
简历文本 + JD 文本
      ↓
ResumeParserAgent：解析候选人画像
      ↓
JobDescriptionAgent：解析岗位要求
      ↓
MatchScoringAgent：计算岗位匹配分数
      ↓
InterviewQuestionAgent：生成个性化面试题
      ↓
ReportAgent：生成 HR 摘要和面试建议
```

## 三、启动项目

```bash
mvn spring-boot:run
```

默认启动端口：`8080`

## 四、本地 Mock 模式

默认配置就是 Mock 模式，不需要任何 API Key。

```yaml
agent:
  llm:
    provider: mock
```

调用：

```http
POST http://localhost:8080/api/resume-agent/analyze
Content-Type: application/json

{
  "candidateName": "张三",
  "targetPosition": "Java 后端开发工程师",
  "resumeText": "张三，本科，3年Java后端开发经验，熟悉Spring Boot、MySQL、Redis、RabbitMQ，负责过订单系统、招聘系统。",
  "jobDescription": "招聘Java后端开发工程师，要求熟悉Spring Boot、MySQL、Redis、消息队列，有高并发系统经验。"
}
```

## 五、接入 OpenAI 兼容模型

修改 `src/main/resources/application.yml`：

```yaml
agent:
  llm:
    provider: openai-compatible
    base-url: https://api.openai.com/v1
    api-key: ${OPENAI_API_KEY:}
    model: gpt-4o-mini
    temperature: 0.2
    max-tokens: 2000
```

环境变量：

```bash
export OPENAI_API_KEY="你的Key"
mvn spring-boot:run
```

只要服务兼容 `/chat/completions` 格式，也可以替换成其他大模型网关。

## 六、接口说明

### 1. 健康检查

```http
GET /api/resume-agent/health
```

### 2. 分析简历

```http
POST /api/resume-agent/analyze
```

请求体：

```json
{
  "candidateName": "张三",
  "targetPosition": "Java 后端开发工程师",
  "resumeText": "简历全文",
  "jobDescription": "岗位JD全文"
}
```

响应体包含：

- 候选人结构化画像
- 岗位结构化画像
- 匹配分数
- 技能匹配明细
- 风险点
- 面试问题
- HR 摘要
- 面试建议

## 七、后续可扩展方向

1. 接入 PDF / Word 简历解析，将文件转为文本后再进入 Agent。
2. 将候选人画像落库，用于人才库搜索。
3. 将 JD 画像落库，用于职位推荐。
4. 接入向量数据库，实现候选人与岗位的语义匹配。
5. 接入企业内部题库，让 Agent 根据岗位和候选人经历组合面试题。
6. 增加人工反馈闭环，用 HR 的“合适/不合适”结果优化打分规则。
