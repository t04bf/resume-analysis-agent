package com.example.resumeagent.dto;

import lombok.Data;

@Data
public class SkillItem {

    private String name;

    /**
     * 例如：了解、掌握、熟练、精通
     */
    private String level;

    /**
     * 从简历中抽取的证据
     */
    private String evidence;
}
