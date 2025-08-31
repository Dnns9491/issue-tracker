package com.example.issue_tracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateProjectRequest (
    @NotBlank @Size(max = 16) String key,

    @NotBlank @Size(max = 100) String name
) {}