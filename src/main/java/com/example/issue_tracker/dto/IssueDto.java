package com.example.issue_tracker.dto;

import com.example.issue_tracker.domain.IssueStatus;

import java.time.Instant;

public record IssueDto(

        Long id,
        String title,
        String description,
        IssueStatus status,
        UserDto assignee,
        ProjectDto project,
        Instant createdAt,
        Instant updatedAt
) {
}
