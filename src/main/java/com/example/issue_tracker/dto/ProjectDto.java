package com.example.issue_tracker.dto;

import java.time.Instant;

public record ProjectDto(Long id, String key, String name, Instant createdAt) {
}