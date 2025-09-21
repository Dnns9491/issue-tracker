package com.example.issue_tracker.dto;

import com.example.issue_tracker.domain.IssueStatus;
import jakarta.validation.constraints.Size;

public record UpdateIssueRequest(
        @Size(max = 200) String title,
        @Size(max = 10_000) String description,
        IssueStatus status,
        Long assigneeId
) {
}
