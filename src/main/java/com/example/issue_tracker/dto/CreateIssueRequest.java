package com.example.issue_tracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateIssueRequest (
        @NotNull Long projectId,
        @NotBlank @Size(max=200) String title,
        @Size(max=10_000) String description,
        Long assigneeId
){}
