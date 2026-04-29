package com.example.resumeagent.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectExperience {

    private String name;

    private String role;

    private String description;

    private List<String> techStack = new ArrayList<>();

    private List<String> highlights = new ArrayList<>();
}
